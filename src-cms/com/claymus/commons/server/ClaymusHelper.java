package com.claymus.commons.server;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.claymus.commons.shared.UserStatus;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.RoleAccess;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.ApiProxy;

public class ClaymusHelper {

	public static final String SESSION_ATTRIB_CURRENT_USER_ID = "CurrentUserId";
	
	public static final String URL_RESOURCE =
			ClaymusHelper.getSystemProperty( "resource" );
	public static final String URL_RESOURCE_STATIC =
			ClaymusHelper.getSystemProperty( "resource.static" );

	private static final String URL_LOGIN_PAGE = "#signin";
	private static final String URL_LOGOUT_PAGE = "/logout?dest=";
	private static final String URL_REGISTER_PAGE = "#signup";
	private static final String URL_FORGOTPASSWORD_PAGE = "#forgotpassword";
	
	private final HttpServletRequest request;
	private final HttpSession session;
	
	private Long currentUserId;
	private User currentUser;
	private List<UserRole> currentUserRoleList;

	
	@Deprecated
	public ClaymusHelper() {
		this.request = null;
		this.session = null;
	}
	
	public ClaymusHelper( HttpServletRequest request ) {
		this.request = request;
		this.session = request.getSession();
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

	@SuppressWarnings("serial")
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
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
				currentUser = dataAccessor.getUser( getCurrentUserId() );
				dataAccessor.destroy();
			}
			
		}
		return currentUser;
	}
	
	public List<UserRole> getCurrentUserRoleList() {
		if( currentUserRoleList == null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			currentUserRoleList = dataAccessor.getUserRoleList( getCurrentUserId() );
			dataAccessor.destroy();
		}
		return currentUserRoleList;
	}
	
	public String getCurrentUserTimeZone() {
		return "Asia/Kolkata";
	}
	
	@Deprecated
	public static boolean isUserAdmin() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.isUserLoggedIn() && userService.isUserAdmin();
	}
	
	public boolean hasUserAccess( String accessId, boolean defaultAccess ) {
		Boolean access = null;

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		for( UserRole userRole : getCurrentUserRoleList() ) {
			RoleAccess roleAccess = dataAccessor
					.getRoleAccess( userRole.getRoleId(), accessId );
			if( roleAccess != null ) {
				access = roleAccess.hasAccess();
				if( access )
					break;
			}
		}
		dataAccessor.destroy();
		
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
	
}
