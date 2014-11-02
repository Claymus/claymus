package com.claymus.data.transfer;

import java.io.Serializable;

public interface WebsiteLayout extends Serializable {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getTemplate();

	void setTemplate( String template );

}
