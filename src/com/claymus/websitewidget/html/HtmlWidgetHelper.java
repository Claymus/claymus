package com.claymus.websitewidget.html;

import com.claymus.websitewidget.WebsiteWidgetHelper;
import com.claymus.websitewidget.html.gae.HtmlWidgetEntity;
import com.claymus.websitewidget.html.shared.HtmlWidgetData;

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
