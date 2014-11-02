package com.claymus.commons.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.Memcache;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.service.shared.data.PageData;
import com.claymus.service.shared.data.UserData;
import com.google.apphosting.api.ApiProxy;

@SuppressWarnings("serial")
public class ClaymusHelper implements Serializable {

	public static final String SESSION_ATTRIB_CURRENT_USER_ID = "CurrentUserId";
	
	public static final String URL_RESOURCE =
			ClaymusHelper.getSystemProperty( "resource" );
	public static final String URL_RESOURCE_STATIC =
			ClaymusHelper.getSystemProperty( "resource.static" );

	private static final String URL_LOGIN_PAGE = "#signin";
	private static final String URL_LOGOUT_PAGE = "/logout?dest=";
	private static final String URL_REGISTER_PAGE = "#signup";
	private static final String URL_FORGOTPASSWORD_PAGE = "#forgotpassword";
	
	protected final HttpServletRequest request;
	private final HttpSession session;
	
	private Long currentUserId;
	private User currentUser;
	private List<UserRole> currentUserRoleList;

	
	protected ClaymusHelper( HttpServletRequest request ) {
		this.request = request;
		this.session = request.getSession();
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
	
	
	public boolean isUserLoggedIn() {
		if( currentUserId == null ) {
			currentUserId = (Long) session.getAttribute( SESSION_ATTRIB_CURRENT_USER_ID );
			if( currentUserId == null )
				currentUserId = 0L;
		}
		return currentUserId != 0L;
	}

	public Long getCurrentUserId() {
		if( currentUserId == null ) {
			currentUserId = (Long) session.getAttribute( SESSION_ATTRIB_CURRENT_USER_ID );
			if( currentUserId == null )
				currentUserId = 0L;
		}
		return currentUserId;
	}

	public User getCurrentUser() {
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
	
	public List<UserRole> getCurrentUserRoleList() {
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
	
	public String getCurrentUserTimeZone() {
		return "Asia/Kolkata";
	}
	
	public boolean hasUserAccess( Access access ) {
		return hasUserAccess( access.getId(), access.getDefault() );
	}

	public boolean hasUserAccess( String accessId, boolean defaultAccess ) {
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
	
	public String createLoginURL() {
		return URL_LOGIN_PAGE;
	}

	public static String createLoginURL( String destinationURL ) {
		return URL_LOGIN_PAGE + destinationURL;
	}

	public String createLogoutURL() {
		return URL_LOGOUT_PAGE + request.getRequestURI();
	}

	public static String createLogoutURL( String destinationURL ) {
		return URL_LOGOUT_PAGE + destinationURL;
	}

	public String createRegisterURL() {
		return URL_REGISTER_PAGE;
	}
	
	public String createForgotPasswordURL() {
		return URL_FORGOTPASSWORD_PAGE;
	}

	
	public static String getSystemProperty( String propertyName ) {
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		if( appId.startsWith("s~") )
			appId = appId.substring( 2 );
		return System.getProperty( appId + "." + propertyName );
	}
	
	public UserData createUserData( User user ) {
		UserData userData = new UserData();
		
		userData.setId( user.getId() );
		userData.setFirstName( user.getFirstName() );
		userData.setLastName( user.getLastName() );
		userData.setEmail( user.getEmail() );
		userData.setStatus( user.getStatus() );
		
		return userData;
	}
	
	public PageData createPageData( Page page ) {
		PageData pageData = new PageData();
		
		pageData.setId( page.getId() );
		pageData.setUri( page.getUri() );
		pageData.setUriAlias( page.getUriAlias() );
		pageData.setTitle( page.getTitle() );
		
		return pageData;
	}
	
	public String generateUriAlias( String oldUriAlias, String uriPrefix, String... keywords ) {
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
	
}
