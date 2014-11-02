package com.claymus.pagecontent.pages;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.pages.gae.PagesContentEntity;
import com.claymus.pagecontent.pages.shared.PagesContentData;

public class PagesContentHelper extends PageContentHelper<
		PagesContent,
		PagesContentData,
		PagesContentProcessor> {

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
		return 4.0;
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
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_LIST_PAGE_DATA );
	}
	
	public static boolean hasRequestAccessToAddPageData( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_ADD_PAGE_DATA );
	}

	public static boolean hasRequestAccessToUpdatePageData( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_PAGE_DATA );
	}

}
