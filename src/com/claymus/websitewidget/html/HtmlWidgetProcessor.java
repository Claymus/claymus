package com.claymus.websitewidget.html;

import com.claymus.websitewidget.WebsiteWidgetProcessor;

public class HtmlWidgetProcessor extends WebsiteWidgetProcessor<HtmlWidget> {

	@Override
	public String getTemplateName() {
		return "com/claymus/module/websitewidget/html/HtmlWidget.ftl";
	}

}
