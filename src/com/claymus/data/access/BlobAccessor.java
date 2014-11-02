package com.claymus.data.access;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.transfer.BlobEntry;

public interface BlobAccessor {
	
	@Deprecated
	String createUploadUrl( String fileName );

	@Deprecated
	boolean createBlob( HttpServletRequest request, String fileName );

	void createBlob( String fileName, String mimeType, byte[] bytes )
			throws IOException;

	void createBlob( String fileName, String mimeType, byte[] bytes, String acl, Map<String, String> metaDataMap )
			throws IOException;

	List<String> getFilenameList( String prefix ) throws IOException;
	
	void createBlob( String fileName, String mimeType, String content, Charset charset )
			throws IOException;

	void updateBlob( BlobEntry blobEntry, byte[] bytes )
			throws IOException;
	
	void updateBlob( BlobEntry blobEntry, String content, Charset charset )
			throws IOException;

	BlobEntry getBlob( String fileName )
			throws IOException;

	@Deprecated
	void serveBlob(
			String fileName,
			HttpServletResponse response ) throws IOException;

}
