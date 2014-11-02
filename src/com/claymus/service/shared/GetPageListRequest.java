package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GetPageListRequest implements IsSerializable {

	private String cursor;
	
	private int resultCount;
	
	
	@SuppressWarnings("unused")
	private GetPageListRequest() {}

	public GetPageListRequest( String cursor, int resultCount ) {
		this.cursor = cursor;
		this.resultCount = resultCount;
	}

	
	public String getCursor() {
		return cursor;
	}
	
	public int getResultCount() {
		return resultCount;
	}

}
