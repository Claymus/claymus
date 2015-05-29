package com.claymus.websitewidget.html;

import com.claymus.websitewidget.WebsiteWidgetProcessor;

public class HtmlWidgetProcessor extends WebsiteWidgetProcessor<HtmlWidget> {
	
	protected CacheLevel getCacheLevel() {
		return CacheLevel.GLOBAL;
	}

	protected boolean hasBasicMode() {
		return false;
	}
	
}
