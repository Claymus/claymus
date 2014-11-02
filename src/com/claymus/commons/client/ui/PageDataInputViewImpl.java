package com.claymus.commons.client.ui;

import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.claymus.service.shared.data.PageData;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class PageDataInputViewImpl extends PageDataInputView {
	
	private Panel panel = new FlowPanel();
	
	private TextInputFormField uriInput = new TextInputFormField();
	private TextInputFormField titleInput = new TextInputFormField();
	
	private PageData pageData = null;

	
	public PageDataInputViewImpl() {
		
	    uriInput.setPlaceholder( "URL" );
	    uriInput.setRequired( true );
	    titleInput.setPlaceholder( "Title" );
		
	
		// Composing the widget
		panel.add( uriInput );
		panel.add( titleInput );

		
		// Setting required style classes
		panel.getElement().setAttribute( "style", "margin:15px" );

		
		initWidget( panel );
	}
	
	
	@Override
	public void add( Button button ) {
		panel.add( button );
	}

	@Override
	public boolean validateInputs() {
		boolean validated = true;
		validated = uriInput.validate() && validated;
		validated = titleInput.validate() && validated;
		return validated;
	}

	@Override
	public void setEnabled(boolean enabled) {
		uriInput.setEnabled( enabled );
		titleInput.setEnabled( enabled );
	}

	@Override
	public PageData getPageData() {
		PageData pageData = new PageData();
		pageData.setId( this.pageData == null ? null : this.pageData.getId() );
		
		pageData.setUriAlias( uriInput.getText() );
		pageData.setTitle( titleInput.getText() );
		
		return pageData;
	}

	@Override
	public void setPageData( PageData pageData ) {
		this.pageData = pageData;
		
		uriInput.setText( pageData.getUri() );
		titleInput.setText( pageData.getTitle() );
	}

}
