package com.claymus.pagecontent.blogpost;

import com.claymus.data.transfer.PageContent;

public interface BlogPostContent extends PageContent {
	
	String getTitle();
	
	void setTitle( String title );
	
	String getContent();
	
	void setContent( String html );
	
	Boolean preview();
	
	Long getBlogId();
	
	void setBlogId( Long blogId );
	
	void setPreview( Boolean preview );
	
}
