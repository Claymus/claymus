package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.commons.shared.AccessTokenType;
import com.claymus.data.transfer.AccessToken;

@SuppressWarnings("serial")
@PersistenceCapable( table = "ACCESS_TOKEN" )
public class AccessTokenEntity implements AccessToken {

	@PrimaryKey
	@Persistent( column = "ACCESS_TOKEN_ID" )
	private String accessTokenId;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;

	@Persistent( column = "EXPIRY" )
	private Date expiry;

	@Persistent( column = "USER_ID" )
	private Long userId;

	@Persistent( column = "PUBLISHER_ID" )
	private Long publisherId;

	@Persistent( column = "ACCESS_TOKEN_TYPE" )
	private AccessTokenType type;

	
	@Override
	public String getId() {
		return accessTokenId;
	}

	@Override
	public void setId( String accessTokenId ) {
		this.accessTokenId = accessTokenId;
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
	public AccessTokenType getType() {
		return type;
	}

	@Override
	public void setType( AccessTokenType accessTokenType ) {
		this.type = accessTokenType;
	}

}
