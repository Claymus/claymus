package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Modal extends Composite {

	private final Panel panel = new SimplePanel();
	
	private final Panel panelDialog = new SimplePanel();
	private final Panel panelContent = new FlowPanel();
	
	private final Panel header = new FlowPanel();
	private final Panel body = new FlowPanel();
	private final Panel footer = new FlowPanel();

	private final Button xButton = new Button();
	private final HeadingElement titleElement = Document.get().createHElement( 3 );
	private final Button closeButton = new Button();
	
	
	public Modal() {

		panel.getElement().setTabIndex( -1 );
		xButton.getElement().setAttribute( "data-dismiss", "modal" );
		xButton.setHTML( "<span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span>" );
		closeButton.getElement().setAttribute( "data-dismiss", "modal" );
		closeButton.setText( "Close" );
		

		// Composing the widget
		panel.add( panelDialog );
		panelDialog.add( panelContent );

		panelContent.add( header );
		panelContent.add( body );
		panelContent.add( footer );
		
		header.add( xButton );
		header.getElement().appendChild( titleElement );
		
		footer.add( closeButton );
		

		// Setting required style classes
		panel.setStyleName( "modal fade" );
		panelDialog.setStyleName( "modal-dialog" );
		panelContent.setStyleName( "modal-content" );

		header.setStyleName( "modal-header" );
		body.setStyleName( "modal-body" );
		footer.setStyleName( "modal-footer" );
		
		xButton.setStyleName( "close" );
		titleElement.setAttribute( "class", "modal-title" );

		closeButton.setStyleName( "btn btn-default" );

		
		initWidget( panel );
	}
	
	public void setTitle( String title ) {
		titleElement.setInnerText( title );
	}

	public void add( Button button ) {
		footer.add( button );
	}
	
	public void add( Widget child ) {
		body.add( child );
	}

	public void add( IsWidget child ) {
		body.add( child );
	}
	
	public void show() {
		showModal( panel.getElement() );
	}
	
	public void hide() {
		hideModal( panel.getElement() );
	}
	
	private static native void showModal( Element element ) /*-{
		$wnd.jQuery( element ).modal( 'show' );
	}-*/;
	
	private static native void hideModal( Element element ) /*-{
		$wnd.jQuery( element ).modal( 'hide' );
	}-*/;

}
