package com.claymus.pagecontent.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.service.shared.data.UserData;

public class UserContentProcessor extends PageContentProcessor<UserContent> {

	@Override
	public String generateHtml(
			UserContent userContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! UserContentHelper.hasRequestAccessToListUserData( request ) )
			throw new InsufficientAccessException();
		

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<User> userList = dataAccessor.getUserList();

		ClaymusHelper claymusHelper = ClaymusHelper.get( request );
		List<UserData> userDataList = new ArrayList<>( userList.size() );
		for( User user : userList )
			userDataList.add( claymusHelper.createUserData( user ) );
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "userDataList", userDataList );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}

}
