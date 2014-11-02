package com.claymus.service.shared;

import com.claymus.service.shared.data.FacebookLoginData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class FacebookLoginUserRequest implements IsSerializable{

	private FacebookLoginData facebookLoginData = new FacebookLoginData();
	
	@SuppressWarnings("unused")
	private FacebookLoginUserRequest() {}
	
	public FacebookLoginUserRequest( FacebookLoginData facebookLoginData ) {
		this.facebookLoginData = facebookLoginData;
	}
	
	public FacebookLoginData getFacebookLoginData() {
		return this.facebookLoginData;
	}
	
}
