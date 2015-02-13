package com.claymus.pagecontent.pages.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetPageListRequest extends GenericRequest {

	private String cursor;
	
	private Integer resultCount;
	
	
	public String getCursor() {
		return cursor;
	}
	
	public int getResultCount() {
		return resultCount;
	}

}
