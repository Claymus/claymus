package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface BlobEntry extends Serializable {
	
	String getName();

	byte[] getData();

	String getMimeType();

	String getETag();
	
	Date getLastModified();

}
