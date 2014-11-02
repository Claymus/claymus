package com.claymus.email;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.claymus.data.transfer.EmailTemplate;
import com.claymus.data.transfer.User;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EmailUtil {

	private final static Properties properties = new Properties();
	private final static Session session =
			Session.getDefaultInstance( properties, null );

	
	public static void sendMail( String recepientName, String recepientEmail,
			EmailTemplate emailTemplate, Object dataModel )
					throws MessagingException, IOException, TemplateException {

		Message msg = new MimeMessage( session );
	
		msg.setFrom( new InternetAddress( emailTemplate.getSenderEmail(), emailTemplate.getSenderName() ) );
		msg.addRecipient( Message.RecipientType.TO, new InternetAddress( recepientEmail, recepientName ) );
		msg.setReplyTo( new Address[]{ new InternetAddress( emailTemplate.getReplyToEmail(), emailTemplate.getReplyToName() ) } );

		Writer writer = new StringWriter();
		Template template = new Template( null, new StringReader( emailTemplate.getSubject() ), new Configuration() );
		template.process( dataModel, writer );
		msg.setSubject( writer.toString() );
		
		writer = new StringWriter();
		template = new Template( null, new StringReader( emailTemplate.getBody() ), new Configuration() );
		template.process( dataModel, writer );
		msg.setContent( writer.toString(), "text/html" );

		Transport.send( msg );
	}
	
	public static String createUserName( User user ) {
		if( user.getFirstName() != null && user.getLastName() != null )
			return user.getFirstName() + " " + user.getLastName();
		else if( user.getFirstName() != null )
			return user.getFirstName();
		else if( user.getNickName() != null )
			return user.getNickName();
		else
			return null;
	}
	
}
