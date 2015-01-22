package com.claymus.pagecontent.pages.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.claymus.data.transfer.client.PageData;

@SuppressWarnings("serial")
public class GetPageListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<PageData> pageList;
	
	@SuppressWarnings("unused")
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetPageListResponse() {}
	
	public GetPageListResponse( List<PageData> pageList, String cursor ) {
		this.pageList = pageList;
		this.cursor = cursor;
	}
	
}
