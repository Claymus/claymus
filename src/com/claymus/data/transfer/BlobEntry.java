package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface BlobEntry extends Serializable {
	
	String getName();

	void setName( String name );

	byte[] getData();
	
	void setData( byte[] byteArray );
	
	long getDataLength();
	
	String getMimeType();

	void setMimeType( String mimeType );
	
	String getCacheControl();
	
	void setCacheControl( String cacheControl );

	String getETag();
	
	Date getLastModified();

	Map<String, String> getMetaData();

	String getMetaData( String key );
	
}
