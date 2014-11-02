package com.claymus.service.shared;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetBlogListResponse implements IsSerializable {

	private List<String> blogPostHtmlList;
	
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetBlogListResponse() {}
	
	public GetBlogListResponse(
			List<String> blogPostHtmlList,
			String cursor ) {
		
		this.blogPostHtmlList = blogPostHtmlList;
		this.cursor = cursor;
	}
	
	
	public List<String> getBlogPostHtmlList() {
		return blogPostHtmlList;
	}

	public String getCursor() {
		return cursor;
	}
	
}
