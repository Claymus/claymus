package com.claymus.pagecontent.comment;

import com.claymus.data.transfer.PageContent;

public interface CommentContent extends PageContent {
	
	String getRefId();
	
	void setRefId( String refId );
	
	String getCursor();
	
	void setCursor( String cursor );

	Integer getPostCount();
	
	void setPostCount( Integer postCount );
	
}
