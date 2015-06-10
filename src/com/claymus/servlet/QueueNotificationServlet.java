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

import com.claymus.commons.shared.exception.InvalidArgumentException;
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
		String recipientId = request.getParameter( "recipientId" );
		String pratilipiId = request.getParameter( "pratilipiId" );
		String notificationType = request.getParameter( "notificationType" );
		
		logger.log( Level.INFO, "UserId : " + request.getParameter( "userId" )
							+ "; Recipient Id : " + recipientId 
							+ "; Pratilipi Id : " + pratilipiId 
							+ "; Notification Type : " + notificationType );

		try {
			if( userId == null || userId == 0L )
				throw new InvalidArgumentException( "UserId is null" );
			
			if( recipientId == null ){
				logger.log( Level.INFO, "Recipient Id is null" );
				throw new InvalidArgumentException( "Recipient Id is null" );
			}
			
			if( pratilipiId == null )
				throw new InvalidArgumentException( "Pratilipi Id is null" );
			
			if( notificationType == null )
				throw new InvalidArgumentException( "Notification Type is null" );
			
			User user = dataAccessor.getUser( userId );
			User recipient = dataAccessor.getUser( Long.parseLong( recipientId ) );
			Pratilipi pratilipi = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( request )
					.getPratilipi( Long.parseLong( pratilipiId ));
			PratilipiData pratilipiData = PratilipiContentHelper.createPratilipiData( pratilipi, null, null, request );
			
			if( user == null )
				throw new InvalidArgumentException( "Could not find user. Invalid userId!" );
			
			if( recipient == null )
				throw new InvalidArgumentException( "Could not find recipient. Invalid recipientId!" );
			
			if( pratilipi == null )
				throw new InvalidArgumentException( "Pratilipi is null. Invalid pratilipiId!" );
			
			// Creating Email Template
			// TODO: migrate it to DataStore
			String subject = "Pratilipi.com - Notification";
			File file;
			if( pratilipiData.getLanguageId().equals( 6319546696728576L )){
				file = new File( "WEB-INF/classes/com/pratilipi/servlet/content/NotificationEmailContentTamil.ftl" );
				logger.log( Level.INFO, "Template Name : " + file.getName() );
			}
			else
				file = new File( "WEB-INF/classes/com/pratilipi/servlet/content/NotificationEmailContent.ftl" );
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
			
			//Sending email
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put( "user", user );
			dataModel.put( "recipient", recipient );
			dataModel.put( "pratilipiData", pratilipiData );
			dataModel.put( "notificationType", notificationType );
			
			logger.log( Level.INFO, "Recipient Email : " + recipient.getEmail() );
			logger.log( Level.INFO, "Pratilipi Type : " + pratilipiData.getType() );
			
			if( recipient != null && recipient.getEmail() != null ){
				logger.log( Level.INFO, "Sending email..." );
				EmailUtil.sendMail(
						EmailUtil.createUserName( recipient ), recipient.getEmail(),
						notificationEmailTemplate, dataModel );
			}
		} catch ( MessagingException | TemplateException | InvalidArgumentException e1) {
			e1.printStackTrace();
		}
		
		
	}
}
