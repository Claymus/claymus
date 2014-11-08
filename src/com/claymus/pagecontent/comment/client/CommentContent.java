package com.claymus.pagecontent.comment.client;

import com.claymus.commons.client.ui.formfield.RichTextInputFormField;
import com.claymus.commons.client.ui.formfield.TextInputFormField;
import com.claymus.commons.shared.CommentStatus;
import com.claymus.pagecontent.comment.shared.CommentContentData;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.SavePageContentRequest;
import com.claymus.service.shared.SavePageContentResponse;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;


public class CommentContent implements EntryPoint, ClickHandler {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	private RootPanel titlePanel =
			RootPanel.get( "PageContent-BlogPost-Title" );
	private RootPanel contentPanel =
			RootPanel.get( "PageContent-BlogPost-Content" );
	private RootPanel editOptionsPanel =
			RootPanel.get( "PageContent-BlogPost-EditOptions" );
	
	private String pageConentIdStr = titlePanel.getElement().getAttribute( "data-pageContentId" );

	private TextInputFormField titleInput = new TextInputFormField();
	private RichTextInputFormField contentInput = new RichTextInputFormField();
	private Button editButton = new Button( "Edit Post" );
	private Button saveButton = new Button( "Save Post" );
	
	@Override
	public void onModuleLoad() {
		
		editButton.addClickHandler( this );
		saveButton.addClickHandler( this );

		saveButton.setEnabled( false );
		saveButton.setVisible( false );
		
		editOptionsPanel.add( editButton );
		editOptionsPanel.add( saveButton );

		editButton.setStyleName( "btn btn-primary" );
		saveButton.setStyleName( "btn btn-danger" );
	}
	
	@Override
	public void onClick( ClickEvent event ) {
		if( event.getSource() == editButton ) {
			editButton.setEnabled( false );
			editButton.setVisible( false );
			saveButton.setEnabled( true );
			saveButton.setVisible( true );
			
			titleInput.setText( titlePanel.getElement().getInnerText() );
			contentInput.setHtml( contentPanel.getElement().getInnerHTML() );
			
			titlePanel.getElement().setInnerText( "" );
			contentPanel.getElement().setInnerHTML( "" );

			titlePanel.add( titleInput );
			contentPanel.add( contentInput );

			
		} else if( event.getSource() == saveButton ) {
			if( ! titleInput.validate() )
				return;
			
			saveButton.setEnabled( false );

			CommentContentData commentContentData = new CommentContentData();
			
			
			commentContentData.setId( Long.parseLong( pageConentIdStr ) );
//			commentContentData.setEmail(titleInput.getText());
//			commentContentData.setCommentContent(contentInput.getHtml());
//			commentContentData.setStatus(CommentStatus.PENDING_APPROVAL);
//			commentContentData.setParentId(null);
			
			claymusService.savePageContent(
					new SavePageContentRequest( commentContentData ),
					new AsyncCallback<SavePageContentResponse>() {
						
				@Override
				public void onSuccess(SavePageContentResponse response) {
					
					if( pageConentIdStr.isEmpty() ) {
						Window.Location.replace( "/blog/" + response.getPageContentId() );
					
					} else {
						titlePanel.remove( titleInput );
						contentPanel.remove( contentInput );
						
						titlePanel.getElement().setInnerText( titleInput.getText() );
						contentPanel.getElement().setInnerHTML( contentInput.getHtml() );
						
						saveButton.setVisible( false );
						editButton.setEnabled( true );
						editButton.setVisible( true );
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					saveButton.setEnabled( true );
				}
				
			});

		}
	}
}
