package com.claymus.commons.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.UserRole;

public class UserAccessHelper {
	
	public static boolean hasUserAccess( Long userId, Access access, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<UserRole> userRoleList = dataAccessor.getUserRoleList( userId );

		Boolean userAccess = null;
		for( UserRole userRole : userRoleList ) {
			RoleAccess roleAccess = dataAccessor.getRoleAccess( userRole.getRoleId(), access.getId() );
			if( roleAccess != null ) {
				userAccess = roleAccess.hasAccess();
				if( userAccess )
					break;
			}
		}
		
		if( userAccess == null )
			return access.getDefault();
		
		return userAccess;
	}

}