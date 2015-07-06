package com.claymus.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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
		if( requestUri.equals( "/remote-api" )
				|| requestUri.equals( "/oauth" )
				|| requestUri.equals( "/register" )
				|| requestUri.equals( "/_ah/warmup" )
				|| request.getHeader( "X-AppEngine-QueueName" ) != null ) {
			chain.doFilter( request, response );
			return;
		}
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken;
		
		String accessTokenId = request.getParameter( "accessToken" );
		if( accessTokenId == null || accessTokenId.isEmpty() ) {
			final String requestPayload = IOUtils.toString( request.getInputStream(), "UTF-8" );
			accessTokenId = requestPayload == null || requestPayload.isEmpty()
					? null
					: gson.fromJson( requestPayload, JsonElement.class ).getAsJsonObject().get( "accessToken" ).getAsString();

			request = new HttpServletRequestWrapper( request ) {
				
				public ServletInputStream getInputStream() throws IOException {
					return new ServletInputStream() {
						InputStream in = new ByteArrayInputStream( requestPayload.getBytes( StandardCharsets.UTF_8 ) );
						@Override
						public int read() throws IOException {
							return in.read();
						}
					};
				}

			};
		}
		
		if( accessTokenId == null ) {
			send401Resposne( response, "Access Token is missing." );

		} else if( ( accessToken = dataAccessor.getAccessTokenById( accessTokenId ) ) == null ) {
			send401Resposne( response, "Access Token is invalid." );
		
		} else if( accessToken.isExpired() ) {
			send401Resposne( response, "Access Token is expired." );

		} else {
			Logger.getLogger( ClaymusServiceFilter.class.getName() ).log( Level.INFO, "Old Expiry" + accessToken.getExpiry() );
			boolean isUpdatedToday = false;
			if( accessToken.getLastUpdatedDate() != null ){
				Long timeDiff = new Date().getTime() - accessToken.getLastUpdatedDate().getTime();
				Logger.getLogger( ClaymusServiceFilter.class.getName() ).log( Level.INFO, "Time Diff : " + timeDiff );
				isUpdatedToday = timeDiff < 3600 * 24;
			}
			if( !isUpdatedToday
					&& accessToken.getUserId() != 0L 
					&& accessToken.getLogOutDate() == null ){
				Logger.getLogger( ClaymusServiceFilter.class.getName() ).log( Level.INFO, "Access Token expiry updated" );
				accessToken.setExpiry( new Date( new Date().getTime() + ClaymusHelper.ACCESS_TOKEN_VALIDITY ) );
				accessToken.setLastUpdatedDate( new Date() );
				accessToken = dataAccessor.updateAccessToken( accessToken );
			}
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
