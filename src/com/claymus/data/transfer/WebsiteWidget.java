package com.claymus.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface WebsiteWidget extends Serializable {

	Long getId();

	String getHome();
	
	void setHome( String home );
	
	String[] getPageTypeList();
	
	boolean isPageTypeListInclusive();
	
	void setPageTypeList( String[] pageTypeList );
	
	void setPageTypeList( String[] pageTypeList, boolean inclusive );

	String getPosition();

	void setPosition( String position );

	Integer getOrder();

	void setOrder( Integer order );

	Date getCreationDate();

	void setCreationDate( Date creationDate );

	Date getLastUpdated();

	void setLastUpdated( Date lastUpdated );

}
