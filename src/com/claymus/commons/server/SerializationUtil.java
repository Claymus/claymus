package com.claymus.commons.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamReader;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamWriter;

public class SerializationUtil {

	private static final Logger logger =
			Logger.getLogger( SerializationUtil.class.getName() );

	
	public static String encode( IsSerializable object )
			throws UnexpectedServerException {
		
		ServerSerializationStreamWriter streamWriter =
				new ServerSerializationStreamWriter(
						RPC.getDefaultSerializationPolicy() );
		streamWriter.prepareToWrite();
		try {
			streamWriter.serializeValue( object, object.getClass() );
			return streamWriter.toString();
		} catch( SerializationException e ) {
			String msg = "Failed to serialize object.";
			logger.log( Level.SEVERE, msg, e );
			throw new UnexpectedServerException( msg );
		}
		
	}
	
	public static IsSerializable decode( String encodedStr )
			throws UnexpectedServerException {
		
		ServerSerializationStreamReader streamReader =
				new ServerSerializationStreamReader(
						Thread.currentThread().getContextClassLoader(), null );
		try {
			streamReader.prepareToRead( encodedStr );
			return (IsSerializable) streamReader.readObject();
		} catch( SerializationException e ) {
			String msg = "Failed to deserialize encoded string.";
			logger.log( Level.SEVERE, msg, e );
			throw new UnexpectedServerException( msg );
		}
		
	}
	
}
