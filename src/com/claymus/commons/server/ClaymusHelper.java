package com.claymus.commons.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.UserSignUpSource;
import com.claymus.commons.shared.UserState;
import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.data.transfer.shared.PageData;
import com.claymus.service.shared.data.UserData;
import com.google.apphosting.api.ApiProxy;
import com.pratilipi.commons.shared.UserGender;
import com.pratilipi.data.type.AccessToken;

@SuppressWarnings("serial")
public class ClaymusHelper implements Serializable {

	public static final String REQUEST_ATTRIB_MODE_BASIC = "BasicMode";
	public static final String REQUEST_ATTRIB_MODE_EMBED = "EmbedMode";
	public static final String REQUEST_ATTRIB_ACCESS_TOKEN = "AccessToken";
	@Deprecated
	public static final String SEARCH_INDEX_NAME = "GLOBAL_INDEX";
	
	@Deprecated
	public static final String URL_RESOURCE =
			ClaymusHelper.getSystemProperty( "resource" );
	@Deprecated
	public static final String URL_RESOURCE_STATIC =
			ClaymusHelper.getSystemProperty( "resource.static" );
	
	public static final long ACCESS_TOKEN_VALIDITY = 60 * 60 * 24 * 7 * 1000; // 1 Wk

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
		Logger.getLogger( ClaymusHelper.class.getName() ).log( Level.INFO, "Is Access Token Null : " + String.valueOf( accessToken == null ));
		if( accessToken == null )
			accessToken = ( AccessToken ) request.getAttribute( REQUEST_ATTRIB_ACCESS_TOKEN );
		Logger.getLogger( ClaymusHelper.class.getName() ).log( Level.INFO, "User ID : " + accessToken.getUserId() );
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

					@Override
					public Date getDateOfBirth() {
						return null;
					}

					@Override
					public void setDateOfBirth(Date dateOfBirth) { }

					@Override
					public UserGender getGender() {
						return null;
					}

					@Override
					public void setGender(UserGender userSex) { }

					@Override
					public UserState getState() {
						return null;
					}

					@Override
					public void setState(UserState userState) {	}

					@Override
					public UserSignUpSource getSignUpSource() {
						return null;
					}

					@Override
					public void setSignUpSource(UserSignUpSource signUpSource) { }


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
		String cookieString;
		if( request.getHeader( "Cookie" ) != null ){
			cookieString = request.getHeader( "Cookie" );
		}
		else {
			cookieString = request.getHeader( "Coikoe" );
		}
		
		if( cookieString != null ){
			String[] cookieArray = cookieString.split( ";" );
			for( int i=0; i < cookieArray.length; ++i ){
				if( cookieArray[ i ].contains( cookieName )){
					int index = cookieArray[ i ].indexOf( "=" );
					return cookieArray[ i ].substring( index + 1 );
				}
			}
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
		if( uriAlias.length() > uriPrefix.length() + 1 ) {
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
		
		accessToken.setLastUpdatedDate( new Date() );
		dataAccessor.updateAccessToken( accessToken );
		
		return accessToken;
		
	}
	
	public final AccessToken createAccessToken( Long userId ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = dataAccessor.newAccessToken();
		accessToken.setUserId( userId );
		accessToken.setType( ClaymusAccessTokenType.USER.toString() );
		accessToken.setExpiry( new Date( new Date().getTime() + ACCESS_TOKEN_VALIDITY ) ); // 1Wk
		accessToken.setLastUpdatedDate( new Date() );
		accessToken = dataAccessor.createAccessToken( accessToken );
		
		return accessToken;
	}
	
}
