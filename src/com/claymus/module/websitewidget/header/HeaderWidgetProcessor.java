package com.claymus.module.websitewidget.header;

import com.claymus.module.websitewidget.WebsiteWidgetProcessor;

public class HeaderWidgetProcessor extends WebsiteWidgetProcessor<HeaderWidget> {

	@Override
	public String getTemplateName() {
		return "com/claymus/module/websitewidget/header/HeaderWidget.ftl";
	}

}
