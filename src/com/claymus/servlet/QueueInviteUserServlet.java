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

import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class QueueInviteUserServlet extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( QueueInviteUserServlet.class.getName() );

	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Long userId = Long.parseLong( request.getParameter( "userId" ) );
		User user = dataAccessor.getUser( userId );
		User referer = dataAccessor.getUser( Long.parseLong( user.getReferer() ) );
		
		// Creating Email Template
		// TODO: migrate it to DataStore
		String subject = "Invitaiton from "
				+ ( referer.getFirstName() == null
						? referer.getEmail()
						: referer.getLastName() == null
								? referer.getFirstName()
								: referer.getFirstName() + " " + referer.getLastName() )
				+ " | Pratilipi";
		
		File file = new File( "WEB-INF/classes/com/pratilipi/servlet/content/ReferralEmailContent.ftl" );
		List<String> lines;
		lines = FileUtils.readLines( file, "UTF-8" );
		String body = "";
		for( String line : lines )
			body = body + line;

		EmailTemplate passwordResetEmailTemplate = dataAccessor.newEmailTemplate();
		passwordResetEmailTemplate.setSenderName( "Ranjeet (Pratilipi)" );
		passwordResetEmailTemplate.setSenderEmail( "no-reply@pratilipi.com" );
		passwordResetEmailTemplate.setReplyToName( "Pratilipi Team" );
		passwordResetEmailTemplate.setReplyToEmail( "contact+referral@pratilipi.com" );
		passwordResetEmailTemplate.setSubject( subject );
		passwordResetEmailTemplate.setBody( body );
		
		// Sending email to the user
		try {
			
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put( "user", user );
			dataModel.put( "referer", referer );

			EmailUtil.sendMail(
					EmailUtil.createUserName( user ), user.getEmail(),
					passwordResetEmailTemplate, dataModel );
			
		} catch( MessagingException | TemplateException e ) {
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			logger.log( Level.SEVERE, "Failed to send the email !", e );
		}
		
	}
	
}
