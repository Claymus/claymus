package com.claymus.api.shared;

import com.claymus.api.annotation.Validate;

@SuppressWarnings("serial")
public class GetPageContentListRequest extends GenericRequest {

	@Validate( required = true )
	private Long pageId;
	

	public Long getPageId() {
		return pageId;
	}

}