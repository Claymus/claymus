package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Accordion extends Composite {

	private final Panel panel = new FlowPanel();
	
	private final Panel headingPanel = new SimplePanel();
	private final Panel collapsePanel = new SimplePanel();
	
	private final Anchor titleAnchor = new Anchor();
	private final HeadingElement titleElement = Document.get().createHElement( 4 );
	
	private final Panel bodyPanel = new FlowPanel();

	
	public Accordion() {

		String id = Document.get().createUniqueId();
		titleAnchor.setHref( "#" + id );
		collapsePanel.getElement().setAttribute( "id", id );

		
		// Composing the widget
		panel.add( headingPanel );
		panel.add( collapsePanel );
	
		headingPanel.add( titleAnchor );
		titleAnchor.getElement().appendChild( titleElement );
		
		collapsePanel.add( bodyPanel );


		// Setting required style classes
		panel.addStyleName( "panel panel-default" );
		
		headingPanel.setStyleName( "panel-heading" );
		collapsePanel.setStyleName( "panel-collapse collapse" );
		
		titleAnchor.getElement().setAttribute( "data-toggle", "collapse" );
		titleAnchor.getElement().setAttribute( "style", "text-decoration:none;" );
		titleElement.setAttribute( "class", "panel-title" );
		
		bodyPanel.setStyleName( "panel-body" );

		
		initWidget( panel );
	}
	
	public void setTitle( String html ) {
		titleElement.setInnerHTML( html );
	}

	public void add( Widget child ) {
		bodyPanel.add( child );
	}

	public void add( IsWidget child ) {
		bodyPanel.add( child );
	}
	
	public void clear() {
		bodyPanel.clear();
	}
	
	public void show() {
		showCollapse( collapsePanel.getElement() );
	}
	
	public void hide() {
		hideCollapse( collapsePanel.getElement() );
	}
	
	private static native void showCollapse( Element element ) /*-{
		$wnd.jQuery( element ).collapse( 'show' );
	}-*/;
	
	private static native void hideCollapse( Element element ) /*-{
		$wnd.jQuery( element ).collapse( 'hide' );
	}-*/;

}
