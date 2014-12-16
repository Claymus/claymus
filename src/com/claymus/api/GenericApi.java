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
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;


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
	

	@Override
	protected final void service(
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
		
		
		perThreadRequest.set( request );
		perThreadResponse.set( response );
		
		
		// Invoking get/put method for API response
		Object apiResponse = null;
		String method = request.getMethod();
		if( method.equals( "GET" ) && getMethod != null )
			apiResponse = executeApi( getMethod, requestPayloadJson, getMethodParameterType );
		else if( method.equals( "PUT" ) && putMethod != null )
			apiResponse = executeApi( putMethod, requestPayloadJson, putMethodParameterType );
		else
			apiResponse = new UnexpectedServerException( "Invalid resource or method." );

		
		// Dispatching API response
		dispatchApiResponse( apiResponse, request, response );
		
		
		perThreadRequest.set( null );
		perThreadResponse.set( null );
		DataAccessorFactory.getDataAccessor( request ).destroy();
	}
	
	
	private Object executeApi( Method apiMethod, JsonObject requestPayloadJson,
			Class<? extends GenericRequest> apiMethodParameterType ) {
		
		try {
			GenericRequest apiRequest = gson.fromJson( requestPayloadJson, apiMethodParameterType );
			apiRequest.validate();
			String accessTokenId = apiRequest.getAccessToken();
			if( accessTokenId != null ) {
				HttpServletRequest httpRequest = this.getThreadLocalRequest();
				DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( httpRequest );
				if( dataAccessor.getAccessToken( accessTokenId ) == null )
					throw new InvalidArgumentException( "AccessToken is invalid or expired." );
				httpRequest.setAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN, apiRequest.getAccessToken() );
			}
			return apiMethod.invoke( this, apiRequest );

		} catch( JsonSyntaxException e ) {
			return new InvalidArgumentException( "Invalid JSON in request body." );
		
		} catch( InvalidArgumentException | UnexpectedServerException e) {
			return e;
		
		} catch( InvocationTargetException e ) {
			Throwable te = e.getTargetException();
			if( te instanceof InvalidArgumentException
					|| te instanceof InsufficientAccessException
					|| te instanceof UnexpectedServerException ) {
				return te;
			
			} else {
				logger.log( Level.SEVERE, "Failed to execute API.", e );
				return new UnexpectedServerException();
			}
			
		} catch( IllegalAccessException | IllegalArgumentException e ) {
			logger.log( Level.SEVERE, "Failed to execute API.", e );
			return new UnexpectedServerException();
		}
		
	}
	
	private void dispatchApiResponse( Object apiResponse,
			HttpServletRequest request, HttpServletResponse response ) throws IOException {
		
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
			
		} else if( apiResponse instanceof Throwable ) {

			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = response.getWriter();

			if( apiResponse instanceof InvalidArgumentException )
				response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
			else if( apiResponse instanceof InsufficientAccessException )
				response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
			else if( apiResponse instanceof UnexpectedServerException )
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			else
				response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			
			writer.println( ((Throwable) apiResponse ).getMessage() );
			writer.close();

		}
		
	}
	
	protected HttpServletRequest getThreadLocalRequest() {
		return perThreadRequest.get();
	}
	
	protected HttpServletResponse getThreadLocalResponse() {
		return perThreadResponse.get();
	}
	
}
