package com.claymus.pagecontent.pages.client;

import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.claymus.commons.client.ui.PageDataInputView;
import com.claymus.commons.client.ui.PageDataInputViewModalImpl;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.GetPageListRequest;
import com.claymus.service.shared.GetPageListResponse;
import com.claymus.service.shared.SavePageRequest;
import com.claymus.service.shared.SavePageResponse;
import com.claymus.service.shared.data.PageData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.claymus.commons.shared.exception.IllegalArgumentException;

public class PagesContent implements EntryPoint, ClickHandler {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	
	private final RootPanel pageListPanel = RootPanel.get( "PageContent-Pages" );
	private final RootPanel addPageOptionPanel = RootPanel.get( "PageContent-Pages-AddOption" );
	
	private final Anchor newPageAnchor = new Anchor( "New Page" );
	private final Button savePageDataButton = new Button( "Save" );
	private final PageDataInputView pageDataInputView = new PageDataInputViewModalImpl();
	
	private String cursor = null;
	private int resultCount = 20;

	
	public void onModuleLoad() {

		newPageAnchor.addClickHandler( this );
		savePageDataButton.addClickHandler( this );
		
		InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				claymusService.getPageList(
						new GetPageListRequest( cursor, resultCount ),
						new AsyncCallback<GetPageListResponse>() {
							
							@Override
							public void onSuccess( GetPageListResponse response ) {
								for( PageData pageData : response.getPageDataList() )
									add( new Anchor( pageData.getTitle(), pageData.getUri() ) );

								cursor = response.getCursor();
								if( response.getPageDataList().size() < resultCount )
									loadFinished();
								loadSuccessful();
							}
							
							@Override
							public void onFailure( Throwable caught ) {
								loadFailed();
							}
							
						});
			}
			
		};
		
		
		pageDataInputView.add( savePageDataButton );
		
		addPageOptionPanel.add( newPageAnchor );
		RootPanel.get().add( pageDataInputView );
		pageListPanel.add( infiniteScrollPanel );
		
		savePageDataButton.setStyleName( "btn btn-success" );
	}


	@Override
	public void onClick( ClickEvent event ) {
		
		if( event.getSource() == newPageAnchor ) {
			pageDataInputView.setVisible( true );

			
		} else if( event.getSource() == savePageDataButton ) {
			if( !pageDataInputView.validateInputs() )
				return;
			
			pageDataInputView.setEnabled( false );
			claymusService.savePage(
					new SavePageRequest( pageDataInputView.getPageData() ),
					new AsyncCallback<SavePageResponse>() {
						
						@Override
						public void onSuccess( SavePageResponse result ) {
							Window.Location.reload();
						}
						
						@Override
						public void onFailure( Throwable caught ) {
							if( caught.getClass() == IllegalArgumentException.class )
								Window.alert( caught.getMessage() );
							pageDataInputView.setEnabled( true );
						}
						
					});
		}
		
	}

}