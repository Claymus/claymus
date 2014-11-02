package com.claymus.commons.server;

public class Access {

	private String id;
	
	private boolean defaultAccess;
	
	private String description;
	
	
	public Access( String id, boolean defaultAccess, String description ) {
		this.id = id;
		this.defaultAccess = defaultAccess;
		this.description = description;
	}


	public String getId() {
		return id;
	}

	public boolean getDefault() {
		return defaultAccess;
	}

	public String getDescription() {
		return description;
	}

}
