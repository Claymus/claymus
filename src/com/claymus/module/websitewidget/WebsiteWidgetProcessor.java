package com.claymus.module.websitewidget;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.servlet.ClaymusMain;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class WebsiteWidgetProcessor<T extends WebsiteWidget> {

	private static final Logger logger =
			Logger.getLogger( WebsiteWidgetProcessor.class.getName() );
	
	public String getHtml( T websiteWidget ) {
		
		Writer writer = new StringWriter();

		try {
			Template template
					= ClaymusMain
							.FREEMARKER_CONFIGURATION
							.getTemplate( getTemplateName() );
			template.process( websiteWidget, writer );
		} catch ( IOException | TemplateException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
		}
		
		return writer.toString();
	}
	
	protected String getTemplateName() {
		return null;
	}
		
}
