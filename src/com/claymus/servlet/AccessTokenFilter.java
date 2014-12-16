package com.claymus.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.AccessTokenType;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.AccessToken;

public class AccessTokenFilter implements Filter {
	
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

		ClaymusHelper claymusHelper = ClaymusHelper.get( request );
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		String accessTokenId = claymusHelper.getCookieValue( "access_token" );
		AccessToken accessToken = dataAccessor.getAccessToken( accessTokenId );
		if( accessToken == null ) {
			accessToken = dataAccessor.newAccessToken();
			accessToken.setType( AccessTokenType.USER );
			accessToken.setUserId( 0L );
			accessToken.setExpiry( new Date( new Date().getTime() + 604800000 ) ); // 1Wk
			accessToken = dataAccessor.createAccessToken( accessToken );
			
			accessTokenId = accessToken.getId();
			response.addCookie( new Cookie( "access_token", accessToken.getId() ) );
		}
		request.setAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN, accessTokenId );
		
		chain.doFilter( request, response );
	}

}
