package com.claymus.service.shared;

import com.claymus.service.shared.data.PageContentData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SavePageContentRequest implements IsSerializable {

	private PageContentData pageContentData;
	

	@SuppressWarnings("unused")
	private SavePageContentRequest() {}
	
	public SavePageContentRequest( PageContentData pageContentData ) {
		this.pageContentData = pageContentData;
	}
	
	
	public PageContentData getPageContentData() {
		return pageContentData;
	}
	
}
