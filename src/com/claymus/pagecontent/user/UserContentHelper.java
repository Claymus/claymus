package com.claymus.pagecontent.user;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.user.gae.UserContentEntity;
import com.claymus.pagecontent.user.shared.UserContentData;

public class UserContentHelper extends PageContentHelper<
		UserContent,
		UserContentData,
		UserContentProcessor> {
	
	private static final Access ACCESS_TO_LIST_USER_DATA =
			new Access( "user_data_list", false, "View User Data" );

	
	@Override
	public String getModuleName() {
		return "User";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_USER_DATA };
	}
	

	public static UserContent newUserContent() {
		return new UserContentEntity();
	}

	
	public static boolean hasRequestAccessToListUserData( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).hasUserAccess( ACCESS_TO_LIST_USER_DATA );
	}
	
}
