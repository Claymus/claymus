package com.claymus.commons.server;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class FacebookApi {

	private static final Gson gson = new GsonBuilder().create();
	
	private static final String APP_PROPERTY_ID = "Facebook.Credentials";
	
	private static final String GRAPH_API_URL = "https://graph.facebook.com/v2.2";
	
	
	public static String getAccessToken( HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Map<String, String> facebookCredentials = dataAccessor.getAppProperty( APP_PROPERTY_ID ).getValue();
		return facebookCredentials.get( "appId" ) + "|" + facebookCredentials.get( "appSecret" );
	}
	
	public static long getUrlShareCount( String url, HttpServletRequest request ) throws UnexpectedServerException {
		try {
			String requestUrl = GRAPH_API_URL
					+ "?id=" + URLEncoder.encode( url, "UTF-8" )
					+ "&access_token=" + getAccessToken( request );

			String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
			JsonElement responseJson = gson.fromJson( responsePayload, JsonElement.class );
			
			JsonElement shareJson = responseJson.getAsJsonObject().get( "share" );
			if( shareJson == null )
				return 0L;
			
			JsonElement shareCountJson = shareJson.getAsJsonObject().get( "share_count" );
			return shareCountJson != null ? shareCountJson.getAsLong() : 0L; 
		} catch( IOException e ) {
			throw new UnexpectedServerException();
		}
	}
	
}
