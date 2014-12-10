package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface AccessToken extends Serializable {

	String getId();
	
	void setId( String accessTokenId );
	
	Date getExpiry();
	
	void setExpiry( Date expiry);
	
	String getValues();
	
	void setValues( String value );
	
}
