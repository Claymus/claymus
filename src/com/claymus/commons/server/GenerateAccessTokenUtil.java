package com.claymus.commons.server;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.gson.JsonObject;

public class GenerateAccessTokenUtil {

	private static final Logger logger =
			Logger.getLogger( GenerateAccessTokenUtil.class.getName() );
	
	private String publisherId;
	private String publisherSecret;

	private int accessTokenLength = 64;
	
	protected GenerateAccessTokenUtil(){}
	
	public GenerateAccessTokenUtil( String publisherId, String publisherSecret ){
		this.publisherId = publisherId;
		this.publisherSecret = publisherSecret;
	}
	
	public JsonObject generateAccessToken() throws UnexpectedServerException{
		
		//Generating 64 bytes access token
		byte[] accessTokenInBytes = null;
		try {
			accessTokenInBytes = SecureRandom.getInstance("SHA1PRNG").generateSeed( accessTokenLength );
		} catch (NoSuchAlgorithmException e) {
			String msg = "Access Token generation failed";
			logger.log( Level.SEVERE, msg, e );
			throw new UnexpectedServerException( msg );
		}
		String accessTokenString = Base64.encodeBase64String( accessTokenInBytes );
		
		//Storing data received in http request and access token generated in a json object
		JsonObject accessTokenJson = new JsonObject();
		accessTokenJson.addProperty( "publisherId", publisherId );
		accessTokenJson.addProperty( "publisherSecret", publisherSecret );
		accessTokenJson.addProperty( "accessToken", accessTokenString );
		
		return accessTokenJson;
	}
}
