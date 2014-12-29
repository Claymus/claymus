package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface BlobEntry extends Serializable {
	
	String getName();

	byte[] getData();
	
	void setData( byte[] byteArray );
	
	long getDataLength();
	
	String getMimeType();

	void setMimeType( String mimeType );

	String getETag();
	
	Date getLastModified();

	Map<String, String> getMetaData();

	String getMetaData( String key );
	
}
