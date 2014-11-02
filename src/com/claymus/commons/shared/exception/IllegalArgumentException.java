package com.claymus.commons.shared.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class IllegalArgumentException extends Exception implements IsSerializable {

	@SuppressWarnings("unused")
	private IllegalArgumentException() {}
	
	public IllegalArgumentException( String msg ) {
		super( msg );
	}

}
