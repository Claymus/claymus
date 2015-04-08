package com.claymus.commons.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;

public class GoogleApi {

	private static final Logger logger =
			Logger.getLogger( GoogleApi.class.getName() );
	
	
	private static HttpRequestInitializer getCredential( Collection<String> scopes ) {
		return new AppIdentityCredential( scopes ); // Works only on Google AppEngine
	}

	public static Analytics getAnalytics( Collection<String> scopes )
			throws UnexpectedServerException {
		
		try {
			return new Analytics.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(),
					getCredential( scopes ) )
				.setApplicationName( "Claymus" )
				.build();
		} catch( GeneralSecurityException | IOException e ) {
			logger.log( Level.SEVERE, "Failed to create Analytics Service Object.", e );
			throw new UnexpectedServerException();
		}
	}
	
}
