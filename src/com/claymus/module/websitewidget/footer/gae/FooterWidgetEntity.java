package com.claymus.module.websitewidget.footer.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.WebsiteWidgetEntity;
import com.claymus.module.websitewidget.footer.FooterWidget;

@SuppressWarnings("serial")
@PersistenceCapable
public class FooterWidgetEntity extends WebsiteWidgetEntity implements FooterWidget {

	@Persistent( column = "X_COL_0" )
	private String[][] links;

	@Persistent( column = "X_COL_1" )
	private String copyrightNote;
	
	
	@Override
	public String getCopyrightNote() {
		return copyrightNote;
	}

	@Override
	public void setCopyrightNote( String copyrightNote ) {
		this.copyrightNote = copyrightNote;
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
