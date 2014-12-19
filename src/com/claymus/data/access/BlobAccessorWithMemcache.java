package com.claymus.data.access;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;


public class BlobAccessorWithMemcache implements BlobAccessor {

	private static final Logger logger = 
			Logger.getLogger( BlobAccessorWithMemcache.class.getName() );

	
	private final static String PREFIX = "BlobEntry-";
	private final static int SEGMENT_SIZE = 1000 * 1024; // bytes

	private final BlobAccessor blobAccessor;
	private final Memcache memcache;


	public BlobAccessorWithMemcache( BlobAccessor blobAccessor, Memcache memcache ) {
		this.blobAccessor = blobAccessor;
		this.memcache = memcache;
	}
	
	
	@Override
	public String createUploadUrl( String fileName ) {
		return blobAccessor.createUploadUrl( fileName );
	}

	@Override
	public boolean createBlob( HttpServletRequest request, String fileName ) {
		boolean blobCreated = blobAccessor.createBlob( request, fileName );
		if( blobCreated )
			memcache.remove( PREFIX + fileName );
		return blobCreated;
	}

	@Override
	public void createBlob( String fileName, String mimeType, byte[] bytes )
			throws IOException {
		
		blobAccessor.createBlob( fileName, mimeType, bytes );
		memcache.remove( PREFIX + fileName );
	}

	@Override
	public void createBlob( String fileName, String mimeType, byte[] bytes,
			String acl, Map<String, String> metaDataMap) throws IOException {
		
		blobAccessor.createBlob( fileName, mimeType, bytes, acl, metaDataMap );
		memcache.remove( PREFIX + fileName );
	}

	@Override
	public void createBlob( String fileName, String mimeType, String content, Charset charset )
			throws IOException {
		
		blobAccessor.createBlob( fileName, mimeType, content, charset );
		memcache.remove( PREFIX + fileName );
	}

	@Override
	public void updateBlob( BlobEntry blobEntry, byte[] bytes )
			throws IOException {

		blobAccessor.updateBlob( blobEntry, bytes );
		memcache.remove( PREFIX + blobEntry.getName() );
	}

	@Override
	public void updateBlob( BlobEntry blobEntry, String content, Charset charset )
			throws IOException {

		blobAccessor.updateBlob( blobEntry, content, charset );
		memcache.remove( PREFIX + blobEntry.getName() );
	}

	@Override
	public BlobEntry getBlob( String fileName ) throws IOException {
		
		BlobEntry blobEntry = memcache.get( PREFIX + fileName );
		
		
		if( blobEntry == null ) {
			blobEntry = blobAccessor.getBlob( fileName );

			if( blobEntry != null && blobEntry.getDataLength() <= 1000 * 1024 ) {
				memcache.put( PREFIX + fileName, blobEntry );
			
			} else if( blobEntry != null ) { // && blobEntry.getData().length > 1000 * 1024
				byte[] blobData = blobEntry.getData();
				int dataLength = (int) blobEntry.getDataLength();
				blobEntry.setData( Arrays.copyOfRange( blobData, 0, SEGMENT_SIZE ) );
				Map<String, Serializable> keySegmentMap = new HashMap<>();
				keySegmentMap.put( PREFIX + fileName, blobEntry );
				for( int i = 1; i < (int) Math.ceil( (double) dataLength / SEGMENT_SIZE ); i++ ) {
					int fromIndex = i * SEGMENT_SIZE;
					int toIndex = (i + 1) * SEGMENT_SIZE;
					toIndex = toIndex > dataLength ? dataLength : toIndex;
					keySegmentMap.put( PREFIX + fileName + "?" + i, Arrays.copyOfRange( blobData, fromIndex, toIndex ) );
				}
				memcache.putAll( keySegmentMap );
				blobEntry.setData( blobData );
			}
		
			
		} else if( blobEntry.getData().length < blobEntry.getDataLength() ) {
			int dataLength = (int) blobEntry.getDataLength();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.write( blobEntry.getData() );

			List<String> keyList = new ArrayList<String>( (int) Math.ceil( (double) dataLength / SEGMENT_SIZE ) );
			for( int i = 1; i < (int) Math.ceil( (double) dataLength / SEGMENT_SIZE ); i++ )
				keyList.add( PREFIX + fileName + "?" + i );

			Map<String, Serializable> keySegmentMap = memcache.getAll( keyList );
			for( String key : keyList ) {
				byte[] dataSegment = (byte[]) keySegmentMap.get( key );
				if( dataSegment == null )
					break;
				baos.write( dataSegment );
			}
			
			byte[] blobData = baos.toByteArray();
			if( blobData.length < dataLength ) {
				logger.log( Level.INFO, "Blob size (" + blobData.length + ") did not match expected size (" + dataLength + ")" );
				blobEntry = blobAccessor.getBlob( fileName );
			} else {
				blobEntry.setData( blobData );
			}
		}
		
		
		return blobEntry;
	}
	
	@Override
	public void serveBlob( String fileName, HttpServletResponse response )
			throws IOException {

		blobAccessor.serveBlob( fileName, response );
	}

	@Override
	public List<String> getFilenameList(String prefix) throws IOException {
		// TODO : ADD MEMECACHE LOGIC
		return null;
	}

}