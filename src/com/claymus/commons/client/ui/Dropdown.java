package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class Dropdown extends Composite {
	
	private Panel panel = new FlowPanel();
	private Anchor anchor = new Anchor();
	private UListElement ulElement = Document.get().createULElement();

	
	public Dropdown() {
		
		String id = Document.get().createUniqueId();
		anchor.setHref( "#" );
		anchor.getElement().setAttribute( "id", id );
		ulElement.setAttribute( "aria-labelledby", id );
		
		
		// Composing the widget
		panel.add( anchor );
		panel.getElement().appendChild( ulElement );

		
		// Setting required style classes
		panel.setStyleName( "dropdown" );
		panel.getElement().setAttribute( "style", "display:inline;" );

		anchor.getElement().setAttribute( "style", "text-decoration:none;" );
		anchor.getElement().setAttribute( "role", "button" );
		anchor.getElement().setAttribute( "data-toggle", "dropdown" );

		ulElement.setAttribute( "class", "dropdown-menu arrow" );
		ulElement.setAttribute( "style", "margin-top:0px;" );
		ulElement.setAttribute( "role", "menu" );

		
		initWidget( panel );
	}

	public Dropdown( String title ) {
		this();
		setTitle( title );
	}

	public void setTitle( String html ) {
		anchor.setHTML( html + "<span class='caret'>" );
	}

	public void add( Anchor anchor ) {
		LIElement liElement = Document.get().createLIElement();
		liElement.setAttribute( "role", "presentation" );

		anchor.getElement().setAttribute( "role", "menuitem" );
		anchor.getElement().setAttribute( "tabindex", "-1" );
		
		// TODO: Remove this hack
		panel.add( anchor ); // Hack: To make click handler on anchor work
		liElement.appendChild( anchor.getElement() );
		ulElement.appendChild( liElement );
	}

}
