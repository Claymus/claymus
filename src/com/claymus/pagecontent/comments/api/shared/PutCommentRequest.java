package com.claymus.pagecontent.comments.api.shared;

import com.claymus.api.shared.GenericRequest;
import com.claymus.commons.shared.CommentParentType;

@SuppressWarnings( "serial" )
public class PutCommentRequest extends GenericRequest {
	
	private Long id;
	
	private String parentId;
	
	private CommentParentType parentType;
	
	private String comment;
	private boolean hasComment;
	
	
	private Integer upvote;
	private boolean hasUpvote;
	
	private Integer downvote;
	private boolean hasDownvote;
	
	public Long getId(){
		return id;
	}
	
	public String getParentId(){
		return parentId;
	}
	
	public CommentParentType getParentType(){
		return parentType;
	}
	
	public String getComment(){
		return comment;
	}
	
	public Boolean hasComment(){
		return hasComment;
	}
	
	public Integer getUpvote(){
		return upvote;
	}
	
	public Boolean hasUpvote(){
		return hasUpvote;
	}
	
	public Integer getDownvote(){
		return downvote;
	}
	
	public Boolean hasDownvote(){
		return hasDownvote;
	}

}
