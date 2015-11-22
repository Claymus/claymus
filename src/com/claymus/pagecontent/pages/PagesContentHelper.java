package com.claymus.pagecontent.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.UserAccessHelper;
import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.ClaymusPageType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AuditLog;
import com.claymus.data.transfer.shared.PageData;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.pages.gae.PagesContentEntity;
import com.claymus.pagecontent.pages.shared.PagesContentData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.Page;

public class PagesContentHelper extends PageContentHelper<
		PagesContent,
		PagesContentData,
		PagesContentProcessor> {

	private static final Gson gson = new GsonBuilder().create();

	private static final Access ACCESS_TO_LIST_PAGE_DATA =
			new Access( "page_data_list", false, "View Page Data" );
	private static final Access ACCESS_TO_ADD_PAGE_DATA =
			new Access( "page_data_add", false, "Add Page Data" );
	private static final Access ACCESS_TO_UPDATE_PAGE_DATA =
			new Access( "page_data_update", false, "Update Page Data" );

	
	@Override
	public String getModuleName() {
		return "Pages";
	}

	@Override
	public Double getModuleVersion() {
		return 5.1;
	}

	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_PAGE_DATA,
				ACCESS_TO_ADD_PAGE_DATA,
				ACCESS_TO_UPDATE_PAGE_DATA
		};
	};
	

	public static PagesContent newPagesContent() {
		return new PagesContentEntity();
	}
	

	public static boolean hasRequestAccessToListPageData( HttpServletRequest request ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) )
			return UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_LIST_PAGE_DATA, request );
		return false;
	}
	
	public static boolean hasRequestAccessToAddPageData( HttpServletRequest request ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) )
			return UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_ADD_PAGE_DATA, request );
		return false;
	}

	public static boolean hasRequestAccessToUpdatePageData( HttpServletRequest request ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) )
			return UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_UPDATE_PAGE_DATA, request );
		return false;
	}
	
	
	private static PageData createPageData( Page page ) {
		PageData pageData = new PageData();
		
		pageData.setId( page.getId() );
		pageData.setUri( page.getUri() );
		pageData.setUriAlias( page.getUriAlias() );
		pageData.setTitle( page.getTitle() );
		
		return pageData;
	}
	
	
	public static DataListCursorTuple<PageData> getPageList( String cursor, int resultCount, HttpServletRequest request )
			throws InsufficientAccessException {
		
		if( ! hasRequestAccessToListPageData( request ) )
			throw new InsufficientAccessException();

		DataListCursorTuple<Page> pageListCursorTuple = DataAccessorFactory
				.getDataAccessor( request )
				.getPageList( cursor, resultCount );
		
		List<PageData> pageDataList = new ArrayList<PageData>( pageListCursorTuple.getDataList().size() );
		for( Page page : pageListCursorTuple.getDataList() )
			pageDataList.add( createPageData( page ) );
		
		return new DataListCursorTuple<PageData>( pageDataList, pageListCursorTuple.getCursor() );
	}

	public static void savePage( PageData pageData, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException {

		Long pageId = pageData.getId();
		String uriAlias = pageData.getUriAlias().toLowerCase();
		String title = pageData.getTitle();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page = dataAccessor.getPage( uriAlias );
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );

		if( pageId == null ) { // Add page usecasee
			
			if( ! hasRequestAccessToAddPageData( request ) )
				throw new InsufficientAccessException();

			if( page != null )
				throw new InvalidArgumentException( "Another page is already associated with this URL." );
			
			page = dataAccessor.newPage();
			
			auditLog.setAccessId( ACCESS_TO_ADD_PAGE_DATA.getId() );
			auditLog.setEventDataOld( gson.toJson( page ) );
			
			page.setType( ClaymusPageType.GENERIC.toString() );
			page.setUriAlias( uriAlias );
			page.setTitle( title );
			page.setCreationDate( new Date() );

			
		} else { // Update page usecase

			if( ! hasRequestAccessToUpdatePageData( request ) )
				throw new InsufficientAccessException();

			if( page != null && ! page.getId().equals( pageId ) )
				throw new InvalidArgumentException( "Another page is already associated with this URL." );

			if( page == null )
				page = dataAccessor.getPage( pageId );

			auditLog.setAccessId( ACCESS_TO_UPDATE_PAGE_DATA.getId() );
			auditLog.setEventDataOld( gson.toJson( page ) );
			
			page.setUri( uriAlias );
			page.setTitle( title );
		}

		page = dataAccessor.createOrUpdatePage( page );
		
		auditLog.setEventDataNew( gson.toJson( page ) );
		auditLog = dataAccessor.createAuditLog( auditLog );
	}

}
