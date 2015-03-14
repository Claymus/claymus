package com.claymus.pagecontent.pages.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.transfer.shared.PageData;
import com.claymus.pagecontent.pages.PagesContentHelper;
import com.claymus.pagecontent.pages.api.shared.PutPageRequest;
import com.claymus.pagecontent.pages.api.shared.PutPageResponse;

@SuppressWarnings("serial")
@Bind( uri = "/page" )
public class PageApi extends GenericApi {

	@Put
	public PutPageResponse putPage( PutPageRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		PageData pageData  = new PageData();
		pageData.setId( request.getId() );
		pageData.setUriAlias( request.getUriAlias() );
		pageData.setTitle( request.getTitle() );
		
		PagesContentHelper.savePage( pageData, this.getThreadLocalRequest() );
		
		return new PutPageResponse();
	}

}
