package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UpdateUserPasswordRequest implements IsSerializable {

	private String userEmail;
	
	private String token;

	private String currentPassword;
	
	private String newPassword;

	
	@SuppressWarnings("unused")
	private UpdateUserPasswordRequest() {}
	
	public UpdateUserPasswordRequest(
			String userEmail, String token,
			String currentPassword, String newPassword ) {
		
		this.userEmail = userEmail;
		this.token = token;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getToken() {
		return token;
	}
	
	public String getCurrentPassword() {
		return currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

}
