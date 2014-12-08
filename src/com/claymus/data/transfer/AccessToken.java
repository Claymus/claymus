package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface AccessToken extends Serializable {

	String getUUID();
	
	void setUUID( String uuid );
	
	Date getExpires();
	
	void setExpires( Date expiresAfter);
	
	String getValue();
	
	void setValue( String value );
	
}
