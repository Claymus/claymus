package com.claymus.pagecontent.pages;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.ClaymusResource;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class PagesContentProcessor extends PageContentProcessor<PagesContent> {

	@Override
	public Resource[] getDependencies( PagesContent pagesContent, HttpServletRequest request ) {
		return new Resource[] {
				ClaymusResource.JQUERY_2,
				ClaymusResource.POLYMER,
				ClaymusResource.POLYMER_CORE_AJAX,
				ClaymusResource.POLYMER_CORE_TOOLBAR,
				ClaymusResource.POLYMER_PAPER_DIALOG,
				ClaymusResource.POLYMER_PAPER_ACTION_DIALOG,
				ClaymusResource.POLYMER_PAPER_FAB,
				ClaymusResource.POLYMER_PAPER_SLIDER,
				ClaymusResource.POLYMER_PAPER_SPINNER,
				new Resource() {
					
					@Override
					public String getTag() {
						return "<link rel='import' href='/polymer/pagecontent-pages.html'>";
					}
					
				},
		};
	}

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
