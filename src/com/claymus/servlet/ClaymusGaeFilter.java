package com.claymus.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ClaymusGaeFilter implements Filter {
	
	@Override
	public void init( FilterConfig config ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter(
			ServletRequest req, ServletResponse resp,
			FilterChain chain ) throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		UserService userService = UserServiceFactory.getUserService();
		
		if( request.getRequestURI().equals( "/login" ) ) { // Google account login
			
			if( userService.isUserLoggedIn() ) {
				com.google.appengine.api.users.User gaeUser = userService.getCurrentUser();
				User user = dataAccessor.getUserByEmail( gaeUser.getEmail() );
				if( user == null ) {
					user = dataAccessor.newUser();
					user.setNickName( gaeUser.getNickname() );
					user.setEmail( gaeUser.getEmail() );
					user.setSignUpDate( new Date() );
					user = dataAccessor.createOrUpdateUser( user );

					if( userService.isUserAdmin() ) {
						UserRole userRole = dataAccessor.newUserRole();
						userRole.setUserId( user.getId() );
						userRole.setRoleId( "administrator" );
						userRole = dataAccessor.createOrUpdateUserRole( userRole );
					}
				}
// TODO: FIX IT	request.getSession().setAttribute( ClaymusHelper.SESSION_ATTRIB_CURRENT_USER_ID, user.getId() );
				response.sendRedirect( "/" );

			} else {
				response.sendRedirect( userService.createLoginURL( "/login" ) );
			}

			
		} else if( request.getRequestURI().equals( "/logout" ) ) { // Google account logout
			response.sendRedirect( userService.createLogoutURL( "/" ) );

			
		} else {
			chain.doFilter( req, resp );
			
		}
	}

}
