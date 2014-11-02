package com.claymus.pagecontent.blogpost.shared;

import com.claymus.service.shared.data.PageContentData;

public class BlogPostContentData extends PageContentData {

	private String title;
	private boolean hasTitle;
	
	private String content;
	private boolean hasContent;

	private Long blogId;
	private boolean hasBlogId;
	
	
	public String getTitle() {
		return title;
	};
	
	public void setTitle( String title ) {
		this.title = title;
		this.hasTitle = true;
	};

	public boolean hasTitle() {
		return hasTitle;
	}
	
	public String getContent() {
		return content;
	};
	
	public void setContent( String html ) {
		this.content = html;
		this.hasContent = true;
	};
	
	public boolean hasContent() {
		return hasContent;
	}
	
	public Long getBlogId() {
		return blogId;
	};
	
	public void setBlogId( Long blogId ) {
		this.blogId = blogId;
		this.hasBlogId = true;
	};

	public boolean hasBlogId() {
		return hasBlogId;
	}
	
}
