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
	@Persistent( column = "UUID" )
	private String uuid;
	
	@Persistent( column = "EXPIRY" )
	private Date expiry;

	@Persistent( column = "VALUES" )
	private String values;

	@Override
	public String getUuid() {
		return uuid;
	}

	@Override
	public void setUuid( String uuid ) {
		this.uuid = uuid;
	}

	@Override
	public Date getExpiry() {
		return this.expiry;
	}

	@Override
	public void setExpiry( Date expiry) {
		this.expiry = expiry;
	}

	@Override
	public String getValues() {
		return this.values;
	}

	@Override
	public void setValues( String values ) {
		this.values = values;
	}
}
