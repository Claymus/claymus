package com.claymus.pagecontent.pages;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.ClaymusResource;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;

public class PagesContentProcessor extends PageContentProcessor<PagesContent> {

	@Override
	public Resource[] getDependencies( PagesContent pagesContent, HttpServletRequest request ) {
		
		ClaymusHelper claymusHelper = ClaymusHelper.get( request );

		if( claymusHelper.isModeBasic() ) {
			return new Resource[] {};
		
		} else {
			return new Resource[] {
					ClaymusResource.JQUERY_2,
					ClaymusResource.POLYMER,
					ClaymusResource.POLYMER_CORE_AJAX,
					ClaymusResource.POLYMER_CORE_ICON_BUTTON,
					ClaymusResource.POLYMER_PAPER_SPINNER,
					new Resource() {
						
						@Override
						public String getTag() {
							return "<link rel='import' href='/polymer/pagecontent-pages.html'>";
						}
						
					},
			};
		}
		
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

		return FreeMarkerUtil.processTemplate( pagesContent, getTemplateName( request ) );
	}

}
