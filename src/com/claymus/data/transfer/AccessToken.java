package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface AccessToken extends Serializable {

	String getId();
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
	boolean isExpired();
	
	Date getExpiry();
	
	void setExpiry( Date expiry);
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getPublisherId();
	
	void setPublisherId( Long publisherId );
	
	String getType();
	
	void setType( String accessTokenType );
	
	Date getLogInDate();
	
	void setLogInDate( Date logInDate );
	
	Date getLogOutDate();
	
	void setLogOutDate( Date logOutDate );
	
	Date getLastUpdatedDate();
	
	void setLastUpdatedDate( Date lastUpdatedDate );
	
}
