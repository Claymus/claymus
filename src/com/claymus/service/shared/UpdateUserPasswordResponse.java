package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UpdateUserPasswordResponse implements IsSerializable { 
	
	private String message;
	
	public UpdateUserPasswordResponse() {}
	
	public UpdateUserPasswordResponse( String message ) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
