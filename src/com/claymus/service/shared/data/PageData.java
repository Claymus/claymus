package com.claymus.service.shared.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PageData implements IsSerializable {

	private Long id;
	
	private String uri;
	
	private String uriAlias;
	
	private String title;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri( String uri ) {
		this.uri = uri;
	}

	public String getUriAlias() {
		return uriAlias;
	}

	public void setUriAlias( String uriAlias ) {
		this.uriAlias = uriAlias;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
	}

}
