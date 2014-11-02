package com.claymus.service.shared;

import com.claymus.service.shared.data.UserData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class InviteUserRequest implements IsSerializable {

	private UserData userData;


	@SuppressWarnings("unused")
	private InviteUserRequest() {}
	
	public InviteUserRequest( UserData useData ) {
		this.userData = useData;
	}
	
	
	public UserData getUser() {
		return this.userData;
	}

}
