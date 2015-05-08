package com.claymus.pagecontent.blogpost;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FacebookApi;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blogpost.shared.BlogPostContentData;

public class BlogPostContentProcessor extends PageContentProcessor<BlogPostContent> {
	
	private static final String DOMAIN = ClaymusHelper.getSystemProperty( "domain" );

	@Override
	public Resource[] getDependencies( BlogPostContent blogPostContent, HttpServletRequest request){
	
		String ogFbAppId = FacebookApi.getAppId( request );
		String ogLocale = "hi_IN";
		String ogTitle = blogPostContent.getTitle();
		String ogUrl = "http://" + DOMAIN + "/blog/" + blogPostContent.getId();
		String ogPublisher = "https://www.facebook.com/Pratilipidotcom";
		
		final String fbOgTags = "<meta property='fb:app_id' content='" + ogFbAppId + "' />"
				+ "<meta property='og:locale' content='" + ogLocale + "' />"
				+ "<meta property='og:type' content='website' />"
				+ "<meta property='og:title' content='" + ogTitle + "' />"
				+ "<meta property='og:url' content='" + ogUrl + "' />"
				+ "<meta property='og:publisher' content='" + ogPublisher + "' />";
		
		
		return new Resource[] {

			new Resource() {
				@Override
				public String getTag() {
					return fbOgTags;
				}
			},

		};
	}
	
	@Override
	protected CacheLevel getCacheLevel( BlogPostContent blogPostContent, HttpServletRequest request ) {
		return CacheLevel.USER_ROLE;
	}

	@Override
	public String generateHtml(
			BlogPostContent blogPostContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		BlogPostContentHelper blogPostContentHelper =
				(BlogPostContentHelper) PageContentRegistry.getPageContentHelper(
						BlogPostContentData.class );
		
		boolean showEditOptions =
				blogPostContentHelper.hasRequestAccessToAddContent( request );
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "blogPostContent", blogPostContent );
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		if( blogPostContent.getId() == null ) // TODO: Hack for "New Blog" Page
			dataModel.put( "pageUrl", "/" );
		else
			dataModel.put( "pageUrl", "/blog/" + blogPostContent.getId() );
		dataModel.put( "showEditOptions", showEditOptions );
		
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}

}
