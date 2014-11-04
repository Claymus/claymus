package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class Search extends Composite {

	private final Panel searchBoxPanel = new HorizontalPanel();
	private Panel inputGroupPanel = new FlowPanel();
	private Panel inputButtonGrpPanel = new FlowPanel();
	private Button inputGroupButton = new Button();
	private final TextBox searchTextBox = new TextBox();
	private final Button searchButton = new Button( "Search" );
	private final Element glyphicon = Document.get().createSpanElement();
	private Element unOrderedListElement = Document.get().createULElement();
	
	public Search(){
		
		glyphicon.addClassName( "glyphicon glyphicon-search" );
		
		Element searchPratilipiListElement = Document.get().createLIElement();
		searchPratilipiListElement.setInnerHTML( "<a href='#'>All</a>" );
		searchPratilipiListElement.setAttribute( "onclick", "onClick( this )" );
		
		Element searchBookListElement = Document.get().createLIElement();
		searchBookListElement.setInnerHTML( "<a href='#'>Book</a>" );
		searchBookListElement.setAttribute( "onclick", "onClick( this )" );
		
		Element searchPoemListElement = Document.get().createLIElement();
		searchPoemListElement.setInnerHTML( "<a href='#'>Poem</a>" );
		searchPoemListElement.setAttribute( "onclick", "onClick( this )" );
		
		Element searchStoryListElement = Document.get().createLIElement();
		searchStoryListElement.setInnerHTML( "<a href='#'>Story</a>" );
		searchStoryListElement.setAttribute( "onclick", "onClick( this )" );
		
		Element searchArticleListElement = Document.get().createLIElement();
		searchArticleListElement.setInnerHTML( "<a href='#'>Article</a>" );
		searchArticleListElement.setAttribute( "onclick", "onClick( this )" );
		
		unOrderedListElement.addClassName( "dropdown-menu" );
		unOrderedListElement.setAttribute("role", "menu" );
		unOrderedListElement.appendChild( searchPratilipiListElement );
		unOrderedListElement.appendChild( searchArticleListElement );
		unOrderedListElement.appendChild( searchBookListElement );
		unOrderedListElement.appendChild( searchPoemListElement );
		unOrderedListElement.appendChild( searchStoryListElement );
		
		inputGroupButton.getElement().setId( "inputgroupbutton" );
		inputGroupButton.addStyleName( "btn btn-default dropdown-toggle" );
		inputGroupButton.getElement().setAttribute( "data-toggle", "dropdown" );
		inputGroupButton.getElement().setInnerHTML( "All <span class=\"caret\"></span>");
		
		inputButtonGrpPanel.setStyleName( "input-group-btn" ); 
		inputButtonGrpPanel.add( inputGroupButton );
		inputButtonGrpPanel.getElement().appendChild( unOrderedListElement );
		
		searchTextBox.addStyleName( "form-control" );
		searchTextBox.getElement().setAttribute( "name" , "searchBox");
		searchTextBox.getElement().getStyle().setDisplay( Display.INLINE_BLOCK );

		inputGroupPanel.setStyleName( "input-group" );
		inputGroupPanel.add( inputButtonGrpPanel );
		inputGroupPanel.add( searchTextBox );
		
		searchButton.setStyleName( "btn btn-info" );
		searchButton.setWidth( "100%" );
		
		searchBoxPanel.setStyleName( "col-md-6 " );
		searchBoxPanel.add( inputGroupPanel );
		searchBoxPanel.add( searchButton );
		
		initWidget( searchBoxPanel );
	}
	
	public void addSearchButtonClickHandler( ClickHandler searchButtonClickHandler ){
		searchButton.addClickHandler( searchButtonClickHandler );
	}
	
	public void addSearchBoxKeyDownHandler( KeyDownHandler searchBoxKeyDownHandler ){
		searchTextBox.addKeyDownHandler( searchBoxKeyDownHandler );
	}
	
	public String getSearchQuery(){
		return searchTextBox.getText();
	}
	
	public void setSearchQuery( String searchQuery ){
		searchTextBox.setText( searchQuery );
	}
	
	public Boolean validate(){
		if( searchTextBox.getText().length() == 0 )
			return false;
		else
			return true;
	}
	
	public void hideSearchButton( Boolean visible ){
		searchButton.setVisible( visible );
	}

	public String getPrefix() {
		return inputGroupButton.getText().trim();
	}
	
	public void setPrefix( String prefix ) {
		inputGroupButton.getElement().setInnerHTML( prefix + " " + "<span class=\"caret\"></span>");
	}
}
