package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.commons.shared.CommentParentType;
import com.claymus.commons.shared.CommentState;
import com.claymus.data.transfer.Comment;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
@PersistenceCapable( table = "COMMENT" )
public class CommentEntity implements Comment {

	@PrimaryKey
	@Persistent( column = "COMMENT_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;

	@Persistent( column = "PARENT_ID" )
	private String parentId;
	
	@Persistent( column = "PARENT_TYPE" )
	private CommentParentType parentType;
	
	@Persistent( column = "REFERENCE_ID" )
	private String refId;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "CONTENT" )
	private Text content;
	
	@Persistent( column = "STATUS" )
	private CommentState status;
	
	@Persistent( column = "COMMENT_DATE")
	private Date commentDate;
	
	@Persistent( column = "COMMENT_LAST_UPDATED_DATE" )
	private Date commentLastUpdatedDate;
	
	@Persistent( column = "UPVOTE" )
	private Integer upvote;
	
	@Persistent( column = "DOWNVOTE" )
	private Integer downvote;
	
	@Persistent( column = "CREATION_DATE")
	private Date creationDate;
	
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	@Override
	public void setParentId( String parentId ) {
		this.parentId = parentId;
	}
	
	@Override
	public CommentParentType getParentType(){
		return parentType;
	}
	
	@Override
	public void setParentType( CommentParentType parentType ){
		this.parentType = parentType;
	}

	@Override
	public String getRefId() {
		return refId;
	}

	@Override
	public void setRefId( String refId ) {
		this.refId = refId;		
	}
	
	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUser( Long userId ) {
		this.userId = userId;	
		
	}

	@Override
	public String getContent() {
		return content == null ? null : content.getValue();
	}

	@Override
	public void setContent( String content ) {
		this.content = content == null ? null : new Text( content );
	}

	@Override
	public CommentState getStatus() {
		return status;
	}

	@Override
	public void setStatus( CommentState status ) {
		this.status = status;
	}

	@Override
	public Date getCommentDate() {
		return commentDate;
	}

	@Override
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
	@Override
	public Date getCommentLastUpdatedDate(){
		return commentLastUpdatedDate;
	}
	
	@Override
	public void setCommentLastUpdatedDate( Date commentLastUpdatedDate ){
		this.commentLastUpdatedDate = commentLastUpdatedDate;
	}
	
	@Override
	public Integer getUpvote(){
		return upvote == null ? 0 : upvote;
	}
	
	@Override
	public void setUpvote( Integer upvote ){
		this.upvote = upvote;
	}
	
	@Override
	public Integer getDownvote(){
		return downvote == null ? 0 : downvote;
	}
	
	@Override
	public void setDownvote( Integer downvote ){
		this.downvote = downvote;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
