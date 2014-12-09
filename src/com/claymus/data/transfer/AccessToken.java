package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface AccessToken extends Serializable {

	String getUuid();
	
	void setUuid( String uuid );
	
	Date getExpiry();
	
	void setExpiry( Date expiry);
	
	String getValues();
	
	void setValues( String value );
	
}
