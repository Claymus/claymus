package com.claymus.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Put;
import com.claymus.api.shared.GenericRequest;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


@SuppressWarnings("serial")
public abstract class GenericApi extends HttpServlet {

	private static final Logger logger =
			Logger.getLogger( GenericApi.class.getName() );

	protected static final Gson gson = new GsonBuilder().create();

	protected transient ThreadLocal<HttpServletRequest> perThreadRequest;
	protected transient ThreadLocal<HttpServletResponse> perThreadResponse;
	  
	private Method getMethod;
	private Method putMethod;
	
	private Class<? extends GenericRequest> getMethodParameterType;
	private Class<? extends GenericRequest> putMethodParameterType;

	
	@SuppressWarnings("unchecked")
	@Override
	public void init() throws ServletException {
		perThreadRequest = new ThreadLocal<HttpServletRequest>();
		perThreadResponse = new ThreadLocal<HttpServletResponse>();
		
		for( Method method : this.getClass().getMethods() ) {
			if( method.getAnnotation( Get.class ) != null ) {
				getMethod = method;
				getMethodParameterType = (Class<? extends GenericRequest>) method.getParameterTypes()[0];
			} else if( method.getAnnotation( Put.class ) != null ) {
				putMethod = method;
				putMethodParameterType = (Class<? extends GenericRequest>) method.getParameterTypes()[0];
			}
		}
	}
	

	protected void service(
			HttpServletRequest request,
			HttpServletResponse response ) throws ServletException, IOException {
		
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
		
		
		logger.log( Level.INFO, "Request Payload: " + requestPayloadJson );
		
		
		// Request method
		String method = request.getMethod();
	
		
		// Parsing request payload to request object
		GenericRequest apiRequest;
		if( method.equals( "GET" ) && getMethod != null ) {
			apiRequest = gson.fromJson( requestPayloadJson, getMethodParameterType );
		} else if( method.equals( "PUT" ) && putMethod != null ) {
			apiRequest = gson.fromJson( requestPayloadJson, putMethodParameterType );
		} else {
			super.service( request, response );
			return;
		}

		
		// TODO: Validate apiRequest. send SC_BAD_REQUEST if required parameters are missing.
		
		
		perThreadRequest.set( request );
		perThreadResponse.set( response );
		Object apiResponse = null;

		
		// Invoking get/put method for API response
		try {
			if( method.equals( "GET" ) ) {
				apiResponse = (GenericResponse) getMethod.invoke( this, apiRequest );
			} else if( method.equals( "PUT" ) ) {
				apiResponse = (GenericResponse) putMethod.invoke( this, apiRequest );
			}
			
		} catch( InvocationTargetException e ) {
			Throwable te = e.getTargetException();
			if( te instanceof InsufficientAccessException
					|| te instanceof com.claymus.commons.shared.exception.IllegalArgumentException
					|| te instanceof UnexpectedServerException ) {
				apiResponse = te;
			
			} else {
				logger.log( Level.SEVERE, "Failed to execute API.", e );
				apiResponse = new UnexpectedServerException();
			}
			
		} catch( IllegalAccessException | IllegalArgumentException e ) {
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			apiResponse = new UnexpectedServerException();
		}


		// Dispatching API response
		if( apiResponse instanceof GenericResponse ) {
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();
			writer.println( gson.toJson( apiResponse ) );
			writer.close();
		
		} else if( apiResponse instanceof BlobEntry ) {
			BlobEntry blobEntry = (BlobEntry) apiResponse;

			String eTag = request.getHeader( "If-None-Match" );
			if( eTag == null )
				logger.log( Level.INFO, "No eTag found !" );
				
			if( eTag != null && eTag.equals( blobEntry.getETag() ) ) {
				response.setStatus( HttpServletResponse.SC_NOT_MODIFIED );
			
			} else {
				response.setContentType( blobEntry.getMimeType() );
				response.setHeader( "ETag", blobEntry.getETag() );

				OutputStream out = response.getOutputStream();
				out.write( blobEntry.getData() );
				out.close();
			}
			
		} else if( apiResponse instanceof InsufficientAccessException ) {
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();
			writer.println( ((Throwable) apiResponse ).getMessage() );
			writer.close();
			
		} else if( apiResponse instanceof com.claymus.commons.shared.exception.IllegalArgumentException ) {
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();
			writer.println( ((Throwable) apiResponse ).getMessage() );
			writer.close();
		
		} else if( apiResponse instanceof UnexpectedServerException ) {
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();
			writer.println( ((Throwable) apiResponse ).getMessage() );
			writer.close();
		}
		
		
		DataAccessorFactory.getDataAccessor( request ).destroy();
		perThreadRequest.set( null );
		perThreadResponse.set( null );
	}
	
	protected HttpServletRequest getThreadLocalRequest() {
		return perThreadRequest.get();
	}
	
	protected HttpServletResponse getThreadLocalResponse() {
		return perThreadResponse.get();
	}
	
}
