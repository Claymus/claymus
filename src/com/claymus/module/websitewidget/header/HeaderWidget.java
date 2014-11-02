package com.claymus.module.websitewidget.header;

import com.claymus.data.transfer.WebsiteWidget;

public interface HeaderWidget extends WebsiteWidget {
	
	String getBrand();
	
	void setBrand( String brand );
	
	String getTagLine();
	
	void setTagLine( String tagLine );
	
	Object[][] getLeftNavItems();
	
	void setLeftNavItems( Object[][] leftNavItems );

	Object[][] getRightNavItems();
	
	void setRightNavItems( Object[][] rightNavItems );

}
