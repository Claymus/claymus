package com.claymus.module.websitewidget.header.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.WebsiteWidgetEntity;
import com.claymus.module.websitewidget.header.HeaderWidget;

@SuppressWarnings("serial")
@PersistenceCapable
public class HeaderWidgetEntity extends WebsiteWidgetEntity implements HeaderWidget {

	@Persistent( column = "X_COL_0" )
	private String brand;
	
	@Persistent( column = "X_COL_1" )
	private String tagLine;

	@Persistent( column = "X_COL_2" )
	private Object[][] leftNavItems;

	@Persistent( column = "X_COL_3" )
	private Object[][] rightNavItems;

	
	@Override
	public String getBrand() {
		return brand;
	}

	@Override
	public void setBrand( String brand ) {
		this.brand = brand;
	}

	@Override
	public String getTagLine() {
		return tagLine;
	}

	@Override
	public void setTagLine( String tagLine ) {
		this.tagLine = tagLine;
	}
	
	@Override
	public Object[][] getLeftNavItems() {
		return leftNavItems;
	}

	@Override
	public void setLeftNavItems( Object[][] leftNavItems ) {
		this.leftNavItems = leftNavItems;
	}

	@Override
	public Object[][] getRightNavItems() {
		return rightNavItems;
	}

	@Override
	public void setRightNavItems( Object[][] rightNavItems ) {
		this.rightNavItems = rightNavItems;
	}

}
