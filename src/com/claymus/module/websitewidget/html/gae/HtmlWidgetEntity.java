package com.claymus.module.websitewidget.html.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.claymus.data.access.gae.WebsiteWidgetEntity;
import com.claymus.module.websitewidget.html.HtmlWidget;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable
public class HtmlWidgetEntity extends WebsiteWidgetEntity implements HtmlWidget {

	@Persistent( column = "X_COL_0" )
	private String title;
	
	@Persistent( column = "X_COL_1" )
	private Text html;

	
	@Override
	public String getHtml() {
		return html == null ? null : html.getValue();
	}

	@Override
	public void setHtml( String html ) {
		this.html = new Text( html );
	}

}
