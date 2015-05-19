package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.claymus.commons.shared.CommentParentType;
import com.claymus.commons.shared.CommentState;

public interface Comment extends Serializable {
	
	Long getId();
	
	String getParentId();
	
	void setParentId( String parentId );
	
	CommentParentType getParentType();
	
	void setParentType( CommentParentType parentType );
	
	String getRefId();
	
	void setRefId( String refId );

	Long getUserId();

	void setUser( Long userId );

	String getContent();

	void setContent( String content );
	
	CommentState getStatus();

	void setStatus( CommentState status );
	
	Date getCommentDate();
	
	void setCommentDate( Date date );
	
	Date getCommentLastUpdatedDate();
	
	void setCommentLastUpdatedDate( Date commentLastUpdatedDate );
	
	Integer getUpvote();
	
	void setUpvote( Integer upvote );
	
	Integer getDownvote();
	
	void setDownvote( Integer downvote );
	
	Date getCreationDate();
	
	void setCreationDate( Date date );

}
