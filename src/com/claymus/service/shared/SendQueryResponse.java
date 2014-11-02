package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SendQueryResponse implements IsSerializable {

	private String message;


	@SuppressWarnings("unused")
	private SendQueryResponse() {}
	
	public SendQueryResponse( String message ) {
		this.message = message;
	}

	
	public String getMessage() {
		return message;
	}
	
}
