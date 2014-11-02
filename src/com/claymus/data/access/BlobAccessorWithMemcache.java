package com.claymus.data.access;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;

public class BlobAccessorWithMemcache implements BlobAccessor {

	private final static String PREFIX = "BlobEntry-";

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
	public BlobEntry getBlob( String fileName )
			throws IOException {
		
		BlobEntry blobEntry = memcache.get( PREFIX + fileName );
		if( blobEntry == null ) {
			blobEntry = blobAccessor.getBlob( fileName );
			if( blobEntry != null && blobEntry.getData().length <= 1000 * 1024 )
				memcache.put( PREFIX + fileName, blobEntry );
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
