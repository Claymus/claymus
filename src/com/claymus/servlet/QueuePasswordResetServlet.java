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

import com.claymus.commons.server.EncryptPassword;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.User;
import com.claymus.email.EmailUtil;

import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class QueuePasswordResetServlet extends HttpServlet {
	
	private static final Logger logger = 
			Logger.getLogger( QueuePasswordResetServlet.class.getName() );


	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Long userId = Long.parseLong( request.getParameter( "userId" ) );

		// New password
		String newPassword = generatePassword();
		
		// Updating user password in the DataStore
		User user = dataAccessor.getUser( userId );
		user.setPassword( EncryptPassword.getSaltedHash( newPassword ) );
		dataAccessor.createOrUpdateUser( user );

		// Creating Email Template
		// TODO: migrate it to DataStore
		File file = new File( "WEB-INF/classes/com/pratilipi/servlet/content/PasswordResetEmailContent.ftl" );
		List<String> lines;
		lines = FileUtils.readLines( file, "UTF-8" );
		String body = "";
		for( String line : lines )
			body = body + line;

		EmailTemplate passwordResetEmailTemplate = dataAccessor.newEmailTemplate();
		passwordResetEmailTemplate.setSenderName( "Pratilipi" );
		passwordResetEmailTemplate.setSenderEmail( "no-reply@pratilipi.com" );
		passwordResetEmailTemplate.setReplyToName( "Pratilipi Team" );
		passwordResetEmailTemplate.setReplyToEmail( "contact+password-reset@pratilipi.com" );
		passwordResetEmailTemplate.setSubject( "Forgot Password: Pratilipi.com" );
		passwordResetEmailTemplate.setBody( body );
		
		// Sending email to the user
		try {
			
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put( "user", user );
			dataModel.put( "newPassword", newPassword );

			EmailUtil.sendMail(
					EmailUtil.createUserName( user ), user.getEmail(),
					passwordResetEmailTemplate, dataModel );
			
		} catch( MessagingException | TemplateException e ) {
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			logger.log( Level.SEVERE, "Failed to send the email !", e );
		}
		
	}
	
	private String generatePassword() {
		char[] charRange = new char[ 10 + 26 + 26];
		
		int i = 0;
		for( char ch = '0'; ch <= '9'; ch++ )
			charRange[ i++ ] = ch;
		for( char ch = 'a'; ch <= 'z'; ch++ )
			charRange[ i++ ] = ch;
		for( char ch = 'A'; ch <= 'Z'; ch++ )
			charRange[ i++ ] = ch;
		
		char[] password = new char[ 16 ];
		for( int j = 0; j < 16; j++ )
			password[ j ] = charRange[ (int)( Math.random() * charRange.length ) ];

		return new String( password );
	}
	
}
