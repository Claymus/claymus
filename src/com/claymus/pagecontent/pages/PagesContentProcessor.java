package com.claymus.pagecontent.pages;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class PagesContentProcessor extends PageContentProcessor<PagesContent> {

	@Override
	public String generateTitle( PagesContent pagesContent, HttpServletRequest request ) {
		return "Pages";
	}

	@Override
	public String generateHtml( PagesContent pagesContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException { 
		
		if( ! PagesContentHelper.hasRequestAccessToListPageData( request ) )
			throw new InsufficientAccessException();

		return FreeMarkerUtil.processTemplate( pagesContent, getTemplateName() );
	}

}
