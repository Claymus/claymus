package com.claymus.api.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GenericRequest implements Serializable {

	private String accessToken;


	@SuppressWarnings("unused")
	private GenericRequest() {}

	public GenericRequest( String accessToken ) {
		this.accessToken = accessToken;
	}

	public final String getAccessToken() {
		return accessToken;
	}

}
