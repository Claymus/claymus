package com.claymus.pagecontent.html.shared;

import com.claymus.service.shared.data.PageContentData;

public class HtmlContentData extends PageContentData {

	private String title;
	
	private String content;

	
	public String getTitle() {
		return title;
	};
	
	public void setTitle( String title ) {
		this.title = title;
	};
	
	public String getContent() {
		return content;
	};
	
	public void setContent( String content ) {
		this.content = content;
	};
	
}
