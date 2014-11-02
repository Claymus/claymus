package com.claymus.service.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SavePageContentResponse implements IsSerializable {
	
	private Long pageContentId;
	
	
	@SuppressWarnings("unused")
	private SavePageContentResponse() {}
	
	public SavePageContentResponse( Long pageContentId ) {
		this.pageContentId = pageContentId;
	}
	
	
	public Long getPageContentId() {
		return pageContentId;
	}
	
}
