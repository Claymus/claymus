package com.claymus.data.access.gae;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.EmailTemplate;

@SuppressWarnings("serial")
@PersistenceCapable( table = "EMAIL_TEMPLATE" )
public class EmailTemplateEntity implements EmailTemplate {
	
	@PrimaryKey
	@Persistent( column = "EMAIL_TEMPLATE_ID" )
	private String id;
	
	@Persistent( column = "SENDER_NAME" )
	private String senderName;
	
	@Persistent( column = "SENDER_EMAIL" )
	private String senderEmail;
	
	@Persistent( column = "REPLY_TO_NAME" )
	private String replyToName;
	
	@Persistent( column = "REPLY_TO_EMAIL" )
	private String replyToEmail;
	
	@Persistent( column = "EMAIL_SUBJECT" )
	private String subject;

	@Persistent( column = "EMAIL_BODY" )
	private String body;

	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId( String id ) {
		this.id = id;
	}
	
	@Override
	public String getSenderName() {
		return senderName;
	}

	@Override
	public void setSenderName( String senderName ) {
		this.senderName = senderName;
	}

	@Override
	public String getSenderEmail() {
		return senderEmail;
	}

	@Override
	public void setSenderEmail( String senderEmail ) {
		this.senderEmail = senderEmail;
	}

	@Override
	public String getReplyToName() {
		return replyToName;
	}

	@Override
	public void setReplyToName( String replyToName ) {
		this.replyToName = replyToName;
	}

	@Override
	public String getReplyToEmail() {
		return replyToEmail;
	}

	@Override
	public void setReplyToEmail( String replyToEmail ) {
		this.replyToEmail = replyToEmail;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public void setSubject( String subject ) {
		this.subject = subject;
	}

	@Override
	public void setBody( String body ) {
		this.body = body;
	}

	@Override
	public String getBody() {
		return body;
	}

}
