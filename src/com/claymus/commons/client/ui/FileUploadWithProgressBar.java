package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class FileUploadWithProgressBar extends Composite implements HasValueChangeHandlers<String> {

	private final Panel panel = new FlowPanel();
	
	private final Panel progress = new SimplePanel();
	private final Panel progressBar = new SimplePanel();
	private final FileUpload fileUpload = new FileUpload();
	private final Anchor uploadAnchor = new Anchor( "Select File" );
	
	private String uploadUrl;

	
	public FileUploadWithProgressBar() {

		progress.setStyleName( "progress" );
		progress.setVisible( false );

		progressBar.setStyleName( "progress-bar progress-bar-success progress-bar-striped active" );
		progressBar.getElement().setAttribute( "role", "progressbar" );
		progressBar.getElement().setAttribute( "aria-valuenow", "0" );
		progressBar.getElement().setAttribute( "aria-valuemin", "0" );
		progressBar.getElement().setAttribute( "aria-valuemax", "100" );
		progressBar.getElement().setAttribute( "style", "width:0%" );
		
		fileUpload.addChangeHandler( new ChangeHandler() {
			
			@Override
			public void onChange( ChangeEvent event ) {
				progress.setVisible( true );
				uploadAnchor.setVisible( false );
			}
			
		});
		fileUpload.setVisible( false );
		
		uploadAnchor.addClickHandler( new ClickHandler() {
			
			@Override
			public void onClick( ClickEvent event ) {
				fileUpload.getElement().<InputElement>cast().click();
			}
			
		});
		
		
		progress.add( progressBar );
		panel.add( progress );
		panel.add( fileUpload );
		panel.add( uploadAnchor );
		
		initWidget( panel );
		
		
		initializeFileUploader(
				this,
				fileUpload.getElement(),
				progressBar.getElement() );
		
	}
	
	
	public HandlerRegistration addValueChangeHandler( ValueChangeHandler<String> handler ) {
		return addHandler( handler, ValueChangeEvent.getType() );
	}

	public void setTitle( String html ) {
		uploadAnchor.setHTML( html );
	}
	
	public void setAcceptedFileTypes( String fileTypes ) {
		fileUpload.getElement().setAttribute( "accept", fileTypes );
	}
	
	public void setUploadUrl( String uploadUrl ) {
		this.uploadUrl = uploadUrl;
		setFileUploadOption( fileUpload.getElement(), "url", uploadUrl );
	}
	
	public Widget getProgressBar() {
		return progress;
	}
	
	private void fileUploadDone() {
		progress.setVisible( false );
		uploadAnchor.setVisible( true );
		ValueChangeEvent.fire( this, uploadUrl );
	}

	private static void fileUploadDone( FileUploadWithProgressBar fileUploadWithProgressBar ) {
		fileUploadWithProgressBar.fileUploadDone();
	}

	private native void initializeFileUploader(
			FileUploadWithProgressBar fileUploadWithProgressBar, Element fileUpload, Element progressBar ) /*-{

		if( typeof $wnd.fileUploadDone == "undefined" )
			$wnd.fileUploadDone = $entry(@com.claymus.commons.client.ui.FileUploadWithProgressBar::fileUploadDone(Lcom/claymus/commons/client/ui/FileUploadWithProgressBar;));

		$wnd.jQuery( fileUpload ).fileupload({
			dataType: 'html',
			replaceFileInput: false,
			progressall: function( e, data ) {
				var progress = parseInt( data.loaded / data.total * 100, 10 );
				$wnd.jQuery( progressBar ).css( 'width', progress + '%' );
				$wnd.jQuery( progressBar ).attr( "aria-valuenow", progress );
		    },
		    done: function( e, data ) {
				$wnd.fileUploadDone( fileUploadWithProgressBar );
				$wnd.jQuery( progressBar ).css( 'width', '0%' );
				$wnd.jQuery( progressBar ).attr( "aria-valuenow", 0 );
		    }
		});
		
	}-*/;

	private native void setFileUploadOption( Element fileUpload, String option, String value ) /*-{
		$wnd.jQuery( fileUpload ).fileupload( 'option', option, value );
	}-*/;

}
