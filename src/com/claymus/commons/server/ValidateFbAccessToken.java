package com.claymus.commons.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ValidateFbAccessToken {
	
	private String inputToken;
	private boolean isValid;
	
	private static final Logger logger = 
			Logger.getLogger( ValidateFbAccessToken.class.getName() );
	
	public ValidateFbAccessToken( String inputToken ) {
		this.inputToken = inputToken;
	}
	
	
	public boolean isValid() throws IOException, JSONException {
		URL url;
		try {
			String urlParameters = "input_token=" + URLEncoder.encode( inputToken, "UTF-8" )
					+ "&client_id=" + URLEncoder.encode( "293990794105516", "UTF-8" )
					+ "&redirect_url=" + URLEncoder.encode( "http://mark-2d3.devo-pratilipi.appspot.com", "UTF-8" )
					+ "&access_token=" + URLEncoder.encode( "293990794105516|XXXXXXXXXXXXXXXXXXXXXXXXXXXXX", "UTF-8" );
					
			String validateURL = "https://graph.facebook.com/debug_token?" + urlParameters;
			
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
			logger.log( Level.SEVERE, appAccessTokenBuffer.toString());
			JSONObject accessTokenJSON = new JSONObject( appAccessTokenBuffer.toString() );
			isValid = accessTokenJSON.getString( "data" ).contains("true");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.log( Level.SEVERE, "", e);
		}
		
		return isValid;
	}
	
}
