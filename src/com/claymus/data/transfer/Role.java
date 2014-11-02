package com.claymus.data.transfer;

import java.io.Serializable;

public interface Role extends Serializable {

	Long getId();

	String getName();

	void setName( String name );
	
}
