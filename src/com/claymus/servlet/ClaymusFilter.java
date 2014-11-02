package com.claymus.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.Page;

public class ClaymusFilter implements Filter {
	
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

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page;

		
		if( requestUri.startsWith( "/service." )
				|| requestUri.startsWith( "/resource." )
				|| requestUri.startsWith( "/_ah/queue/" ) ) {
			
			chain.doFilter( request, response );
		
			
		} else if( requestUri.length() > 1 && requestUri.endsWith( "/" ) ) { // Removing trailing "/"

			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri.substring( 0, requestUri.length() -1 ) );

			
		} else if( ( page = dataAccessor.getPage( requestUri ) ) != null
				&& page.getUriAlias() != null
				&& ! requestUri.equals( page.getUriAlias() ) ) {
			
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", page.getUriAlias() );
			
			
		} else {
			chain.doFilter( request, response );
		}

		
		dataAccessor.destroy();
	}

}
