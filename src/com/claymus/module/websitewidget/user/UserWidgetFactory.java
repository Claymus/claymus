package com.claymus.module.websitewidget.user;

import com.claymus.module.websitewidget.WebsiteWidgetFactory;
import com.claymus.module.websitewidget.user.gae.UserWidgetEntity;

public class UserWidgetFactory
		implements WebsiteWidgetFactory<UserWidget, UserWidgetProcessor> {
	
	public static UserWidget newUserWidget() {
		return new UserWidgetEntity();
	}

}
