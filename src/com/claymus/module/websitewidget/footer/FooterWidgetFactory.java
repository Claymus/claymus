package com.claymus.module.websitewidget.footer;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;
import com.claymus.module.websitewidget.footer.gae.FooterWidgetEntity;

public class FooterWidgetFactory
		implements WebsiteWidgetFactory<FooterWidget, FooterWidgetProcessor> {
	
	public static FooterWidget newFooterWidget() {
		return new FooterWidgetEntity();
	}

}
