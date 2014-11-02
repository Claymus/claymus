package com.claymus.commons.client.ui.formfield;
import com.claymus.commons.client.Amount;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class CurrencyInputFormField extends FormField {

	private final Panel formGroup = new FlowPanel();
	private final Element label = Document.get().createLabelElement();
	private final Panel inputGroup = new FlowPanel();
	private final Element glyphicon = Document.get().createSpanElement();

	private final Element symbol = Document.get().createSpanElement();
	private final TextBox textBox = new TextBox();
	
	private static final RegExp regExp = RegExp.compile( "^-?\\d+\\.?\\d*$" );
	
	
	public CurrencyInputFormField() {

		symbol.setInnerText( "Rs." );
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
		formGroup.add( inputGroup );
		formGroup.getElement().appendChild( glyphicon );
		
		inputGroup.getElement().appendChild( symbol );
		inputGroup.add( textBox );
		
		
		// Setting required style classes
		formGroup.setStyleName( "form-group" );
		label.setAttribute( "class", "control-label sr-only" );
		inputGroup.setStyleName( "input-group" );
		symbol.setAttribute( "class", "input-group-addon" );
		textBox.setStyleName( "form-control" );
		
		
		initWidget( formGroup );
	}

	
	public void setPlaceholder( String placeholder ) {
		textBox.getElement().setAttribute( "placeholder", placeholder );
	}
	
	public Amount getAmount() {
		return textBox.getValue().isEmpty() ?
				null :
				new Amount( Double.parseDouble( textBox.getValue().trim() ) );
	}

	public void setAmount( Amount amount ) {
		textBox.setValue( amount == null ? "" : amount.getDecimalValue() + "" );
	}
	
	
	@Override
	public void setEnabled( boolean enabled ) {
		textBox.setEnabled( enabled );
	}

	@Override
	public boolean validate() {
		if( getAmount() == null && !isRequired() ) {
			markDefault();
			return true;
			
		} else if( getAmount() == null && isRequired() ) {
			markError( "Input Required !" );
			return false;

		} else { // if( getAmount() != null ) {
			if( regExp.test( textBox.getText() ) ) {
				markSuccess();
				return true;
			} else {
				markError( "Provide a valid number !" );
				return false;
			}
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
