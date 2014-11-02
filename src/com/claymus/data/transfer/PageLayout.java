package com.claymus.data.transfer;

import java.io.Serializable;

public interface PageLayout extends Serializable {
	
	Long getId();
	
	String getName();
	
	void setName( String name );
	
	String getTemplate();

	void setTemplate( String template );
	
}
