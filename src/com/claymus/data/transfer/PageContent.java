package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface PageContent extends Serializable {

	Long getId();

	Long getPageId();
	
	void setPageId( Long pageId );

	String getPosition();

	void setPosition( String position );

	Date getCreationDate();

	void setCreationDate( Date creationDate );

	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );

}
