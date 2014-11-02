package com.claymus.pagecontent.filebrowser.client;

import com.claymus.commons.client.ui.FileUploadWithProgressBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class FileBrowser implements EntryPoint {
	
	private final FileUploadWithProgressBar imageUpload = new FileUploadWithProgressBar();
	private String imageUploadUrl;

	@Override
	public void onModuleLoad() {
		// Upload new image to image library
		imageUpload.setTitle( "<button type='button' class='btn btn-info btn-lg' >Upload New Image</button>" );
		imageUpload.setAcceptedFileTypes( "image/jpg, image/jpeg, image/png, image/bmp" );
		imageUpload.addValueChangeHandler( new ValueChangeHandler<String>( ) {
			
			@Override
			public void onValueChange( ValueChangeEvent<String> event ) {
				//TODO : FINALIZE IMAGE UPLOAD URL
				CKEditorCallback( imageUploadUrl );
			}
			
		});
		imageUpload.getProgressBar().getElement().setAttribute(
				"style",
				imageUpload.getProgressBar().getElement().getAttribute( "style" )
				+ "margin-top:10px; margin-bottom:10px;" );
		
		
		RootPanel imageUploadPanel = RootPanel.get( "Pratilipi-FileBrowser-ImageUpload" );
		imageUploadPanel.add( imageUpload );
		imageUploadPanel.add( imageUpload.getProgressBar() );
		
	}
	
	private native void CKEditorCallback( String imageUrl )/*-{
		window.opener.CKEDITOR.tools.callFunction( 1, imageUrl, "Upload Failed. Please try again"); 
	}-*/;

}
