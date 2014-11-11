package com.claymus.data.access.gae;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

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
	
	@Persistent( column = "REFERENCE_ID" )
	private String refId;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "CONTENT" )
	private Text content;
	
	@Persistent( column = "STATUS" )
	private CommentState status;
	
	
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

}
