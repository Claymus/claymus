package com.claymus.commons.client.ui;

import java.util.LinkedList;
import java.util.List;

import com.claymus.service.shared.data.PageData;
import com.google.gwt.user.client.ui.Button;

public class PageDataInputViewModalImpl extends PageDataInputView {
	
	private final Modal modal = new Modal();
	private final PageDataInputView pageDataInputView = new PageDataInputViewImpl();
	private final List<Button> addedButtonList = new LinkedList<>();
	
	
	public PageDataInputViewModalImpl() {
		
		modal.setTitle( "New Page" );
		modal.add( pageDataInputView );
		
		initWidget( modal );
	}
	
	@Override
	public void setVisible( boolean visible ) {
		if( visible )
			modal.show();
		else
			modal.hide();
	}
	
	@Override
	public void add( Button button ) {
		modal.add( button );
		addedButtonList.add( button );
	}

	@Override
	public boolean validateInputs() {
		return pageDataInputView.validateInputs();
	}

	@Override
	public void setEnabled( boolean enabled ) {
		pageDataInputView.setEnabled( enabled );
		for( Button button : addedButtonList )
			button.setEnabled( enabled );
	}

	@Override
	public PageData getPageData() {
		return pageDataInputView.getPageData();
	}

	@Override
	public void setPageData( PageData pageData ) {
		pageDataInputView.setPageData( pageData );
		modal.setTitle( "Edit Page" );
	}

}
