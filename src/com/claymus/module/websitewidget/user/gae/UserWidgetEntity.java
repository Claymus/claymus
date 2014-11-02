package com.claymus.module.websitewidget.user.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.WebsiteWidgetEntity;
import com.claymus.module.websitewidget.user.UserWidget;

@SuppressWarnings("serial")
@PersistenceCapable
public class UserWidgetEntity extends WebsiteWidgetEntity implements UserWidget { }
