package com.claymus.data.transfer.client;


public class PageData {

	private Long id;
	private boolean hasId;
	
	private String uri;
	private boolean hasUri;
	
	private String uriAlias;
	private boolean hasUriAlias;

	private String title;
	private boolean hasTitle;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId( Long id ) {
		this.id = id;
		this.hasId = true;
	}
	
	public boolean hasId() {
		return hasId;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri( String uri ) {
		this.uri = uri;
		this.hasUri = true;
	}

	public boolean hasUri() {
		return hasUri;
	}
	
	public String getUriAlias() {
		return uriAlias;
	}

	public void setUriAlias( String uriAlias ) {
		this.uriAlias = uriAlias;
		this.hasUriAlias = true;
	}

	public boolean hasUriAlias() {
		return hasUriAlias;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
		this.hasTitle = true;
	}

	public boolean hasTitle() {
		return hasTitle;
	}

}
