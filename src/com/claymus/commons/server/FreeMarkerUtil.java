package com.claymus.commons.server;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.commons.shared.exception.UnexpectedServerException;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class FreeMarkerUtil {

	private static final Logger logger = 
			Logger.getLogger( FreeMarkerUtil.class.getName() );

	
	private static Configuration cfg;
	
	private static Configuration getConfiguration()
			throws UnexpectedServerException {
		
		if( cfg == null ) {
			cfg = new Configuration();
			try {
				cfg.setDirectoryForTemplateLoading( new File( System.getProperty("user.dir") + "/WEB-INF/classes/" ) );
			} catch ( IOException e ) {
				logger.log( Level.SEVERE, "Failed to set template directory.", e );
				throw new UnexpectedServerException();
			}
			cfg.setObjectWrapper( new DefaultObjectWrapper() );
			cfg.setDefaultEncoding( "UTF-8" );
			cfg.setTemplateExceptionHandler( TemplateExceptionHandler.RETHROW_HANDLER );
			cfg.setIncompatibleImprovements( new Version(2, 3, 20) ); // FreeMarker 2.3.20
		}
		return cfg;
	}
	
	public static String processTemplate( Object dataModel, String templateName )
			throws UnexpectedServerException {
		
		Writer writer = new StringWriter();
		processTemplate( dataModel, templateName, writer );
		return writer.toString();
	}

	public static void processTemplate( Object dataModel, String templateName, Writer writer )
			throws UnexpectedServerException {
		
		try {
			getConfiguration().getTemplate( templateName ).process( dataModel, writer );
		} catch ( TemplateException | IOException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
			throw new UnexpectedServerException();
		}
	}

}
