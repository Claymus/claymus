package com.claymus.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


@SuppressWarnings("serial")
public abstract class GenericApi extends HttpServlet {

	private static final Logger logger =
			Logger.getLogger( GenericApi.class.getName() );

	protected static final Gson gson = new GsonBuilder().create();

	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		JsonObject requestPayloadJson = createRequestPayloadJson( request );
		logger.log( Level.INFO, "Request Payload: " + requestPayloadJson.toString() );
		
		try {
			
			executeGet( requestPayloadJson, request, response );
			
		} catch( UnexpectedServerException e ) {
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			return;
		}
		
	}
	
	@Override
	public void doPut(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		JsonObject requestPayloadJson = createRequestPayloadJson( request );
		logger.log( Level.INFO, "Request Payload: " + requestPayloadJson.toString() );
		
		try {
			
			executePut( requestPayloadJson, request, response );
			
		} catch( UnexpectedServerException e ) {
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			return;
		}

	}
	
	
	private JsonObject createRequestPayloadJson(
			HttpServletRequest request ) throws IOException {
		
		String requestPayload = IOUtils.toString( request.getInputStream() );

		// Creating JsonObject from request body (JSON)
		JsonObject requestPayloadJson = requestPayload == null || requestPayload.isEmpty()
				? new JsonObject()
				: gson.fromJson( requestPayload, JsonElement.class ).getAsJsonObject();

		// Adding query string data in JsonObject		
		Enumeration<String> queryParams = request.getParameterNames();
		while( queryParams.hasMoreElements() ) {
			String param = queryParams.nextElement();
			requestPayloadJson.addProperty( param, request.getParameter( param ) );
		}
		
		return requestPayloadJson;
	}
	
	
	protected void serveJson(
			String json, HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter writer = response.getWriter();
		writer.println( json );
		writer.close();
	}
	
	protected void serveBlob(
			byte[] blob, String mimeType, HttpServletRequest request,
			HttpServletResponse response ) throws IOException {
		
		response.setContentType( mimeType );
		OutputStream out = response.getOutputStream();
		out.write( blob );
		out.close();
	}
	
	
	protected void executeGet(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {
		
		response.sendError( HttpServletResponse.SC_METHOD_NOT_ALLOWED );
	}
	
	protected void executePut(
			JsonObject requestPayloadJson,
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException, UnexpectedServerException {

		response.sendError( HttpServletResponse.SC_METHOD_NOT_ALLOWED );
	}
	
}
