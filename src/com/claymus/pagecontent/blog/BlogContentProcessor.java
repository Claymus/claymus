package com.claymus.pagecontent.blog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.PageContent;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.claymus.pagecontent.blogpost.BlogPostContentHelper;
import com.claymus.pagecontent.blogpost.shared.BlogPostContentData;

public class BlogContentProcessor extends PageContentProcessor<BlogContent> {

	@Override
	public String generateTitle(
			BlogContent blogContent, HttpServletRequest request ) {
		
		return blogContent.getTitle();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String generateHtml(
			BlogContent blogContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<BlogPostContent> dataListCursorTuple =
				dataAccessor.getBlogPostContentList(
						blogContent.getId(), blogContent.getCursor(), 5 );
		
		
		List<BlogPostContent> blogPostContentList = dataListCursorTuple.getDataList();
		List<String> blogPostHtmlList = new ArrayList<>( blogPostContentList.size() );
		PageContentProcessor blogPostContentProcessor =
				PageContentRegistry.getPageContentProcessor(
						BlogPostContentHelper.newBlogPostContent().getClass() );
		for( PageContent blogPostContent : blogPostContentList ) {
			((BlogPostContent) blogPostContent).setPreview( true );
			blogPostHtmlList.add( blogPostContentProcessor.generateHtml( blogPostContent, request ) );
		}
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "blogPostHtmlList", blogPostHtmlList );
		dataModel.put( "blogId", blogContent.getId() );
		dataModel.put( "cursor", dataListCursorTuple.getCursor() );
		dataModel.put( "showEditOptions", PageContentRegistry
				.getPageContentHelper( BlogPostContentData.class )
				.hasRequestAccessToAddContent( request ) );
		
		return super.processTemplate( dataModel, getTemplateName() );
	}

}
