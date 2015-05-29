package com.claymus.websitewidget;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.Resource;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.UserRole;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.servlet.AccessTokenFilter;
import com.claymus.servlet.UxModeFilter;

public abstract class WebsiteWidgetProcessor<T extends WebsiteWidget> {

	protected enum CacheLevel {
		NONE,
		USER,
		USER_ROLE,
		GLOBAL,
	}
	
	
	public Resource[] getDependencies( T websiteWidget ) {
		return new Resource[]{};
	}

	public final String getHtml( T websiteWidget )
			throws InsufficientAccessException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Memcache memcache = DataAccessorFactory.getL2CacheAccessor();
		CacheLevel cacheLevel = getCacheLevel();
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		String html = null;
		
		if( cacheLevel != CacheLevel.NONE
				&& accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {

			String key = getClass().getSimpleName()
					+ "-" + websiteWidget.getId()
					+ "-" + websiteWidget.getLastUpdated().getTime();
			switch( cacheLevel ) {
				case USER:
					key += "-User-" + accessToken.getUserId();
					break;
				case USER_ROLE:
					key += "-UserRole";
					for( UserRole userRole : dataAccessor.getUserRoleList( accessToken.getUserId() ) )
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
				html = generateHtml( websiteWidget );
				memcache.put( key, html, 60 * 60 * 1000 );
			}

		} else {
			html = generateHtml( websiteWidget );
		}
		
		return html;
	}

	
	protected CacheLevel getCacheLevel() {
		return CacheLevel.NONE;
	}
	
	
	public String generateHtml( T websiteWidget )
			throws InsufficientAccessException, UnexpectedServerException {
		
		return FreeMarkerUtil.processTemplate( websiteWidget, getTemplateName() );
	}
	
	protected String getTemplateName() {
		return hasBasicMode() && UxModeFilter.isModeBasic()
				? this.getClass().getName().replaceAll( "[.]", "/" ).replace( "Processor", "Basic.ftl" )
				: this.getClass().getName().replaceAll( "[.]", "/" ).replace( "Processor", ".ftl" );
	}

	protected boolean hasBasicMode() {
		return true;
	}
	
}
