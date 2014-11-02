package com.claymus.commons.client.ui.formfield;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;

public class ListBoxFormField extends FormField {

	private final Panel formGroup = new FlowPanel();
	private final Element label = Document.get().createLabelElement();
	private final ListBox listBox = new ListBox();
	private final Element glyphicon = Document.get().createSpanElement();
	
	
	public ListBoxFormField() {
		listBox.getElement().setAttribute( "data-container", "body" );
		listBox.getElement().setAttribute( "data-placement", "top" );
		listBox.addBlurHandler( new BlurHandler() {
			
			@Override
			public void onBlur( BlurEvent event ) {
				validate();
			}
			
		});
		
		
		// Composing the widget
		formGroup.getElement().appendChild( label );
		formGroup.add( listBox );
		formGroup.getElement().appendChild( glyphicon );
		
		
		// Setting required style classes
		formGroup.setStyleName( "form-group" );
		label.setAttribute( "class", "control-label sr-only" );
		listBox.setStyleName( "form-control" );
		
		
		initWidget( formGroup );
	}
	
	
	public void setPlaceholder( String placeholder ) {
		addItem( placeholder, null );
	}
	
	public void addItem( String item, String value ){
		listBox.addItem( item, value == null ? "" : value );
	}
	
	public String getValue() {
		String value = listBox.getValue( listBox.getSelectedIndex() ).trim();
		return value.isEmpty() ? null : value;
	}
	
	public void setValue( String value ) {
		if( value == null )
			value = "";
		int itemCount = listBox.getItemCount();
		for( int i = 0; i < itemCount; i++ ) {
			if( listBox.getValue( i ).equals( value ) ) {
				listBox.setSelectedIndex( i );
				break;
			}
		}
	}
	
	public String getValueText() {
		String value = listBox.getItemText( listBox.getSelectedIndex() ).trim();
		return value.isEmpty() ? null : value;
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		listBox.setEnabled( enabled );
	}
	
	@Override
	public boolean validate() {
		if( getValue() == null && !isRequired() ) {
			markDefault();
			return true;
		
		} else if( getValue() == null && isRequired() ) {
			markError( "Input Required !" );
			return false;

		} else { // if( getSelectedValue() != "" ) {
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
		hidePopover( listBox.getElement() );
	}
	
	private void markSuccess() {
		formGroup.setStyleName( "form-group has-success has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-ok" );
		hidePopover( listBox.getElement() );
	}
	
	private void markError( String errorMsg ) {
		formGroup.setStyleName( "form-group has-error has-feedback" );
		glyphicon.setAttribute( "class", "form-control-feedback glyphicon glyphicon-remove" );
		showPopover( listBox.getElement(), errorMsg );
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
