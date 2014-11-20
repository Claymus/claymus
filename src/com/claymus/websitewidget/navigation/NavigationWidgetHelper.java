package com.claymus.websitewidget.navigation;

import com.claymus.websitewidget.WebsiteWidgetHelper;
import com.claymus.websitewidget.navigation.gae.NavigationWidgetEntity;
import com.claymus.websitewidget.navigation.shared.NavigationWidgetData;

public class NavigationWidgetHelper extends WebsiteWidgetHelper<
		NavigationWidget,
		NavigationWidgetData,
		NavigationWidgetProcessor> {
	
	@Override
	public String getModuleName() {
		return "Navigation";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	public static NavigationWidget newNavigationWidget() {
		return new NavigationWidgetEntity();
	}
	
}
