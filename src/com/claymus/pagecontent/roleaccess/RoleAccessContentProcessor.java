package com.claymus.pagecontent.roleaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;

public class RoleAccessContentProcessor extends PageContentProcessor<RoleAccessContent> {

	private String[] roleList = new String[]{ "guest", "member", "administrator" };
	
	
	@Override
	public String generateHtml(
			RoleAccessContent roleAccessContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		if( ! RoleAccessContentHelper.hasRequestAccessToListAccessData( request ) )
			throw new InsufficientAccessException();
		
		boolean showUpdateOptions =
				RoleAccessContentHelper.hasRequestAccessToUpdateAccessData( request );

		@SuppressWarnings("rawtypes")
		List<PageContentHelper> pageContentHelperList =
				PageContentRegistry.getPageContentHelperList();
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "pageContentHelperList", pageContentHelperList );
		dataModel.put( "roleIdList", roleList );
		dataModel.put( "dataAccessor", dataAccessor );
		dataModel.put( "showUpdateOptions", showUpdateOptions );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}

}
