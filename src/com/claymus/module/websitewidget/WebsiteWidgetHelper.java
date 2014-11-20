package com.claymus.module.websitewidget;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.service.shared.data.WebsiteWidgetData;

public abstract class WebsiteWidgetHelper<
		P extends WebsiteWidget,
		Q extends WebsiteWidgetData,
		R extends WebsiteWidgetProcessor<P>> {
	
	public abstract String getModuleName();
	
	public abstract Double getModuleVersion();

	public Access[] getAccessList() {
		return new Access[]{};
	};
	
	public boolean hasRequestAccessToAddWidget( HttpServletRequest request ) {
		return false;
	};
	
	public boolean hasRequestAccessToUpdateWidget( HttpServletRequest request ) {
		return false;
	};
	
	public P createOrUpdateFromData( Q websiteWidgetData, P websiteWidget ) {
		return websiteWidget;
	};

}
