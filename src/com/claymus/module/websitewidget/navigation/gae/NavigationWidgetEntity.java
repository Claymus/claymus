package com.claymus.module.websitewidget.navigation.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.WebsiteWidgetEntity;
import com.claymus.module.websitewidget.navigation.NavigationWidget;

@SuppressWarnings("serial")
@PersistenceCapable
public class NavigationWidgetEntity extends WebsiteWidgetEntity implements NavigationWidget {

	@Persistent( column = "X_COL_0" )
	private String title;
	
	@Persistent( column = "X_COL_1" )
	private String[][] links;

	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle( String title ) {
		this.title = title;
	}

	@Override
	public String[][] getLinks() {
		return links;
	}

	@Override
	public void setLinks( String[][] links ) {
		this.links = links;
	}

}
