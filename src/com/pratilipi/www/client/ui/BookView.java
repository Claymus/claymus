package com.pratilipi.www.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.pratilipi.service.shared.data.BookData;

public abstract class BookView extends Composite {

	public abstract void setBookData( BookData book );

}
