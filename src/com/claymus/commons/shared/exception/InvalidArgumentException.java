package com.claymus.commons.shared.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class InvalidArgumentException extends Exception implements IsSerializable {

	@SuppressWarnings("unused")
	private InvalidArgumentException() {}
	
	public InvalidArgumentException( String msg ) {
		super( msg );
	}

}
