package com.claymus.data.transfer;

import java.io.Serializable;

import com.claymus.commons.shared.CommentStatus;

public interface Comment extends Serializable {
	
	Long getId();
	
	String getParentId();
	
	void setParentId( String parentId );
	
	String getRefId();
	
	void setRefId( String refId );

	Long getUserId();

	void setUser( Long userId );

	String getContent();

	void setContent( String content );

	CommentStatus getStatus();

	void setStatus( CommentStatus status );

}
