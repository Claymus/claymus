package com.claymus.module.websitewidget.footer;

import com.claymus.module.websitewidget.WebsiteWidgetProcessor;

public class FooterWidgetProcessor extends WebsiteWidgetProcessor<FooterWidget> {

	@Override
	public String getTemplateName() {
		return "com/claymus/module/websitewidget/footer/FooterWidget.ftl";
	}

}
