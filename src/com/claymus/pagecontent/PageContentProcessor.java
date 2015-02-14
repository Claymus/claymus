package com.claymus.pagecontent;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.UserRole;

public abstract class PageContentProcessor<T extends PageContent> {

	protected enum CacheLevel {
		USER,
		USER_ROLE,
		GLOBAL,
		NONE
	}
	
	
	public Resource[] getDependencies( T pageContent, HttpServletRequest request ) {
		return new Resource[]{};
	}

	public final String getTitle( T pageContent, HttpServletRequest request ) {
		return generateTitle( pageContent, request );
	}
	
	public final String getHtml( T pageContent, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		Memcache memcache = DataAccessorFactory.getL2CacheAccessor();
		CacheLevel cacheLevel = getCacheLevel();
		String html = null;
		
		if( cacheLevel != CacheLevel.NONE ) {
			ClaymusHelper claymusHelper = ClaymusHelper.get( request );
			String key = getClass().getSimpleName()
					+ "-" + pageContent.getId()
					+ "-" + pageContent.getLastUpdated().getTime();
			switch( cacheLevel ) {
				case USER:
					key += "-User-" + claymusHelper.getCurrentUserId();
					break;
				case USER_ROLE:
					key += "-UserRole";
					for( UserRole userRole : claymusHelper.getCurrentUserRoleList() )
						key += "-" + userRole.getRoleId();
					break;
				case GLOBAL:
					key += "-Global";
					break;
				default:
					break;
			}
			html = memcache.get( key );
			
			if( html == null ) {
				html = generateHtml( pageContent, request );
				memcache.put( key, html, 60 * 60 * 1000 );
			}

		} else {
			html = generateHtml( pageContent, request );
		}
		
		return html;
	}

	
	protected CacheLevel getCacheLevel() {
		return CacheLevel.NONE;
	}
	
	
	public String generateTitle( T pageContent, HttpServletRequest request ) {
		return null;
	}
	
	public String generateHtml( T pageContent, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		return FreeMarkerUtil.processTemplate( pageContent, getTemplateName( request ) );
	}
	
	protected String getTemplateName() {
		return this.getClass().getName().replaceAll( "[.]", "/" ).replace( "Processor", ".ftl" );
	}
	
	protected String getTemplateName( HttpServletRequest request ) {
		return ClaymusHelper.get( request ).isModeBasic()
				? this.getClass().getName().replaceAll( "[.]", "/" ).replace( "Processor", "Basic.ftl" )
				: this.getClass().getName().replaceAll( "[.]", "/" ).replace( "Processor", ".ftl" );
	}

}
