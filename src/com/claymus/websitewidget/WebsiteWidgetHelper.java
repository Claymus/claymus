package com.claymus.websitewidget;

import com.claymus.commons.server.Access;
import com.claymus.data.transfer.WebsiteWidget;

public abstract class WebsiteWidgetHelper<
		P extends WebsiteWidget,
		Q extends WebsiteWidgetProcessor<P>> {
	
	public abstract String getModuleName();
	
	public abstract Double getModuleVersion();

	public Access[] getAccessList() {
		return new Access[]{};
	};
	
	public boolean hasRequestAccessToAddWidget() {
		return false;
	};
	
	public boolean hasRequestAccessToUpdateWidget() {
		return false;
	};
	
}
