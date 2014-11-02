package com.claymus.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

@SuppressWarnings("serial")
public class ResourceServlet extends HttpServlet {
	
	private static final Logger logger =
			Logger.getLogger( ResourceServlet.class.getName() );

	
	@Override
	public void doPost(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		ServletConfig servletConfig = getServletConfig();

		String resourceServletType = servletConfig.getInitParameter( "ResourceServletType" );
		if( resourceServletType != null && resourceServletType.equals( "DOWNLOAD_ONLY" ) ) {
			response.sendError( HttpServletResponse.SC_METHOD_NOT_ALLOWED );
			return;
		}

			
		String fileName = getFileName( request );
		if( ! DataAccessorFactory.getBlobAccessor().createBlob( request, fileName ) )
			response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		
		
		postUpload( request );
	}
	
	@Override
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		ServletConfig servletConfig = getServletConfig();
		String resourceServletType = servletConfig.getInitParameter( "ResourceServletType" );
		if( resourceServletType != null && resourceServletType.equals( "UPLOAD_ONLY" ) ) {
			response.sendError( HttpServletResponse.SC_METHOD_NOT_ALLOWED );
			return;
		}

		
		BlobEntry blobEntry = getBlobEntry( request );
		if( blobEntry == null ) {
			response.sendError( HttpServletResponse.SC_NOT_FOUND );
			return;
		}
		
		
		String eTag = request.getHeader( "If-None-Match" );
		if( eTag == null )
			logger.log( Level.WARNING, "Cound not find an eTag. Request: \n" + request );
			
		if( eTag != null && eTag.equals( blobEntry.getETag() ) ) {
			response.setStatus( HttpServletResponse.SC_NOT_MODIFIED );
		
		} else {
			response.setContentType( blobEntry.getMimeType() );
			response.setHeader( "ETag", blobEntry.getETag() );

			OutputStream out = response.getOutputStream();
			String resizeImageParam = servletConfig.getInitParameter( "ResizeImage" );
			
			if( resizeImageParam != null ) {
				ImagesService imagesService = ImagesServiceFactory.getImagesService();
		        Transform resize = ImagesServiceFactory.makeResize(
		        		Integer.parseInt( resizeImageParam ),
		        		10 * Integer.parseInt( resizeImageParam ) );
		        Image oldImage = ImagesServiceFactory.makeImage( blobEntry.getData() );
		        Image newImage = imagesService.applyTransform( resize, oldImage );
		        out.write( newImage.getImageData() );
			
			} else {
				out.write( blobEntry.getData() );

			}
			
			out.close();
		}
	}
	
	protected BlobEntry getBlobEntry( HttpServletRequest request ) throws IOException {
		String fileName = getFileName( request );
		return DataAccessorFactory.getBlobAccessor().getBlob( fileName );
	}
	
	protected String getFileName( HttpServletRequest request ) {
		return request.getRequestURI()
				.substring( ClaymusHelper.URL_RESOURCE.length() );
	}
	
	protected void postUpload( HttpServletRequest request ) throws IOException {}
	
}
