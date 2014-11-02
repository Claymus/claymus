package com.claymus.pagecontent.blog.client;

import com.claymus.commons.client.SocialUtil;
import com.claymus.commons.client.ui.InfiniteScrollPanel;
import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.GetBlogListRequest;
import com.claymus.service.shared.GetBlogListResponse;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class BlogContent implements EntryPoint {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	
	
	private Long blogId = null;
	private String cursor = null;
	private int resultCount = 2;

	public void onModuleLoad() {
		
		RootPanel rootPanel = RootPanel.get( "PageContent-Blog" );
		blogId = Long.parseLong( rootPanel.getElement().getAttribute( "data-blogId" ) );
		cursor = rootPanel.getElement().getAttribute( "data-cursor" );
		
		InfiniteScrollPanel infiniteScrollPanel = new InfiniteScrollPanel() {
			
			@Override
			protected void loadItems() {
				claymusService.getBlogPostList(
						new GetBlogListRequest( blogId, cursor, resultCount ),
						new AsyncCallback<GetBlogListResponse>() {
							
							@Override
							public void onSuccess( GetBlogListResponse response ) {
								for( String html : response.getBlogPostHtmlList() ) {
									HTML blogPost = new HTML( html );
									SocialUtil.renderSocial( blogPost.getElement() );
									add( blogPost );
								}
								
								cursor = response.getCursor();
								if( response.getBlogPostHtmlList().size() < resultCount )
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
		
		rootPanel.add( infiniteScrollPanel );
	}

}