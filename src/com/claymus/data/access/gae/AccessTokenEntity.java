package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.AccessToken;

@SuppressWarnings("serial")
@PersistenceCapable( table = "ACCESS_TOKEN" )
public class AccessTokenEntity implements AccessToken {

	@PrimaryKey
	@Persistent( column = "ACCESS_TOKEN_ID" )
	private String id;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	@Persistent( column = "EXPIRY" )
	private Date expiry;

	@Persistent( column = "USER_ID" )
	private Long userId;

	@Persistent( column = "PUBLISHER_ID" )
	private Long publisherId;

	@Persistent( column = "ACCESS_TOKEN_TYPE" )
	private String type;

	@Persistent( column = "LOGIN_DATE" )
	private Date logInDate;
	
	@Persistent( column = "LOGOUT_DATE" )
	private Date logOutDate;

	
	@Override
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}
	
	@Override
	public boolean isExpired() {
		return expiry.before( new Date() );
	}

	@Override
	public Date getExpiry() {
		return expiry;
	}

	@Override
	public void setExpiry( Date expiry ) {
		this.expiry = expiry;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public Long getPublisherId() {
		return publisherId;
	}

	@Override
	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType( String accessTokenType ) {
		this.type = accessTokenType;
	}

	@Override
	public Date getLogInDate() {
		return logInDate;
	}
	
	@Override
	public void setLogInDate( Date logInDate ) {
		this.logInDate = logInDate;
	}
	
	@Override
	public Date getLogOutDate() {
		return logOutDate;
	}
	
	@Override
	public void setLogOutDate( Date logOutDate ) {
		this.logOutDate = logOutDate;
	}
	
}
