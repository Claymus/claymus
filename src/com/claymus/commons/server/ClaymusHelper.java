package com.claymus.commons.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.data.transfer.shared.PageData;
import com.claymus.service.shared.data.UserData;
import com.google.apphosting.api.ApiProxy;

@SuppressWarnings("serial")
public class ClaymusHelper implements Serializable {

	public static final String REQUEST_ATTRIB_MODE_BASIC = "BasicMode";
	public static final String REQUEST_ATTRIB_MODE_EMBED = "EmbedMode";
	public static final String REQUEST_ATTRIB_ACCESS_TOKEN = "AccessToken";
	@Deprecated
	public static final String REQUEST_ATTRIB_ACCESS_TOKEN_ID = "AccessTokenId";
	public static final String SESSION_ATTRIB_CURRENT_USER_ID = "CurrentUserId";
	@Deprecated
	public static final String SEARCH_INDEX_NAME = "GLOBAL_INDEX";
	
	public static final String URL_RESOURCE =
			ClaymusHelper.getSystemProperty( "resource" );
	public static final String URL_RESOURCE_STATIC =
			ClaymusHelper.getSystemProperty( "resource.static" );

	private static final String URL_LOGIN_PAGE = "#signin";
	private static final String URL_LOGOUT_PAGE = "/logout?dest=";
	private static final String URL_REGISTER_PAGE = "#signup";
	private static final String URL_FORGOTPASSWORD_PAGE = "#forgotpassword";
	
	protected final HttpServletRequest request;
	
	private User currentUser;
	private List<UserRole> currentUserRoleList;
	
	private AccessToken accessToken;

	
	protected ClaymusHelper( HttpServletRequest request ) {
		this.request = request;
	}
	
	public static ClaymusHelper get( HttpServletRequest request ) {
		Memcache memcache = DataAccessorFactory.getL1CacheAccessor();
		ClaymusHelper claymusHelper = memcache.get( "ClaymusHelper-" + request.hashCode() );
		if( claymusHelper == null ) {
			claymusHelper = new ClaymusHelper( request );
			memcache.put( "ClaymusHelper-" + request.hashCode(), claymusHelper );
		}
		return claymusHelper;
	}
	
	
	public final boolean isUserLoggedIn() {
		if( accessToken == null )
			accessToken = ( AccessToken ) request.getAttribute( REQUEST_ATTRIB_ACCESS_TOKEN );
		return accessToken.getUserId() != 0L;
	}

	@Deprecated
	public final Long getCurrentUserId() {
		if( accessToken == null )
			accessToken = ( AccessToken ) request.getAttribute( REQUEST_ATTRIB_ACCESS_TOKEN );
		return accessToken.getUserId();
	}

	public final User getCurrentUser() {
		if( currentUser == null ) {

			if( getCurrentUserId() == 0L ) {
				currentUser = new User() {

					@Override
					public Long getId() {
						return 0L;
					}

					@Override
					public void setId( Long id ) { }

					@Override
					public String getPassword() {
						return null;
					}

					@Override
					public void setPassword( String password ) { }

					@Override
					public String getFirstName() {
						return "Anonymous";
					}

					@Override
					public void setFirstName( String firstName ) { }

					@Override
					public String getLastName() {
						return "User";
					}

					@Override
					public void setLastName( String lastName ) { }

					@Override
					public String getNickName() {
						return "Anonymous User";
					}

					@Override
					public void setNickName( String nickName ) { }

					@Override
					public String getEmail() {
						return null;
					}

					@Override
					public void setEmail( String email ) { }

					@Override
					public String getPhone() {
						return null;
					}

					@Override
					public void setPhone( String phone ) { }

					@Override
					public String getCampaign() {
						return null;
					}

					@Override
					public void setCampaign( String campaign ) { }

					@Override
					public String getReferer() {
						return null;
					}

					@Override
					public String setReferer( String referer ) {
						return null;
					}

					@Override
					public Date getSignUpDate() {
						return new Date();
					}

					@Override
					public void setSignUpDate( Date date ) { }

					@Override
					public UserStatus getStatus() {
						return null;
					}

					@Override
					public void setStatus( UserStatus userStatus ) { }
					
				};
			} else {
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
				currentUser = dataAccessor.getUser( getCurrentUserId() );
			}
			
		}
		return currentUser;
	}
	
	public final List<UserRole> getCurrentUserRoleList() {
		if( getCurrentUserId() == 0L ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			UserRole userRole = dataAccessor.newUserRole();
			userRole.setRoleId( "guest" );
			userRole.setUserId( 0L );
			
			currentUserRoleList = new ArrayList<>( 1 );
			currentUserRoleList.add( userRole );
			
		} else if( currentUserRoleList == null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
			currentUserRoleList = dataAccessor.getUserRoleList( getCurrentUserId() );
		}
		
		return currentUserRoleList;
	}
	
	public final String getCurrentUserTimeZone() {
		return "Asia/Kolkata";
	}
	
	public final boolean isModeBasic() {
		return request.getAttribute( REQUEST_ATTRIB_MODE_BASIC ) != null
				&& (boolean) request.getAttribute( REQUEST_ATTRIB_MODE_BASIC );
	}
	
	public final boolean isModeEmbed() {
		return request.getAttribute( REQUEST_ATTRIB_MODE_EMBED ) != null
				&& (boolean) request.getAttribute( REQUEST_ATTRIB_MODE_EMBED );
	}
	
	public final String getCookieValue( String cookieName ) {
		Cookie[] cookies = request.getCookies();
		if( cookies == null ) return null;
		for( Cookie cookie : cookies ) {
			if( cookie.getName().equals( cookieName ) )
				return cookie.getValue();
		}
		return null;
	}
	
	@Deprecated
	public final boolean hasUserAccess( Access access ) {
		return hasUserAccess( access.getId(), access.getDefault() );
	}

	@Deprecated
	public final boolean hasUserAccess( String accessId, boolean defaultAccess ) {
		Boolean access = null;

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		for( UserRole userRole : getCurrentUserRoleList() ) {
			RoleAccess roleAccess = dataAccessor
					.getRoleAccess( userRole.getRoleId(), accessId );
			if( roleAccess != null ) {
				access = roleAccess.hasAccess();
				if( access )
					break;
			}
		}
		
		if( access == null )
			return defaultAccess;
		
		return access;
	}
	
	public final String createLoginURL() {
		return URL_LOGIN_PAGE;
	}

	public final static String createLoginURL( String destinationURL ) {
		return URL_LOGIN_PAGE + destinationURL;
	}

	public final String createLogoutURL() {
		return URL_LOGOUT_PAGE + request.getRequestURI();
	}

	public final String createLogoutURL( String destinationURL ) {
		return URL_LOGOUT_PAGE + destinationURL;
	}

	public final String createRegisterURL() {
		return URL_REGISTER_PAGE;
	}
	
	public final String createForgotPasswordURL() {
		return URL_FORGOTPASSWORD_PAGE;
	}

	
	public static final String getSystemProperty( String propertyName ) {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.startsWith("s~") )
			appId = appId.substring( 2 );
		return System.getProperty( appId + "." + propertyName );
	}
	
	public final UserData createUserData( User user ) {
		UserData userData = new UserData();
		
		userData.setId( user.getId() );
		userData.setFirstName( user.getFirstName() );
		userData.setLastName( user.getLastName() );
		userData.setEmail( user.getEmail() );
		userData.setStatus( user.getStatus() );
		
		return userData;
	}
	
	public final PageData createPageData( Page page ) {
		PageData pageData = new PageData();
		
		pageData.setId( page.getId() );
		pageData.setUri( page.getUri() );
		pageData.setUriAlias( page.getUriAlias() );
		pageData.setTitle( page.getTitle() );
		
		return pageData;
	}
	
	public final String generateUriAlias( String oldUriAlias, String uriPrefix, String... keywords ) {
		String uriAlias = uriPrefix;
		for( String keyword : keywords )
			if( keyword != null )
				uriAlias += keyword
								.trim()
								.toLowerCase()
								.replaceAll( "[^a-z0-9]+", "-" )
						+ "-";
		
		uriAlias = uriAlias.replaceAll( "[-]+", "-" );
		if( uriAlias.length() > uriPrefix.length() ) {
			if( uriAlias.charAt( uriPrefix.length() ) == '-' )
				uriAlias = uriPrefix + uriAlias.substring( uriPrefix.length() + 1, uriAlias.length() - 1 );
			else
				uriAlias = uriAlias.substring( 0, uriAlias.length() - 1 );
		} else {
			uriAlias = uriAlias + "page";
		}
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		for( int i = 0; ; i++ ) {
			String aUriAlias = i == 0 ? uriAlias :  uriAlias + "-" + i;
			if( oldUriAlias != null && oldUriAlias.equals( aUriAlias ) )
				return aUriAlias;

			Page page = dataAccessor.getPage( aUriAlias );
			if( page == null )
				return aUriAlias;
		}

	}
	
	//Update access token entity on user login/logout/register
	public final AccessToken updateAccessToken( String accessTokenId, 
											Long userId, Date loginDate, 
											Date logoutDate, Date expiry ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		
		AccessToken accessToken = dataAccessor.getAccessToken( accessTokenId );
		
		if( userId != null )
			accessToken.setUserId( userId );
		
		if( loginDate != null )
			accessToken.setLogInDate( loginDate );
		
		if( logoutDate != null )
			accessToken.setLogOutDate( logoutDate );
		
		if( expiry != null )
			accessToken.setExpiry( expiry );
		
		dataAccessor.updateAccessToken( accessToken );
		
		return accessToken;
		
	}
	
}
