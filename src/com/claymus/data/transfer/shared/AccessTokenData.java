package com.claymus.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

import com.claymus.service.shared.data.UserData;

@SuppressWarnings("serial")
public class AccessTokenData implements Serializable {

	private String accessTokenId;
	
	private Date creationDate;

	private Date expiry;

	private Long userId;

	private UserData user;
	
	private String type;

	private Date logInDate;
	
	private Date logOutDate;

	
	public String getId() {
		return accessTokenId;
	}

	public void setId( String accessTokenId ) {
		this.accessTokenId = accessTokenId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry( Date expiry ) {
		this.expiry = expiry;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser( UserData user ) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType( String accessTokenType ) {
		this.type = accessTokenType;
	}

	public Date getLogInDate() {
		return logInDate;
	}
	
	public void setLogInDate( Date logInDate ) {
		this.logInDate = logInDate;
	}
	
	public Date getLogOutDate() {
		return logOutDate;
	}
	
	public void setLogOutDate( Date logOutDate ) {
		this.logOutDate = logOutDate;
	}
	
}
