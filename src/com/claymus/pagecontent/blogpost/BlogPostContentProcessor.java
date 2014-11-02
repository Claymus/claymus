package com.claymus.pagecontent.blogpost;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blogpost.shared.BlogPostContentData;

public class BlogPostContentProcessor extends PageContentProcessor<BlogPostContent> {

	@Override
	protected CacheLevel getCacheLevel() {
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
		
		return super.processTemplate( dataModel, getTemplateName() );
	}

}
