package com.claymus.pagecontent.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.CommentFilter;
import com.claymus.commons.shared.CommentParentType;
import com.claymus.commons.shared.NotificationType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.Comment;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.shared.CommentData;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.comments.gae.CommentsContentEntity;
import com.claymus.pagecontent.comments.shared.CommentContentData;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.taskqueue.TaskQueueFactory;

public class CommentsContentHelper extends PageContentHelper<
		CommentsContent,
		CommentContentData,
		CommentsContentProcessor> {
	
	private final static Logger logger = Logger.getLogger( CommentsContentHelper.class.getName() );
	
	private static final Access ACCESS_TO_ADD_COMMENT_DATA =
			new Access( "comment_data_add", false, "Add Comment Data" );
	
	private static final Access ACCESS_TO_UPDATE_COMMENT_DATA =
			new Access( "comment_data_update", false, "Update Comment Data" );
	
	private static final Access ACCESS_TO_LIKE_COMMENT_DATA =
			new Access( "comment_data_like", false, "Like Comment Data" );
	
	@Override
	public String getModuleName() {
		return "Comment List";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_ADD_COMMENT_DATA,
				ACCESS_TO_UPDATE_COMMENT_DATA,
				ACCESS_TO_LIKE_COMMENT_DATA,
		};
	}
	
	public static Boolean hasRequestAccessToAddCommentData( 
				HttpServletRequest request, CommentData commentData  ) 
		throws InvalidArgumentException{
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		if( accessToken.getUserId() == 0 )
			throw new InvalidArgumentException( "User is not logged in" );
		
		if( commentData.getParentType() == CommentParentType.REVIEW ){
			UserPratilipi userPratilipi = dataAccessor.getUserPratilipiById( commentData.getParentId() );
			
			if( userPratilipi == null )
				throw new InvalidArgumentException( "Reveiew does not exists!" );
			
			if( accessToken.getUserId() == userPratilipi.getUserId() )
				return false;
			else
				return true;
		} else if ( commentData.getParentType() == CommentParentType.BLOGPOST ){
			//TODO : ACCESS REQUEST TO ADD COMMENT ON BLOGPOST
			return false;
		} else if ( commentData.getParentType() == CommentParentType.COMMENT ){
			Comment parentComment = com.claymus.data.access.DataAccessorFactory
									.getDataAccessor( request )
									.getCommentById( Long.valueOf( commentData.getParentId() ));
			
			if( parentComment == null )
				throw new InvalidArgumentException( "Comment doesnot exists!" );
			
			if( accessToken.getUserId().equals( parentComment.getUserId() ))
				return false;
			else
				return true;
			
		} else {
			return false;
		}
	}

	public static Boolean hasRequestAccessToUpdateCommentData( HttpServletRequest request, Comment comment  )
				throws InvalidArgumentException {
		
		if( comment == null )
			throw new InvalidArgumentException( "Comment is null" );
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		logger.log( Level.INFO, "USERID : " + comment.getUserId() + "/" + accessToken.getUserId() );
		if( comment.getUserId().equals( accessToken.getUserId() )){
			logger.log( Level.INFO, "COMMENT UPDATE PRATILIPI : " + "TRUE" );
			return true;
		}
		else {
			logger.log( Level.INFO, "COMMENT UPDATE PRATILIPI : " + "FALSE" );
			return false;
		}
	}
	
	public static Boolean hasRequestAccessToLikeCommentData( HttpServletRequest request, Comment comment  ) 
				throws InvalidArgumentException {
		
		if( comment == null )
			throw new InvalidArgumentException( "Comment is null" );
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		
		if( accessToken.getUserId() == 0 )
			throw new InvalidArgumentException( "User is not logged in" );
		
		if( comment.getUserId() == accessToken.getUserId() )
			return false;
		else
			return true;
	}
	
	
	public static CommentsContent newCommentContent() {
		return new CommentsContentEntity();
	}
	
	public static CommentData createCommentData( Long commentId, HttpServletRequest request  ){
		Comment comment =  com.claymus.data.access.DataAccessorFactory
									.getDataAccessor( request )
									.getCommentById( commentId );
		
		return createCommentData( comment, null, request );
	}
	
	public static CommentData createCommentData( Comment comment, User user, HttpServletRequest request ){
		CommentData commentData = new CommentData();
		commentData.setId( comment.getId() );
		commentData.setParentId( comment.getParentId() );
		commentData.setParentType( comment.getParentType() );
		commentData.setRefId( comment.getRefId() );
		commentData.setUserId( comment.getUserId() );
		if( user != null ){
			PratilipiHelper pratilipiHelper = PratilipiHelper.get( request );
			commentData.setUserData( pratilipiHelper.createUserData( user ) );
		}
		commentData.setContent( comment.getContent() );
		commentData.setCommentDate( comment.getCommentDate() );
		commentData.setCommentLastUpdatedDate( comment.getCommentLastUpdatedDate() );
		commentData.setUpvote( comment.getUpvote() );
		commentData.setDownvote( comment.getDownvote() );
		commentData.setCreationDate( comment.getCreationDate() );
		return commentData;
	}
	
	
	public static List<CommentData> createCommentDataList( List<Comment> commentList, Boolean includeUserData, HttpServletRequest request ){
		List<CommentData> commentDataList = new ArrayList<>( commentList.size() );
		for( Comment comment : commentList ){
			if( includeUserData ){
				User user = DataAccessorFactory.getDataAccessor( request ).getUser( comment.getUserId() );
				commentDataList.add( createCommentData( comment, user, request ) );
			} else
				commentDataList.add( createCommentData( comment, null, request ) );
		}
		
		return commentDataList;
	}
	
	public static List<Comment> getCommentList( 
			String parentId, 
			CommentParentType parentType, 
			Long userId, 
			HttpServletRequest request ){
	
		com.claymus.data.access.DataAccessor dataAccessor = com.claymus.data.access.DataAccessorFactory
												.getDataAccessor( request );
		List<Comment> commentList = dataAccessor.getCommentList( 
											parentId, 
											parentType, 
											userId );
		
		return commentList;
		
	}
	
	public static DataListCursorTuple<CommentData> getCommentList( 
			CommentFilter commentFilter, 
			String cursor, 
			Integer resultCount, 
			HttpServletRequest request ){
	
		com.claymus.data.access.DataAccessor dataAccessor = com.claymus.data.access.DataAccessorFactory
												.getDataAccessor( request );
		DataListCursorTuple<Comment> commentListCursorTuple = 
							dataAccessor.getCommentList( 
										commentFilter, 
										cursor, 
										resultCount == null ? 100 : resultCount );
		
		List<CommentData> commentDataList = createCommentDataList( commentListCursorTuple.getDataList(), true, request );
		
		return new DataListCursorTuple<CommentData>( commentDataList, commentListCursorTuple.getCursor() );
		
	}
	
	public static CommentData saveComments( CommentData commentData, HttpServletRequest request )
				throws InsufficientAccessException, InvalidArgumentException
	{
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Comment comment = null;
		String notificationType = null;
		
		if( commentData.getId() != null && commentData.hasContent() ){
			comment = dataAccessor.getCommentById( commentData.getId() );
			
			if( comment == null )
				throw new InvalidArgumentException( "Invalid Comment Id" );
		}
		else {
			if( !commentData.hasContent() ){	//MULTIPLE COMMENT ON ONE REVIEW
				List<Comment> commentList = getCommentList( commentData.getParentId(), commentData.getParentType(), accessToken.getUserId(), request );
				for( Comment c : commentList ){
					if( c.getUpvote() == 1 || c.getDownvote() == 1 )
						comment = c;
					
					if( comment == null && commentList.size() >= 1 )
						comment = commentList.get( commentList.size() -1 );	//First comment entity for passed set of filters
				}
			}
		}
		
		if( comment == null ){
			if( !hasRequestAccessToAddCommentData( request, commentData ))
				throw new InsufficientAccessException( "Insufficient access to add comment" );
			
			comment = dataAccessor.newComment();
			comment.setParentId( commentData.getParentId() );
			comment.setParentType( commentData.getParentType() );
			comment.setUser( accessToken.getUserId() );
			comment.setCreationDate( new Date() );
			notificationType = NotificationType.COMMENT_ADD.toString();
		} else{
			if( !hasRequestAccessToUpdateCommentData( request, comment ))
				throw new InsufficientAccessException( "Insufficient access to update comment" );
			
		}
		
		
		if( commentData.hasContent() ){
			comment.setContent( commentData.getContent() );
			if( comment.getCommentDate() == null )
				comment.setCommentDate( new Date() );
			comment.setCommentLastUpdatedDate( new Date() );
			
			if( notificationType == null )
				notificationType = NotificationType.COMMENT_UPDATE.toString();
		}
		
		if( commentData.hasUpvote() && commentData.hasDownvote() )
			throw new InvalidArgumentException( "User cannot upvote and downvote at same time" );

		if( commentData.hasUpvote() ){
			if( commentData.getUpvote() != 1 && commentData.getUpvote() != 0 )
				throw new InvalidArgumentException( "Invalid Upvote" );
			
			
			comment.setUpvote( commentData.getUpvote() );
			comment.setDownvote( 0 );
		}
		
		if( commentData.hasDownvote() ){
			if( commentData.getDownvote() != 1 && commentData.getDownvote() != 0 )
				throw new InvalidArgumentException( "Invalid Downvote" );
			
			comment.setDownvote( commentData.getDownvote() );
			comment.setUpvote( 0 );
		}
		
		comment = dataAccessor.createOrUpdateComment( comment );
		commentData = createCommentData( comment, null, request);
		
		logger.log( Level.INFO, "Comment Entry created-" + comment.getId() );
		
		if( notificationType != null ){
			UserPratilipi userPratilipi = dataAccessor.getUserPratilipiById( comment.getParentId() );
			Pratilipi pratilipi = dataAccessor.getPratilipi( userPratilipi.getPratilipiId() );
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			
			String recipientIdString;
			if( accessToken.getUserId().equals( author.getUserId() ) || author.getUserId() == null )
				//WHEN AUTHOR MAKES A COMMENT.
				recipientIdString = userPratilipi.getUserId().toString();
			else{
					recipientIdString = author.getUserId() + "~" + userPratilipi.getUserId();
			}
			
			logger.log( Level.INFO, "Recipient Id string : " + recipientIdString );
			
			createEmailTasks( 
					recipientIdString, 
					accessToken.getUserId().toString(), 
					pratilipi.getId().toString(), 
					notificationType );
			
		}
		
		return commentData;
	}
	
	private static void createEmailTasks( String recipientIdString, String userId, String pratilipiId, String notificationType ){
		String[] recipientIdArray;
		if( recipientIdString.indexOf( "~" ) != -1 )
			recipientIdArray = recipientIdString.split( "~" );
		else
			recipientIdArray = new String[]{ recipientIdString };
		
		for( int i = 0; i < recipientIdArray.length; i++){
			Task task = TaskQueueFactory.newTask();
			task.addParam( "userId", userId );
			task.addParam( "recipientId", recipientIdArray[i] );
			task.addParam( "pratilipiId", pratilipiId );
			task.addParam( "notificationType", notificationType );
			
			TaskQueue taskQueue = TaskQueueFactory.getNotificationTaskQueue();
			taskQueue.add( task );
		}
	}
	
}
