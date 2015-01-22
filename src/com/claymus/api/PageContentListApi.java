package com.claymus.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.shared.GetPageContentListRequest;
import com.claymus.api.shared.GetPageContentListResponse;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.PageContent;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.service.shared.data.PageContentData;


@SuppressWarnings("serial")
@Bind( uri = "/pagecontent/list" )
public class PageContentListApi extends GenericApi {

	@Get
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GetPageContentListResponse getPageContentList( GetPageContentListRequest request ) {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<PageContent> pageContentList = dataAccessor.getPageContentList( request.getPageId() );

		List<PageContentData> pageContentDataList = new ArrayList<>( pageContentList.size() );
		for( PageContent pageContent : pageContentList ) {
			PageContentHelper pageContentHelper
					= PageContentRegistry.getPageContentHelper( pageContent.getClass() );
			PageContentProcessor pageContentProcessor
					= PageContentRegistry.getPageContentProcessor( pageContent.getClass() );

			PageContentData pageContentData = new PageContentData();
			pageContentData.setId( pageContent.getId() );
			pageContentData.setName( pageContentHelper.getModuleName() );
			pageContentData.setTitle( pageContentProcessor.getTitle( pageContent, this.getThreadLocalRequest() ) );
			pageContentDataList.add( pageContentData );
		}
		
		return new GetPageContentListResponse( pageContentDataList );
	}

}
