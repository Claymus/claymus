package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SendQueryRequest implements IsSerializable {

	private String name;
	
	private String email;
	
	private String query;

	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery( String query ) {
		this.query = query;
	}

}
