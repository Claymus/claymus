package com.claymus.data.access;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.transfer.BlobEntry;

public interface BlobAccessor {
	
	BlobEntry newBlob( String fileName );

	@Deprecated
	BlobEntry newBlob( String fileName, byte[] data, String mimeType );

	BlobEntry getBlob( String fileName )
			throws IOException;

	List<String> getNameList( String prefix )
			throws IOException;
	
	@Deprecated
	void createBlob( String fileName, String mimeType, byte[] bytes )
			throws IOException;

	@Deprecated
	void createBlob( String fileName, String mimeType, byte[] bytes, String acl, Map<String, String> metaDataMap )
			throws IOException;

	@Deprecated
	void createBlob( String fileName, String mimeType, String content, Charset charset )
			throws IOException;

	@Deprecated
	boolean createBlob( HttpServletRequest request, String fileName );

	void createOrUpdateBlob( BlobEntry blobEntry )
			throws IOException;
	
}
