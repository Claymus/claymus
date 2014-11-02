package com.claymus.commons.client.ui.formfield;

import java.util.Date;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;

public class TimeInputOptionalFormField extends FormField {

	private final Panel formGroup = new FlowPanel();
	private final Element label = Document.get().createLabelElement();
	private final Panel inputGroup = new FlowPanel();
	private final Element glyphicon = Document.get().createSpanElement();

	private final CheckBox checkBox = new CheckBox();
	private final TextBox textBox = new TextBox();

	
	public TimeInputOptionalFormField() {
		checkBox.addValueChangeHandler( new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange( ValueChangeEvent<Boolean> event ) {
				validate();
				textBox.setEnabled( event.getValue() );
			}
			
		});
		checkBox.setValue( false );
		textBox.getElement().setAttribute( "type", "time" );
		textBox.getElement().setAttribute( "data-container", "body" );
		textBox.getElement().setAttribute( "data-placement", "top" );
		textBox.addBlurHandler( new BlurHandler() {
			
			@Override
			public void onBlur( BlurEvent event ) {
				validate();
			}
			
		});
		textBox.setEnabled( false );
		

		// Composing the widget
		formGroup.getElement().appendChild( label );
		formGroup.add( inputGroup );
		formGroup.getElement().appendChild( glyphicon );
		
		inputGroup.add( checkBox );
		inputGroup.add( textBox );
		
		
		// Setting required style classes
		formGroup.setStyleName( "form-group" );
		label.setAttribute( "class", "control-label sr-only" );
		inputGroup.setStyleName( "input-group" );
		checkBox.setStyleName( "input-group-addon" );
		textBox.setStyleName( "form-control" );
		
		
		initWidget( formGroup );
	}


	public String getTime() {
		return checkBox.getValue() ? textBox.getText() : null;
	}

	public Date getTime( Date date ) {
		String time = getTime();
		if( date != null && time != null ) {
			int hours = Integer.parseInt( time.substring( 0, 2 ) );
			int minutes = Integer.parseInt( time.substring( 3 ) );
			date = new Date( date.getTime() + ( hours * 60 + minutes ) * 60 * 1000 );
		}
		return date;
	}

	public void setTime( Date date ) {
		textBox.setValue( DateTimeFormat.getFormat( "HH:mm" ).format( date ) );
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		checkBox.setEnabled( enabled );
		textBox.setEnabled( enabled ? checkBox.getValue() : enabled );
	}
	
	@Override
	public boolean validate() {
		if( checkBox.getValue() && textBox.getValue().isEmpty() ) {
			markError( "Input Required !" );
			return false;
		
		} else if( checkBox.getValue() && ! textBox.getValue().isEmpty() ) {
			markSuccess();
			return true;

		} else { // if( ! checkBox.getValue() ) {
			markDefault();
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
