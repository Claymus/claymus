package com.claymus.pagecontent.comments.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.CommentFilter;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.shared.CommentData;
import com.claymus.pagecontent.comments.CommentsContentHelper;
import com.claymus.pagecontent.comments.api.shared.GetCommentListRequest;
import com.claymus.pagecontent.comments.api.shared.GetCommentListResponse;
import com.claymus.pagecontent.comments.api.shared.PutCommentRequest;
import com.claymus.pagecontent.comments.api.shared.PutCommentResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/comment" )
public class CommentsApi extends GenericApi {
	
	private final Logger logger = Logger.getLogger( CommentsApi.class.getName() );

	@Get
	public GetCommentListResponse getCommentList( GetCommentListRequest request ){
		
		CommentFilter commentFilter = new CommentFilter();
		
		commentFilter.setParentId( request.getParentId() );
		commentFilter.setParentType( request.getParentType() );

		String cursor = null;
		if( request.hasCursor() )
			cursor = request.getCursor();
		
		Integer resultCount = null;
		if( request.hasResultCount() )
			resultCount = request.getResultCount();
			
		DataListCursorTuple<CommentData> commentListCursorTuple = 
							CommentsContentHelper.getCommentList(
											commentFilter, 
											cursor, 
											resultCount, 
											this.getThreadLocalRequest() );
		
		
		
		return new GetCommentListResponse( commentListCursorTuple.getDataList(), 
											commentListCursorTuple.getCursor() );
	}
	
	@Put
	public PutCommentResponse putComment( PutCommentRequest request ) 
				throws InsufficientAccessException, InvalidArgumentException{
		
		logger.log( Level.INFO, "REQUEST PARENT TYPE OBJECT ; " + request.getParentType() + "/" + request.getParentType().getClass().toString() );
		CommentData commentData = new CommentData();
		commentData.setId( request.getId() );
		commentData.setParentId( request.getParentId() );
		commentData.setParentType( request.getParentType() );
		if( request.hasComment() )
			commentData.setContent( request.getComment() );
		logger.log( Level.INFO, "REQUEST HASUPVOTE : " + request.hasUpvote() );
		if( request.hasUpvote() ){
			commentData.setUpvote( request.getUpvote() );
		}
		else if( request.hasDownvote() ){
			commentData.setDownvote( request.getDownvote() );
		}
			
		commentData = CommentsContentHelper.saveComments( commentData, this.getThreadLocalRequest() );
		logger.log( Level.INFO, "RESPONSE OBJECT : " + commentData.toString() );
		
		return new PutCommentResponse( commentData );
	}
}
