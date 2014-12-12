package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.claymus.commons.shared.AccessTokenType;

public interface AccessToken extends Serializable {

	String getId();
	
	void setId( String accessTokenId );
	
	Date getExpiry();
	
	void setExpiry( Date expiry);
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Long getPublisherId();
	
	void setPublisherId( Long publisherId );
	
	AccessTokenType getType();
	
	void setType( AccessTokenType accessTokenType );
	
}
