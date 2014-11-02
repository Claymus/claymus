package com.claymus.pagecontent.blog;

import com.claymus.data.transfer.PageContent;

public interface BlogContent extends PageContent {
	
	String getTitle();
	
	void setTitle( String title );

	String getCursor();
	
	void setCursor( String cursor );

	Integer getPostCount();
	
	void setPostCount( Integer postCount );
	
}
