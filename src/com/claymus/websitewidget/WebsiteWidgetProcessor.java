package com.claymus.websitewidget;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.UserRole;
import com.claymus.data.transfer.WebsiteWidget;

public abstract class WebsiteWidgetProcessor<T extends WebsiteWidget> {

	protected enum CacheLevel {
		USER,
		USER_ROLE,
		GLOBAL,
		NONE
	}
	
	
	public String getHtml( T websiteWidget, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		Memcache memcache = DataAccessorFactory.getL2CacheAccessor();
		CacheLevel cacheLevel = getCacheLevel();
		String html = null;
		
		if( cacheLevel != CacheLevel.NONE ) {
			ClaymusHelper claymusHelper = ClaymusHelper.get( request );
			String key = getClass().getSimpleName()
					+ "-" + websiteWidget.getId()
					+ "-" + websiteWidget.getLastUpdated().getTime();
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
				html = generateHtml( websiteWidget, request );
				memcache.put( key, html, 60 * 60 * 1000 );
			}

		} else {
			html = generateHtml( websiteWidget, request );
		}
		
		return html;
	}

	
	protected CacheLevel getCacheLevel() {
		return CacheLevel.NONE;
	}
	
	
	public String generateHtml( T websiteWidget, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		return FreeMarkerUtil.processTemplate( websiteWidget, getTemplateName() );
	}
	
	protected String getTemplateName() {
		return this.getClass().getName().replaceAll( "[.]", "/" ).replace( "Processor", ".ftl" );
	}
	
}
