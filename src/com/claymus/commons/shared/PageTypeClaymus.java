package com.claymus.commons.shared;

public enum PageTypeClaymus implements PageType {

	NONE( "/page/" ),
	BLOG( "/blog/" );
	

	private String urlPrefix;
	
	
	private PageTypeClaymus( String urlPrefix ) {
		this.urlPrefix = urlPrefix;
	}
	
	public String getUrlPrefix() {
		return this.urlPrefix;
	}
	
}
