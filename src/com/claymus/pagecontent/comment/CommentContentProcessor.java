package com.claymus.pagecontent.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.Comment;
import com.claymus.data.transfer.PageContent;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blogpost.shared.BlogPostContentData;
import com.claymus.pagecontent.comment.shared.CommentContentData;

public class CommentContentProcessor extends PageContentProcessor<CommentContent> {

	
	@Override
	public String generateHtml( 
			CommentContent commentContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		CommentContentHelper commentContentHelper =
				(CommentContentHelper) PageContentRegistry.getPageContentHelper(
						CommentContentData.class );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<Comment> dataListCursorTuple =
				dataAccessor.getCommentByRefId( commentContent.getRefId(), commentContent.getCursor(), 5 );
		
		List<Comment> commentContentList = dataListCursorTuple.getDataList();
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "commentList", commentContentList );
		dataModel.put( "cursor", dataListCursorTuple.getCursor() );
		dataModel.put( "showEditOptions", PageContentRegistry
				.getPageContentHelper( BlogPostContentData.class )
				.hasRequestAccessToAddContent( request ) );
		
		
		return super.processTemplate( dataModel, getTemplateName() );
	}

	@Override
	protected String getTemplateName() {
		return "com/claymus/pagecontent/comment/CommentContent.ftl";
	}
	
}
