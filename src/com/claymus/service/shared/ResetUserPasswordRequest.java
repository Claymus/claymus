package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ResetUserPasswordRequest implements IsSerializable {

	private String userEmail;

	
	@SuppressWarnings("unused")
	private ResetUserPasswordRequest() {}
	
	public ResetUserPasswordRequest( String email ) {
		this.userEmail = email;
	}
	
	
	public String getUserEmail() {
		return userEmail;
	}

}
