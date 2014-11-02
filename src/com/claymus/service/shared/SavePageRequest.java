package com.claymus.service.shared;

import com.claymus.service.shared.data.PageData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SavePageRequest implements IsSerializable {

	private PageData pageData;
	

	@SuppressWarnings("unused")
	private SavePageRequest() {}
	
	public SavePageRequest( PageData pageData ) {
		this.pageData = pageData;
	}
	
	
	public PageData getPageData() {
		return pageData;
	}
	
}
