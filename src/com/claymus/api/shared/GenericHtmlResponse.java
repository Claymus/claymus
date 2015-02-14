package com.claymus.api.shared;

@SuppressWarnings("serial")
public class GenericHtmlResponse extends GenericResponse {

	private String html;
	
	
	@SuppressWarnings("unused")
	private GenericHtmlResponse() {}
	
	public GenericHtmlResponse( String html ) {
		this.html = html;
	}
	
	
	public String getHtml() {
		return html;
	}

}