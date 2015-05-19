package com.claymus.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

import com.claymus.commons.shared.CommentParentType;
import com.claymus.service.shared.data.UserData;

@SuppressWarnings("serial")
public class CommentData implements Serializable {
	
	private Long id;
	private String parentId;
	private CommentParentType parentType;
	private String refId;
	private Long userId;
	private UserData userData;
	
	private String content;
	private boolean hasContent;
	private Date commentDate;
	private Date commentLastUpdatedDate;
	
	private Integer upvote;
	private boolean hasUpvote;
	
	private Integer downvote;
	private boolean hasDownvote;
	
	private Date creationDate;
	
	public Long getId(){
		return id;
	}
	
	public void setId( Long id ){
		this.id = id;
	}
	
	public String getParentId(){
		return parentId;
	}

	public void setParentId( String parentId ){
		this.parentId = parentId;
	}
	
	public CommentParentType getParentType(){
		return parentType;
	}
	
	public void setParentType ( CommentParentType parentType ){
		this.parentType = parentType;
	}
	
	public String getRefId(){
		return refId;
	}
	
	public void setRefId( String refId ){
		this.refId = refId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId( Long userId ){
		this.userId = userId;
	}
	
	public UserData getUserData(){
		return userData;
	}
	
	public void setUserData( UserData userData ){
		this.userData = userData;
	}

	public String getContent(){
		return content;
	}
	
	public void setContent( String content ){
		this.content = content;
		this.hasContent = true;
	}
	
	public boolean hasContent(){
		return hasContent;
	}
	
	public Date getCommentDate(){
		return commentDate;
	}
	
	public void setCommentDate( Date commentDate ){
		this.commentDate = commentDate;
	}
	
	public Date getCommentLastUpdatedDate(){
		return commentLastUpdatedDate;
	}
	
	public void setCommentLastUpdatedDate( Date commentLastUpdatedDate ){
		this.commentLastUpdatedDate = commentLastUpdatedDate;
	}
	
	public Integer getUpvote(){
		return upvote;
	}
	
	public void setUpvote( Integer upvote ){
		this.upvote = upvote;
		this.hasUpvote = true;
	}
	
	public boolean hasUpvote(){
		return hasUpvote;
	}
	
	public Integer getDownvote(){
		return downvote;
	}
	
	public void setDownvote( Integer downvote ){
		this.downvote = downvote;
		this.hasDownvote = true;
	}
	
	public boolean hasDownvote(){
		return hasDownvote;
	}
	
	public Date getCreationDate(){
		return creationDate;
	}
	
	public void setCreationDate( Date creationDate ){
		this.creationDate = creationDate;
	}
	
}
