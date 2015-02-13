package com.claymus.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class ClaymusServiceFilter implements Filter {
	
	public static final Gson gson = new GsonBuilder().create();

	
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

		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/remote-api." ) || requestUri.equals( "/oauth" ) ) {
			chain.doFilter( request, response );
			return;
		}
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken;
		
		String accessTokenId = request.getParameter( "accessToken" );
		if( accessTokenId == null || accessTokenId.isEmpty() ) {
			String requestPayload = IOUtils.toString( request.getInputStream(), "UTF-8" );
			accessTokenId = requestPayload == null || requestPayload.isEmpty()
					? null
					: gson.fromJson( requestPayload, JsonElement.class ).getAsJsonObject().get( "accessToken" ).getAsString();
		}
		
		if( accessTokenId == null ) {
			send401Resposne( response, "Access Token is missing." );

		} else if( ( accessToken = dataAccessor.getAccessTokenById( accessTokenId ) ) == null ) {
			send401Resposne( response, "Access Token is invalid." );
		
		} else if( accessToken.isExpired() ) {
			send401Resposne( response, "Access Token is expired." );

		} else {
			request.setAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN, accessToken );
			chain.doFilter( request, response );
		}
		
		dataAccessor.destroy();
	}
	
	private void send401Resposne( HttpServletResponse response, String message ) throws IOException {
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter writer = response.getWriter();
		response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		writer.println( message );
		writer.close();
	}
	
}
