package com.claymus.pagecontent.html;

import com.claymus.data.transfer.PageContent;

public interface HtmlContent extends PageContent {

	String getTitle();
	
	void setTitle( String title );

	String getContent();
	
	void setContent( String html );
	
}
