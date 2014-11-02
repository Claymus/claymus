package com.claymus.service.shared;

import com.claymus.service.shared.data.UserData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class RegisterUserRequest implements IsSerializable {

	private UserData userData;


	@SuppressWarnings("unused")
	private RegisterUserRequest() {}
	
	public RegisterUserRequest( UserData userData ) {
		this.userData = userData;
	}
	
	
	public UserData getUser() {
		return this.userData;
	}
	
}
