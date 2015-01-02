package com.claymus.api;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import com.claymus.api.annotation.Bind;

public final class ApiRegistry {

	private static final Logger logger = 
			Logger.getLogger( ApiRegistry.class.getName() );

	
	private static final java.util.Map<String, GenericApi> uriServletMap = new HashMap<>();

	
	public static void register( Class<? extends GenericApi> clazz ) {
		Bind bind = clazz.getAnnotation( Bind.class );
		if( bind != null ) {
			try {
				GenericApi api = clazz.newInstance();
				api.init();
				uriServletMap.put( bind.uri(), api );
			} catch( InstantiationException | IllegalAccessException | ServletException e ) {
				logger.log( Level.SEVERE, "API registration failed.", e );
			}
		}
	}
	
	public static GenericApi getApi( String uri ) {
		return uriServletMap.get( uri );
	}
	
}
