package com.claymus.commons.shared.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class UnexpectedServerException extends Exception implements IsSerializable {

	public UnexpectedServerException() {
		super( "Some exception occured at server. Please try again later !" );
	}

	public UnexpectedServerException( String msg ) {
		super( msg );
	}

}
