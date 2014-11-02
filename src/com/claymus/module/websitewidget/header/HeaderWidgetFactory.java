package com.claymus.module.websitewidget.header;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;
import com.claymus.module.websitewidget.header.gae.HeaderWidgetEntity;

public class HeaderWidgetFactory
		implements WebsiteWidgetFactory<HeaderWidget, HeaderWidgetProcessor> {
	
	public static HeaderWidget newHeaderWidget() {
		return new HeaderWidgetEntity();
	}

}
