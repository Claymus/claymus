package com.claymus.commons.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class FacebookUtil {
	
	public static String APP_PROPERTY_ID = "Facebook.Credentials";
	
	public static String FACEBOOK_APP_ID = "app_id";
	public static String FACEBOOK_ERROR = "error";
	public static String FACEBOOK_ACCESSTOKEN_ISVALID = "is_valid";
	public static String FACEBOOK_USER_ID = "id";
	public static String FACEBOOK_USER_EMAIL = "email";
	public static String FACEBOOK_USER_NAME = "name";
	public static String FACEBOOK_USER_GENDER = "gender";
	public static String FACEBOOK_USER_BIRTHDAY = "birthday";
	public static String FACEBOOK_USER_PROFILE_PIC = "picture";
	public static String FACEBOOK_USER_PROFILE_PIC_URL = "url";
	
	private String appId;
	private String appSecret;
	
	private String requiredProfileFields = "id,email,name,gender, picture";
	
	private final Logger logger = Logger.getLogger( GoogleUtil.class.getName() );

	private String validateTokensEndpoint = "https://graph.facebook.com/debug_token?";
	private String profileEndpoint = "https://graph.facebook.com/me?";
	
	public FacebookUtil( Map<String, String> facebookCredentials ){
		this.appId = facebookCredentials.get( "appId" );
		this.appSecret = facebookCredentials.get( "appSecret" );
	}
	
	public boolean validateAccessToken( String socialId, String accessToken ) 
			throws JSONException, IOException{
		URL url;
		try {
			String appAccessToken = appId + "|" + appSecret;
			String urlParameters = URLEncoder.encode( "input_token", "UTF-8" ) + "=" + URLEncoder.encode( accessToken, "UTF-8" )
					+ "&" + URLEncoder.encode( "access_token", "UTF-8" ) + "=" + URLEncoder.encode( appAccessToken, "UTF-8" );
					
			//Create Connection
			String validateTokenUrl = validateTokensEndpoint + urlParameters;
			url = new URL( validateTokenUrl );
			URLConnection connection = url.openConnection();
			
		    //Get Response
			BufferedReader inputBuffer = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
			String inputLine;
			StringBuffer appAccessTokenBuffer = new StringBuffer();
			while ( ( inputLine = inputBuffer.readLine() ) != null )
				appAccessTokenBuffer.append(inputLine + "\n");
			inputBuffer.close();
			logger.log( Level.INFO, "Response : " + appAccessTokenBuffer.toString() );
			JSONObject accessTokenJSON = new JSONObject( appAccessTokenBuffer.toString() );
			JSONObject dataJSON = accessTokenJSON.getJSONObject( "data" );

			if( dataJSON.has( "error" )){
				logger.log( Level.SEVERE, "Error returned by google token end point : " + dataJSON.getString( "error_description" ));
				return false;
			}
			
			if( dataJSON.has( FACEBOOK_ACCESSTOKEN_ISVALID ) && 
					dataJSON.getBoolean( FACEBOOK_ACCESSTOKEN_ISVALID )){
				if( dataJSON.getString(FACEBOOK_APP_ID ).equals( appId ) 
						&& dataJSON.getString( "user_id" ).equals( socialId ))
					return true;
				else{
					logger.log( Level.INFO, "App Id / Received App Id : " + appId + " / " + dataJSON.getString( FACEBOOK_APP_ID ));
					logger.log( Level.INFO, "Social Id / Received Social Id : " + socialId + " / " + dataJSON.getString( FACEBOOK_USER_ID ));
					return false;
				}
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.log( Level.SEVERE, "", e);
		}
		
		return false;
	}
	
	public Map<String, String> getUserProfile( String socialId, String accessToken ) 
			throws JSONException, IOException, InvalidArgumentException{
		
		URL url;
		try {
			String urlParameters = URLEncoder.encode( "access_token", "UTF-8" ) + "=" + URLEncoder.encode( accessToken, "UTF-8" )
					+ "&" + URLEncoder.encode( "fields", "UTF-8" ) + "=" + URLEncoder.encode( requiredProfileFields, "UTF-8" );
					
			//Create Connection
			String profileUrl = profileEndpoint + urlParameters;
			url = new URL( profileUrl );
			URLConnection connection = url.openConnection();
			
		    //Get Response
			BufferedReader inputBuffer = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
			String inputLine;
			StringBuffer userProfileBuffer = new StringBuffer();
			while ( ( inputLine = inputBuffer.readLine() ) != null )
				userProfileBuffer.append(inputLine + "\n");
			inputBuffer.close();
			JSONObject userProfileJSON = new JSONObject( userProfileBuffer.toString() );
			logger.log( Level.INFO, "Response : " + userProfileBuffer.toString() );

			if( userProfileJSON.has( "error" )){
				logger.log( Level.SEVERE, "Error returned by google token end point : " + userProfileJSON.getString( "error_description" ));
				return null;
			}
			
			Map<String, String> returnMap = new HashMap<>();
			
			String id = userProfileJSON.has( FACEBOOK_USER_ID ) ? userProfileJSON.getString( FACEBOOK_USER_ID ) : null;
			returnMap.put( FACEBOOK_USER_ID, id );
			String name = userProfileJSON.has( FACEBOOK_USER_NAME ) ? userProfileJSON.getString( FACEBOOK_USER_NAME ) : null;
			returnMap.put( FACEBOOK_USER_NAME, name );
			String email = userProfileJSON.has( FACEBOOK_USER_EMAIL ) ? userProfileJSON.getString( FACEBOOK_USER_EMAIL ) : null;
			returnMap.put( FACEBOOK_USER_EMAIL, URLDecoder.decode( email, "UTF-8") );
			String gender = userProfileJSON.has( FACEBOOK_USER_GENDER ) ? userProfileJSON.getString( FACEBOOK_USER_GENDER ) : null;
			returnMap.put( FACEBOOK_USER_GENDER, gender );
			String birthday = userProfileJSON.has( FACEBOOK_USER_BIRTHDAY ) ? userProfileJSON.getString( FACEBOOK_USER_BIRTHDAY ) : null;
			returnMap.put( FACEBOOK_USER_BIRTHDAY, birthday );
			
			JSONObject profilePictureJSON = userProfileJSON.getJSONObject( FACEBOOK_USER_PROFILE_PIC )
															.getJSONObject( "data" );
			String profilePicUrl = profilePictureJSON.getString( FACEBOOK_USER_PROFILE_PIC_URL );
			returnMap.put( FACEBOOK_USER_PROFILE_PIC_URL, profilePicUrl );
			
			return returnMap;
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.log( Level.SEVERE, "", e);
		}
		
		
		return null;
	}

}
