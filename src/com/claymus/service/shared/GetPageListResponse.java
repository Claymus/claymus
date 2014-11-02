package com.claymus.service.shared;

import java.util.List;

import com.claymus.service.shared.data.PageData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class GetPageListResponse implements IsSerializable {

	private List<PageData> pageDataList;
	
	private String cursor;

	
	@SuppressWarnings("unused")
	private GetPageListResponse() {}
	
	public GetPageListResponse( List<PageData> pageDataList, String cursor ) {
		this.pageDataList = pageDataList;
		this.cursor = cursor;
	}
	
	
	public List<PageData> getPageDataList() {
		return pageDataList;
	}

	public String getCursor() {
		return cursor;
	}
	
}
