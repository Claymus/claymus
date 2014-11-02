package com.claymus.pagecontent.pages;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class PagesContentProcessor extends PageContentProcessor<PagesContent> {

	@Override
	public String generateHtml(
			PagesContent pagesContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException { 
		
		if( ! PagesContentHelper.hasRequestAccessToListPageData( request ) )
			throw new InsufficientAccessException();

		return super.processTemplate( pagesContent, getTemplateName() );
	}

}
