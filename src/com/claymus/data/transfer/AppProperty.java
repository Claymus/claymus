package com.claymus.data.transfer;

import java.io.Serializable;

public interface AppProperty extends Serializable {
	
	String getId();

	<T> T getValue();

	<T> void setValue( T value );
	
}