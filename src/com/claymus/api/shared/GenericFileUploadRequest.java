package com.claymus.api.shared;

import com.claymus.api.annotation.Validate;

@SuppressWarnings("serial")
public class GenericFileUploadRequest extends GenericRequest {

	@Validate( required = true )
	private String name;

	@Validate( required = true )
	private byte[] data;

	@Validate( required = true )
	private String mimeType;
	
	private String cacheControl;

	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
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
	
	public String getCacheControl(){
		return cacheControl;
	}
	
	public void setCacheControl( String cacheControl ){
		this.cacheControl = cacheControl;
	}
	
}