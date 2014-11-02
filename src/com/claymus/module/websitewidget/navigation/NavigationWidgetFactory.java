package com.claymus.module.websitewidget.navigation;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;
import com.claymus.module.websitewidget.navigation.gae.NavigationWidgetEntity;

public class NavigationWidgetFactory
		implements WebsiteWidgetFactory<NavigationWidget, NavigationWidgetProcessor> {
	
	public static NavigationWidget newNavigationWidget() {
		return new NavigationWidgetEntity();
	}

}
