package com.claymus.data.access;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import com.claymus.data.access.gcs.BlobEntryGcsImpl;
import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFileOptions.Builder;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.appengine.tools.cloudstorage.RetryParams;

public class BlobAccessorGcsImpl implements BlobAccessor {

	private static final GcsService gcsService =
			GcsServiceFactory.createGcsService( RetryParams.getDefaultInstance() );
	
	private final String bucketName;
	
	
	public BlobAccessorGcsImpl( String bucketName ) {
		this.bucketName = bucketName;
	}
	
	
	@Override
	public BlobEntry newBlob( String fileName ) {
		return new BlobEntryGcsImpl( fileName );
	}

	@Override
	public BlobEntry newBlob( String fileName, byte[] data, String mimeType ) {
		return new BlobEntryGcsImpl( fileName, data, mimeType );
	}

	@Override
	public List<String> getNameList( String prefix )
			throws IOException {
		
		ListOptions.Builder options = new ListOptions.Builder();
		options.setPrefix( prefix );
		
		ListResult result = gcsService.list( bucketName, options.build() );
		
		List<String> fileNameList = new LinkedList<>();
		while( result.hasNext() ) {
			String fileName = result.next().getName();
			if( ! fileName.equals( prefix ) )
				fileNameList.add( fileName.substring( prefix.length() ) );
		}
		return fileNameList;
	}

	@Override
	public BlobEntry getBlob( String fileName ) throws IOException {

		GcsFilename gcsFileName
				= new GcsFilename( bucketName, fileName );
		GcsFileMetadata gcsFileMetadata
				= gcsService.getMetadata( gcsFileName );
		
		if( gcsFileMetadata == null )
			return null;
		
		if( gcsFileMetadata.getLength() == 0 )
			return null;
		
		GcsInputChannel gcsInputChannel
				= gcsService.openReadChannel( gcsFileName, 0 );
		
		ByteBuffer byteBuffer
				= ByteBuffer.allocate( (int) gcsFileMetadata.getLength() );
		gcsInputChannel.read( byteBuffer );
	
		return new BlobEntryGcsImpl( byteBuffer, gcsFileMetadata );
	}

	@Override
	public void createOrUpdateBlob( BlobEntry blobEntry )
			throws IOException {
		
		GcsFilename gcsFileName
				= new GcsFilename( bucketName, blobEntry.getName() );

		Builder builder = new GcsFileOptions.Builder();
		if( blobEntry.getMimeType() != null )
			builder.mimeType( blobEntry.getMimeType() );
		if( blobEntry.getCacheControl() != null )
			builder.cacheControl( blobEntry.getCacheControl() );
		GcsFileOptions gcsFileOptions = builder.build();

		GcsOutputChannel gcsOutputChannel = gcsService
				.createOrReplace( gcsFileName , gcsFileOptions );
		
		gcsOutputChannel.write( ByteBuffer.wrap( blobEntry.getData() ) );
		gcsOutputChannel.close();
	}

}
