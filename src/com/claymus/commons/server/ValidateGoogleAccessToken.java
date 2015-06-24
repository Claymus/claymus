package com.claymus.commons.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

public class ValidateGoogleAccessToken {
	
	private HttpTransport transport;
	private JsonFactory jsonFactory;
	
	private String CLIENT_ID = "587069646345-6kn5hlcvaso2q0u76kup6aske5na2mjg.apps.googleusercontent.com";
	private String APPS_DOMAIN_NAME = "mobi.pratilipi.com";
	
	public boolean isValid( String idTokenString ) throws GeneralSecurityException, IOException{
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder( transport, jsonFactory )
												    .setAudience(Arrays.asList( CLIENT_ID ))
												    .build();
		
		GoogleIdToken idToken = verifier.verify( idTokenString );
		if ( idToken != null ) {
		  Payload payload = idToken.getPayload();
		  if ( payload.getHostedDomain().equals( APPS_DOMAIN_NAME ) ) {
			  System.out.println("User ID: " + payload.getSubject());
			  return true;
		  } else {
			  Logger.getLogger( ValidateGoogleAccessToken.class.getName() ).log( Level.INFO, "Invalid Domain Name In Access Token." );
		  }
		} else {
			Logger.getLogger( ValidateGoogleAccessToken.class.getName() ).log( Level.INFO, "Invalid Id Token" );
		}
		
		
		return false;
	}

}
