package com.claymus.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.api.ApiRegistry;
import com.claymus.api.GenericApi;
import com.claymus.api.PageContentListApi;
import com.claymus.pagecontent.audit.api.AuditLogListApi;
import com.claymus.pagecontent.comments.api.CommentsApi;
import com.claymus.pagecontent.pages.api.PageApi;
import com.claymus.pagecontent.pages.api.PageListApi;


@SuppressWarnings("serial")
public class ClaymusService extends HttpServlet {
	
	static {
		ApiRegistry.register( PageContentListApi.class );

		ApiRegistry.register( PageApi.class );
		ApiRegistry.register( PageListApi.class );
		ApiRegistry.register( AuditLogListApi.class );
		
		ApiRegistry.register( CommentsApi.class );
	}
	
	@Override
	protected final void service(
			HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {

		String requestUri = request.getRequestURI();
				
		ServletConfig servletConfig = getServletConfig();
		String prefix = servletConfig.getInitParameter( "Prefix" );
		if( prefix != null && !prefix.isEmpty() )
			requestUri = requestUri.substring( prefix.length() );

		GenericApi api = ApiRegistry.getApi( requestUri );
		
		if( api == null )
			super.service( request, response );
		else
			api.service( request, response );
	}

}
