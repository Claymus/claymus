package com.claymus.websitewidget.footer;

import com.claymus.websitewidget.WebsiteWidgetHelper;
import com.claymus.websitewidget.footer.gae.FooterWidgetEntity;
import com.claymus.websitewidget.footer.shared.FooterWidgetData;

public class FooterWidgetHelper extends WebsiteWidgetHelper<
		FooterWidget,
		FooterWidgetData,
		FooterWidgetProcessor> {
	
	@Override
	public String getModuleName() {
		return "Footer";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	
	public static FooterWidget newFooterWidget() {
		return new FooterWidgetEntity();
	}
	
}
