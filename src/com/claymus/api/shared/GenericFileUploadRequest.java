package com.claymus.api.shared;

import com.claymus.api.annotation.Validate;

@SuppressWarnings("serial")
public class GenericFileUploadRequest extends GenericRequest {

	@Validate( required = true )
	private String fileName;

	@Validate( required = true )
	private byte[] data;

	@Validate( required = true )
	private String mimeType;

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName( String fileName ) {
		this.fileName = fileName;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData( byte[] data ) {
		this.data = data;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType( String mimeType ) {
		this.mimeType = mimeType;
	}
	
}