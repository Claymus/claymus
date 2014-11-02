package com.claymus.commons.client.ui;

import com.claymus.service.shared.data.PageData;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

public abstract class PageDataInputView extends Composite {
	
	public abstract void add( Button child );

	
	public abstract boolean validateInputs();

	public abstract void setEnabled( boolean enabled );
	
	public abstract PageData getPageData();

	public abstract void setPageData( PageData pageData );
	
}
