package com.claymus.websitewidget.html;

import com.claymus.websitewidget.WebsiteWidgetHelper;
import com.claymus.websitewidget.html.gae.HtmlWidgetEntity;

public class HtmlWidgetHelper extends WebsiteWidgetHelper<
		HtmlWidget,
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
