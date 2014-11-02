package com.claymus.commons.client.ui.formfield;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class NumberInputFormField extends FormField {

	private static final String numberPattern = new String( "^\\d*$" );
	private static final RegExp numberExp = RegExp.compile( numberPattern );
	
	private final Panel formGroup = new FlowPanel();
	private final Element label = Document.get().createLabelElement();
	private final TextBox inputBox = new TextBox();
	private final Element glyphicon = Document.get().createSpanElement();
	
	
	public NumberInputFormField() {

		inputBox.getElement().setAttribute( "type", "text" );
		inputBox.getElement().setAttribute( "data-container", "body" );
		inputBox.getElement().setAttribute( "data-placement", "top" );
		inputBox.addBlurHandler( new BlurHandler() {
			
			@Override
			public void onBlur( BlurEvent event ) {
				validate();
			}
		
		});

		
		// Composing the widget
		formGroup.getElement().appendChild( label );
		formGroup.add( inputBox );
		formGroup.getElement().appendChild( glyphicon );
		
		
		// Setting required style classes
		formGroup.setStyleName( "form-group" );
		label.setAttribute( "class", "control-label sr-only" );
		inputBox.setStyleName( "form-control" );
		
		
		initWidget( formGroup );
	}

	
	public void setPlaceholder( String placeholder ) {
		inputBox.getElement().setAttribute( "placeholder", placeholder );
	}
	
	public Long getValue() {
		return inputBox.getText().trim().isEmpty() ?
				null : Long.valueOf( inputBox.getText().trim() );
	}
	
	public void setValue( Long number ) {
		inputBox.setText( number == null ? "" : Long.toString( number ) );
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		inputBox.setEnabled( enabled );
	}
	
	
	@Override
	public boolean validate() {
		MatchResult matcher = numberExp.exec( inputBox.getText().trim() );
		Boolean matchFound = ( matcher != null );

		if( inputBox.getText().trim().isEmpty() && !isRequired() ) {
			markDefault();
			return true;

		} else if( inputBox.getText().trim().isEmpty() && isRequired() ){
			markError( "Input Required !" );
			return false;
			
		} else if( !matchFound ) { // ! inputBox.getText().trim().isEmpty()
			markError( "Not a valid number !" );
			return false;
		
		} else { // matchFound && inputBox.getText().trim().isEmpty()
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
		hidePopover( inputBox.getElement() );
	}
	
	private void markSuccess() {
		formGroup.setStyleName( "form-group has-success has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-ok" );
		hidePopover( inputBox.getElement() );
	}
	
	private void markError( String errorMsg ) {
		formGroup.setStyleName( "form-group has-error has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-remove" );
		showPopover( inputBox.getElement(), errorMsg );
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
