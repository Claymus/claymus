package com.claymus.commons.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class GoogleUtil {

	public static String APP_PROPERTY_ID = "Google.Credentials";
	
	public static String GOOGLE_ERROR = "error";
	public static String GOOGLE_ACCESSTOKEN = "access_token";
	public static String GOOGLE_REFRESHTOKEN = "refresh_token";
	public static String GOOGLE_IDTOKEN = "id_token";
	public static String GOOGLE_USER_USERID = "user_id";
	public static String GOOGLE_USER_EMAIL = "user_email";
	public static String GOOGLE_USER_DISPLAYNAME = "displayName";
	public static String GOOGLE_USER_GENDER = "gender";
	public static String GOOGLE_USER_BIRTHDAY = "birthday";
	public static String GOOGLE_USER_IMAGE = "image";
	public static String GOOGLE_USER_IMAGE_URL = "url";
	
	private String clientId;
	private String clientSecret;
	private String userId;
	private String userEmail;
	
//	private String APPS_DOMAIN_NAME = "www.pratilipi.com";
	private final Logger logger = Logger.getLogger( GoogleUtil.class.getName() );

	private String getTokensUrl = "https://www.googleapis.com/oauth2/v3/token";
	private String peopleApiUrl = "https://www.googleapis.com/plus/v1/people/";
	
	public GoogleUtil( Map<String, String> googleCredentials ){
		this.clientId = googleCredentials.get( "appId" );
		this.clientSecret = googleCredentials.get( "appSecret" );
	}
	
	
	public boolean isValid( String idTokenString ) 
			throws GeneralSecurityException, IOException{
		
		NetHttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder( transport, jsonFactory )
												    .setAudience( Arrays.asList( clientId ) )
												    .build();
		
		GoogleIdToken idToken = verifier.verify( idTokenString );
		if ( idToken != null ) {
		  Payload payload = idToken.getPayload();
		  userId = payload.getSubject();
		  userEmail = payload.getEmail();
		  return true;
		} else {
			logger.log( Level.INFO, "Invalid Id Token" );
		}
		
		
		return false;
	}
	
	public Map<String, String> getTokens( String idToken ) 
			throws IOException, JSONException, GeneralSecurityException{
		URL url;
		try {
			String urlParameters = URLEncoder.encode( "code", "UTF-8" ) + "=" + URLEncoder.encode( idToken, "UTF-8" )
					+ "&" + URLEncoder.encode( "client_id", "UTF-8" ) + "=" + URLEncoder.encode( clientId, "UTF-8" )
                    + "&" + URLEncoder.encode( "client_secret", "UTF-8" ) + "=" + URLEncoder.encode( clientSecret, "UTF-8" )
                    + "&" + URLEncoder.encode( "redirect_uri", "UTF-8" ) + "=" + URLEncoder.encode( "http://www.pratilipi.com/", "UTF-8" )
                    + "&" + URLEncoder.encode( "grant_type", "UTF-8" ) + "=" + URLEncoder.encode( "authorization_code", "UTF-8" );
					
			//Create Connection
			url = new URL( getTokensUrl );
			HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
			connection.setDoOutput( true );
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			connection.setRequestProperty( "Content-Length", Integer.toString( urlParameters.getBytes().length ) );
			
			 //Send request
		    DataOutputStream wr = new DataOutputStream ( connection.getOutputStream() );
		    wr.write( urlParameters.getBytes("UTF-8") );
		    wr.close();
			
		    //Get Response
			BufferedReader inputBuffer = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
			String inputLine;
			StringBuffer appAccessTokenBuffer = new StringBuffer();
			while ( ( inputLine = inputBuffer.readLine() ) != null )
				appAccessTokenBuffer.append(inputLine + "\n");
			inputBuffer.close();
			JSONObject accessTokenJSON = new JSONObject( appAccessTokenBuffer.toString() );

			Map<String, String> returnMap = new HashMap<>();
			
			if( accessTokenJSON.has( "error" )){
				logger.log( Level.SEVERE, "Error returned by google token end point : " + accessTokenJSON.getString( "error_description" ));
				return null;
			}
			
			if( isValid( accessTokenJSON.getString( GOOGLE_IDTOKEN ))){
				if( accessTokenJSON.has( GOOGLE_ACCESSTOKEN ))
					returnMap.put( GOOGLE_ACCESSTOKEN, accessTokenJSON.getString( GOOGLE_ACCESSTOKEN ) );
				if( accessTokenJSON.has( GOOGLE_REFRESHTOKEN ))
					returnMap.put( GOOGLE_REFRESHTOKEN, accessTokenJSON.getString( GOOGLE_REFRESHTOKEN ) );
				returnMap.put( GOOGLE_USER_USERID, userId );
				returnMap.put( GOOGLE_USER_EMAIL,  userEmail );
				return returnMap;
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.log( Level.SEVERE, "", e);
		}
		
		return null;
	}
	
	public Map<String, String> getUserProfile( String accessToken, String userId ) 
			throws IOException, JSONException {
		URL url;
		try {
			String urlParameters = "access_token=" + URLEncoder.encode( accessToken, "UTF-8" );
					
			String validateURL = peopleApiUrl + userId + "?" + urlParameters;
			
			//Create Connection
			url = new URL( validateURL );
			URLConnection connection = url.openConnection();
			BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			StringBuffer appAccessTokenBuffer = new StringBuffer();
			while ((inputLine = inputBuffer.readLine()) != null)
				appAccessTokenBuffer.append(inputLine + "\n");
			inputBuffer.close();
			logger.log( Level.INFO, appAccessTokenBuffer.toString());
			JSONObject accessTokenJSON = new JSONObject( appAccessTokenBuffer.toString() );
			Map<String, String> returnMap = new HashMap<>();
			if( accessTokenJSON.has( GOOGLE_USER_DISPLAYNAME ))
				returnMap.put( GOOGLE_USER_DISPLAYNAME, accessTokenJSON.getString( GOOGLE_USER_DISPLAYNAME ));
			if( accessTokenJSON.has( GOOGLE_USER_GENDER ))
				returnMap.put( GOOGLE_USER_GENDER, accessTokenJSON.getString( GOOGLE_USER_GENDER ));
			if( accessTokenJSON.has( GOOGLE_USER_BIRTHDAY ))
				returnMap.put( GOOGLE_USER_BIRTHDAY, accessTokenJSON.getString( GOOGLE_USER_BIRTHDAY ));
			
			if( accessTokenJSON.has( GOOGLE_USER_IMAGE )){
				JSONObject imageObject = accessTokenJSON.getJSONObject( GOOGLE_USER_IMAGE );
				if( imageObject.has( GOOGLE_USER_IMAGE_URL ) )
					returnMap.put( GOOGLE_USER_IMAGE_URL, imageObject.getString( GOOGLE_USER_IMAGE_URL ));
			}
			return returnMap;
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.log( Level.SEVERE, "", e);
		}
		
		return null;
	}
	
	public String getNewAccessToken( String refreshToken ) 
			throws IOException, JSONException, GeneralSecurityException{
		
		URL url;
		try {
			String urlParameters = URLEncoder.encode( "code", "UTF-8" ) + "=" + URLEncoder.encode( refreshToken, "UTF-8" )
					+ "&" + URLEncoder.encode( "client_id", "UTF-8" ) + "=" + URLEncoder.encode( clientId, "UTF-8" )
                    + "&" + URLEncoder.encode( "client_secret", "UTF-8" ) + "=" + URLEncoder.encode( clientSecret, "UTF-8" )
                    + "&" + URLEncoder.encode( "grant_type", "UTF-8" ) + "=" + URLEncoder.encode( "refresh_token", "UTF-8" );
					
			//Create Connection
			url = new URL( getTokensUrl );
			HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
			connection.setDoOutput( true );
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			connection.setRequestProperty( "Content-Length", Integer.toString( urlParameters.getBytes().length ) );
			
			 //Send request
		    DataOutputStream wr = new DataOutputStream ( connection.getOutputStream() );
		    wr.write( urlParameters.getBytes("UTF-8") );
		    wr.close();
			
		    //Get Response
			BufferedReader inputBuffer = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
			String inputLine;
			StringBuffer appAccessTokenBuffer = new StringBuffer();
			while ( ( inputLine = inputBuffer.readLine() ) != null )
				appAccessTokenBuffer.append(inputLine + "\n");
			inputBuffer.close();
			JSONObject accessTokenJSON = new JSONObject( appAccessTokenBuffer.toString() );

			if( accessTokenJSON.has( "error" )){
				logger.log( Level.SEVERE, "Refresh Token is invalid : " + accessTokenJSON.getString( "error_description" ));
				return null;
			}
			
			return accessTokenJSON.getString( GOOGLE_ACCESSTOKEN );
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.log( Level.SEVERE, "", e);
		}
		
		return null;
	}
	
}
