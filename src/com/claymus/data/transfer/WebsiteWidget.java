package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface WebsiteWidget extends Serializable {

	Long getId();

	String[] getBasicModePageTypes();
	
	boolean isBasicModePageTypeListInclusive();
	
	void setBasicModePageTypes( String[] pageTypes );
	
	void setBasicModePageTypes( String[] pageTypes, boolean include );

	String[] getStandardModePageTypes();
	
	boolean isStandardModePageTypeListInclusive();
	
	void setStandardModePageTypes( String[] pageTypes );
	
	void setStandardModePageTypes( String[] pageTypes, boolean include );

	String getPosition();

	void setPosition( String position );

	Date getCreationDate();

	void setCreationDate( Date creationDate );

	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );

}
