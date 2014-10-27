package com.claymus.pagecontent.filebrowser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.access.BlobAccessorGcsImpl;
import com.claymus.pagecontent.PageContentProcessor;

public class FileBrowserProcessor extends PageContentProcessor<FileBrowser> {

	@Override
	public String generateHtml(
			FileBrowser fileBrowser, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		BlobAccessor blobAccessor = new BlobAccessorGcsImpl( ClaymusHelper.URL_RESOURCE_STATIC );
		String prefix = "pratilipi-cover/300";
		List<String> imageNameList = null;
		try {
			imageNameList = blobAccessor.getFilenameList( prefix );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> imageUrlList = new ArrayList<>();
		
		for( String imageName : imageNameList )
			//Wasn't sure about using PratilipiHelper.getCoverImage300Url() here.
			imageUrlList.add( ClaymusHelper.URL_RESOURCE_STATIC + 
								"/" + prefix +"/" + imageName );
		
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "imageUrlList", imageUrlList );
		

		// Processing template
		return super.processTemplate( dataModel, getTemplateName() );
	}

}
