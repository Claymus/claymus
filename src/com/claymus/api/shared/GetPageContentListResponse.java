package com.claymus.api.shared;

import java.util.List;

import com.claymus.service.shared.data.PageContentData;

@SuppressWarnings("serial")
public class GetPageContentListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<PageContentData> pageContentList;
	
	
	@SuppressWarnings("unused")
	private GetPageContentListResponse() {}
	
	public GetPageContentListResponse( List<PageContentData> pageContentList ) {
		this.pageContentList = pageContentList;
	}
	
}
