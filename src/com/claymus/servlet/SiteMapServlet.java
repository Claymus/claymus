package com.claymus.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.Page;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class SiteMapServlet extends HttpServlet {
	
	private static final Logger logger =
			Logger.getLogger( SiteMapServlet.class.getName() );

	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<Page> pageList = dataAccessor.getPageList( null, 1000 ).getDataList();
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "domain", ClaymusHelper.getSystemProperty( "domain" ) );
		dataModel.put( "pageList", pageList );

		try {
			Template template = ClaymusMain.FREEMARKER_CONFIGURATION
							.getTemplate( "com/claymus/servlet/SiteMapTemplate.ftl" );
			template.process( dataModel, response.getWriter() );
		} catch ( TemplateException | IOException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		
	}
}
