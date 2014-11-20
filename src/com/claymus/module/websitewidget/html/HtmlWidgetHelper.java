package com.claymus.module.websitewidget.html;

import com.claymus.module.websitewidget.WebsiteWidgetHelper;
import com.claymus.module.websitewidget.html.gae.HtmlWidgetEntity;
import com.claymus.module.websitewidget.html.shared.HtmlWidgetData;

public class HtmlWidgetHelper extends WebsiteWidgetHelper<
		HtmlWidget,
		HtmlWidgetData,
		HtmlWidgetProcessor> {
	
	@Override
	public String getModuleName() {
		return "HTML";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	
	public static HtmlWidget newHtmlWidget() {
		return new HtmlWidgetEntity();
	}
	
}
