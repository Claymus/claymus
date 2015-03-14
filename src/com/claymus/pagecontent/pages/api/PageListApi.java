package com.claymus.pagecontent.pages.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.shared.PageData;
import com.claymus.pagecontent.pages.PagesContentHelper;
import com.claymus.pagecontent.pages.api.shared.GetPageListRequest;
import com.claymus.pagecontent.pages.api.shared.GetPageListResponse;

@SuppressWarnings("serial")
@Bind( uri = "/page/list" )
public class PageListApi extends GenericApi {

	@Get
	public GetPageListResponse getPageList( GetPageListRequest request )
			throws InsufficientAccessException {

		DataListCursorTuple<PageData> dataListCursorTuple = PagesContentHelper.getPageList(
				request.getCursor(),
				request.getResultCount(),
				this.getThreadLocalRequest() );
		
		return new GetPageListResponse(
				dataListCursorTuple.getDataList(),
				dataListCursorTuple.getCursor() );
	}

}
