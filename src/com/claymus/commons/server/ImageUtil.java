package com.claymus.commons.server;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageUtil {

	private static final ImagesService imagesService = ImagesServiceFactory.getImagesService();
	

	public static byte[] resize( byte[] imageData, int width, int height ) {
		Image image = ImagesServiceFactory.makeImage( imageData );
		Transform resize = ImagesServiceFactory.makeResize( width, height );
		return imagesService.applyTransform( resize, image ).getImageData();
	}
	
}
