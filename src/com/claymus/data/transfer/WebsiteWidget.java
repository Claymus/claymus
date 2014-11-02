package com.claymus.data.transfer;

import java.io.Serializable;

public interface WebsiteWidget extends Serializable {

	Long getId();

	String getPosition();

	void setPosition( String position );

}
