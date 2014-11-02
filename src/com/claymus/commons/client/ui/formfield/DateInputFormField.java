package com.claymus.commons.client.ui.formfield;

import java.util.Date;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class DateInputFormField extends FormField {

	private final static DateTimeFormat dateTimeFormat =
			DateTimeFormat.getFormat( "yyyy-MM-dd" );
	
	private final Panel formGroup = new FlowPanel();
	private final Element label = Document.get().createLabelElement();
	private final TextBox textBox = new TextBox();
	private final Element glyphicon = Document.get().createSpanElement();
	
	
	public DateInputFormField() {
		textBox.getElement().setAttribute( "type", "date" );
		textBox.getElement().setAttribute( "data-container", "body" );
		textBox.getElement().setAttribute( "data-placement", "top" );
		textBox.addBlurHandler( new BlurHandler() {
			
			@Override
			public void onBlur( BlurEvent event ) {
				validate();
			}
		
		});

		
		// Composing the widget
		formGroup.getElement().appendChild( label );
		formGroup.add( textBox );
		formGroup.getElement().appendChild( glyphicon );
		
		
		// Setting required style classes
		formGroup.setStyleName( "form-group" );
		label.setAttribute( "class", "control-label sr-only" );
		textBox.setStyleName( "form-control" );
		
		
		initWidget( formGroup );
	}

	
	public Date getDate() {
		return textBox.getText().isEmpty() ? null : dateTimeFormat.parse( textBox.getText() );
	}
	
	public void setDate( Date date ) {
		textBox.setText( date == null ? "" : dateTimeFormat.format( date ) );
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		textBox.setEnabled( enabled );
	}
	
	@Override
	public boolean validate() {
		if( getDate() == null && !isRequired() ) {
			markDefault();
			return true;

		} else if( getDate() == null && isRequired() ) {
			markError( "Input Required !" );
			return false;

		} else { // if( getDate() != null ) {
			markSuccess();
			return true;
		}
	}
	
	@Override
	public void resetValidation() {
		markDefault();
	}

	private void markDefault() {
		formGroup.setStyleName( "form-group" );
		glyphicon.setAttribute( "class", "" );
		hidePopover( textBox.getElement() );
	}
	
	private void markSuccess() {
		formGroup.setStyleName( "form-group has-success has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-ok" );
		hidePopover( textBox.getElement() );
	}
	
	private void markError( String errorMsg ) {
		formGroup.setStyleName( "form-group has-error has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-remove" );
		showPopover( textBox.getElement(), errorMsg );
	}
	
	private static native void showPopover( Element element, String errorMsg ) /*-{
		$wnd.jQuery( element ).popover( 'destroy' );
		$wnd.jQuery( element ).popover( { content : errorMsg } );
		$wnd.jQuery( element ).popover( 'show' );
	}-*/;

	private static native void hidePopover( Element element ) /*-{
		$wnd.jQuery( element ).popover( 'destroy' );
	}-*/;

}
