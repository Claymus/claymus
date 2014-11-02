package com.claymus.module.websitewidget.user;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.websitewidget.WebsiteWidgetProcessor;
import com.claymus.servlet.ClaymusMain;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class UserWidgetProcessor extends WebsiteWidgetProcessor<UserWidget> {

	private static final Logger logger =
			Logger.getLogger( UserWidgetProcessor.class.getName() );
	
	public String getHtml( UserWidget userWidget ) {
		
		Writer writer = new StringWriter();

		try {
			Template template
					= ClaymusMain
							.FREEMARKER_CONFIGURATION
							.getTemplate( getTemplateName() );
			template.process( ClaymusHelper.get( null ), writer );
		} catch ( IOException | TemplateException e ) {
			logger.log( Level.SEVERE, "Template processing failed.", e );
		}
		
		return writer.toString();
	}
	
	@Override
	public String getTemplateName() {
		return "com/claymus/module/websitewidget/user/UserWidget.ftl";
	}
	
}
