package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class Search extends Composite {

	private final Panel searchBox = new HorizontalPanel();
	private final TextBox textBox = new TextBox();
	private final Button searchButton = new Button( "Search" );
	private final Element glyphicon = Document.get().createSpanElement();
	
	public Search(){
		
		glyphicon.addClassName( "glyphicon glyphicon-search" );
		
		textBox.getElement().setAttribute( "placeholder", "Search Pratilipi" );
		textBox.addStyleName( "form-control" );
		
		
		searchButton.setStyleName( "btn btn-info" );
		searchButton.setWidth( "100%" );
		
		searchBox.setStyleName( "col-md-6 " );
		searchBox.getElement().setAttribute( "cellspacing", "10" );
		searchBox.getElement().setAttribute( "style", "border-spacing: 0; border-collapse:collapse;" );
		searchBox.add( textBox );
		searchBox.add( searchButton );
		
		initWidget( searchBox );
	}
	
	public void addSearchButtonClickHandler( ClickHandler searchButtonClickHandler ){
		searchButton.addClickHandler( searchButtonClickHandler );
	}
	
	public void addSearchBoxKeyDownHandler( KeyDownHandler searchBoxKeyDownHandler ){
		textBox.addKeyDownHandler( searchBoxKeyDownHandler );
	}
	
	public String getSearchQuery(){
		return textBox.getText();
	}
	
	public void setSearchQuery( String searchQuery ){
		textBox.setText( searchQuery );
	}
	
	public Boolean validate(){
		if( textBox.getText().length() == 0 )
			return false;
		else
			return true;
	}
	
	public void hideSearchButton( Boolean visible ){
		searchButton.setVisible( visible );
	}
}
