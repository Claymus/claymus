package com.claymus.websitewidget.html;

import com.claymus.data.transfer.WebsiteWidget;

public interface HtmlWidget extends WebsiteWidget {
	
	String getHtml();
	
	void setHtml( String html );

}
