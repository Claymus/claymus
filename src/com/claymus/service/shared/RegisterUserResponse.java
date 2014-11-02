package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RegisterUserResponse implements IsSerializable { 
	
	private String message;
	
	public RegisterUserResponse() {}
	
	public RegisterUserResponse( String message ) {
		this.message = message;
	}
	
	
	public String getMessage() {
		return this.message;
	}
	
}
