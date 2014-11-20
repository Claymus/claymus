package com.claymus.pagecontent.comments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.Comment;
import com.claymus.pagecontent.PageContentProcessor;

public class CommentsContentProcessor extends PageContentProcessor<CommentsContent> {

	private final int commentCount = 20;
	
	@Override
	public String generateHtml( 
			CommentsContent commentContent, HttpServletRequest request )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<Comment> dataListCursorTuple =
				dataAccessor.getCommentList( commentContent.getRefId(), null, commentCount );
		
		List<Comment> commentList = dataListCursorTuple.getDataList();
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "commentList", commentList );
		dataModel.put( "refId", commentContent.getRefId() );
		if( commentList.size() == commentCount )
			dataModel.put( "cursor", dataListCursorTuple.getCursor() );

		
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}

}
