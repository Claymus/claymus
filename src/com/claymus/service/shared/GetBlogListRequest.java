package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetBlogListRequest implements IsSerializable {

	private Long blogId;
	
	private String cursor;
	
	private int resultCount;
	
	
	@SuppressWarnings("unused")
	private GetBlogListRequest() {}

	public GetBlogListRequest( Long blogId, String cursor, int resultCount ) {
		
		this.blogId = blogId;
		this.cursor = cursor;
		this.resultCount = resultCount;
	}

	
	public Long getBlogId() {
		return blogId;
	}
	
	public String getCursor() {
		return cursor;
	}
	
	public int getResultCount() {
		return resultCount;
	}

}
