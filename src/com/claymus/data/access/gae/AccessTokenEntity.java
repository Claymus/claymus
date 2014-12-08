package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.AccessToken;

@SuppressWarnings("serial")
@PersistenceCapable( table = "ACCESSTOKEN" )
public class AccessTokenEntity implements AccessToken {

	@PrimaryKey
	@Persistent( column = "UUID" )
	private String uuid;
	
	@Persistent( column = "EXPIRESAFTER" )
	private Date expiresAfter;

	@Persistent( column = "VALUE" )
	private String value;

	@Override
	public String getUUID() {
		return uuid;
	}

	@Override
	public void setUUID( String uuid ) {
		this.uuid = uuid;
	}

	@Override
	public Date getExpires() {
		return this.expiresAfter;
	}

	@Override
	public void setExpires( Date expiresAfter) {
		this.expiresAfter = expiresAfter;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue( String value ) {
		this.value = value;
	}
}
