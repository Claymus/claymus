package com.claymus.websitewidget.footer;

import com.claymus.data.transfer.WebsiteWidget;

public interface FooterWidget extends WebsiteWidget {
	
	String[][] getLinks();
	
	void setLinks( String[][] links );
	
	String getCopyrightNote();
	
	void setCopyrightNote( String copyrightNote );
	
}
