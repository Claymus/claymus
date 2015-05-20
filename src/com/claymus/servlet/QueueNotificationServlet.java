package com.claymus.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.User;
import com.claymus.email.EmailUtil;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;

import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class QueueNotificationServlet extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( QueueWelcomeUserServlet.class.getName() );

	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Long userId = Long.parseLong( request.getParameter( "userId" ) );
		User user = dataAccessor.getUser( userId );
		String recipientId = request.getParameter( "recipientId" );
		User recipient = dataAccessor.getUser( Long.parseLong( recipientId ) );
		String pratilipiId = request.getParameter( "pratilipiId" );
		Pratilipi pratilipi = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( request )
									.getPratilipi( Long.parseLong( pratilipiId ));
		PratilipiData pratilipiData = PratilipiContentHelper.createPratilipiData( pratilipi, null, null, request );
		String notificationType = request.getParameter( "notificationType" );
		
		// Creating Email Template
		// TODO: migrate it to DataStore
		String subject = "Pratilipi.com - Notification";
		
		File file = new File( "WEB-INF/classes/com/pratilipi/servlet/content/NotificationEmailContent.ftl" );
		List<String> lines;
		lines = FileUtils.readLines( file, "UTF-8" );
		String body = "";
		for( String line : lines )
			body = body + line;

		EmailTemplate notificationEmailTemplate = dataAccessor.newEmailTemplate();
		notificationEmailTemplate.setSenderName( "Team Pratilipi" );
		notificationEmailTemplate.setSenderEmail( "contact@pratilipi.com" );
		notificationEmailTemplate.setReplyToName( "Team Pratilipi" );
		notificationEmailTemplate.setReplyToEmail( "no-reply@pratilipi.com" );
		notificationEmailTemplate.setSubject( subject );
		notificationEmailTemplate.setBody( body );
		
		// Sending email to the user
		try {
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put( "user", user );
			dataModel.put( "recipient", recipient );
			dataModel.put( "pratilipiData", pratilipiData );
			dataModel.put( "notificationType", notificationType );
			
			logger.log( Level.INFO, "Recipient Email : " + recipient.getEmail() );
			
			if( recipient != null && recipient.getEmail() != null ){
				logger.log( Level.INFO, "Sending email..." );
				EmailUtil.sendMail(
						EmailUtil.createUserName( recipient ), recipient.getEmail(),
						notificationEmailTemplate, dataModel );
			}
			
		} catch( MessagingException | TemplateException e ) {
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			logger.log( Level.SEVERE, "Failed to send the email !", e );
		}
		
	}
}
