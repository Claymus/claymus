package com.claymus.pagecontent.roleaccess;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.roleaccess.gae.RoleAccessContentEntity;
import com.claymus.pagecontent.roleaccess.shared.RoleAccessContentData;

public class RoleAccessContentHelper extends PageContentHelper<
		RoleAccessContent,
		RoleAccessContentData,
		RoleAccessContentProcessor> {
	
	private static final Access ACCESS_TO_LIST_ACCESS_DATA =
			new Access( "access_data_list", false, "View Acces Data" );
	private static final Access ACCESS_TO_UPDATE_ACCESS_DATA =
			new Access( "access_data_update", false, "Update Access Data" );

	
	@Override
	public String getModuleName() {
		return "Role Access";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_ACCESS_DATA,
				ACCESS_TO_UPDATE_ACCESS_DATA };
	}
	
	
	public static RoleAccessContent newRoleAccessContent() {
		return new RoleAccessContentEntity();
	}

	
	public static boolean hasRequestAccessToListAccessData( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_LIST_ACCESS_DATA );
	}
	
	public static boolean hasRequestAccessToUpdateAccessData( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_UPDATE_ACCESS_DATA );
	}

}
