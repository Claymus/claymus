package com.claymus.module.websitewidget.navigation;

import com.claymus.data.transfer.WebsiteWidget;

public interface NavigationWidget extends WebsiteWidget {
	
	String getTitle();
	
	void setTitle( String title );
	
	String[][] getLinks();
	
	void setLinks( String[][] links );
	
}
