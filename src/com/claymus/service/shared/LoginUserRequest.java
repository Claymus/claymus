package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginUserRequest implements IsSerializable {

	private String email;

	private String password;
	

	@SuppressWarnings("unused")
	private LoginUserRequest() {}
	
	public LoginUserRequest( String email, String password ) {
		this.email = email;
		this.password = password;
	}
	
	
	public String getLoginId() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
