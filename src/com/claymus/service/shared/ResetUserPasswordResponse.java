package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResetUserPasswordResponse implements IsSerializable {
	private String message;
	
	public ResetUserPasswordResponse() {}
	
	public ResetUserPasswordResponse( String message ) {
		this.message = message;
	}
	
	
	public String getMessage() {
		return this.message;
	}
}