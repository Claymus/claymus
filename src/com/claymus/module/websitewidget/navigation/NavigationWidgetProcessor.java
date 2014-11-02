package com.claymus.module.websitewidget.navigation;

import com.claymus.module.websitewidget.WebsiteWidgetProcessor;

public class NavigationWidgetProcessor extends WebsiteWidgetProcessor<NavigationWidget> {

	@Override
	public String getTemplateName() {
		return "com/claymus/module/websitewidget/navigation/NavigationWidget.ftl";
	}
	
}
