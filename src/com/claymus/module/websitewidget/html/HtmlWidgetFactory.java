package com.claymus.module.websitewidget.html;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;
import com.claymus.module.websitewidget.html.gae.HtmlWidgetEntity;

public class HtmlWidgetFactory
		implements WebsiteWidgetFactory<HtmlWidget, HtmlWidgetProcessor> {
	
	public static HtmlWidget newHtmlWidget() {
		return new HtmlWidgetEntity();
	}

}
