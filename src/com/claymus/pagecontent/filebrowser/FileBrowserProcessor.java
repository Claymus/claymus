package com.claymus.pagecontent.filebrowser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.FreeMarkerUtil;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.pagecontent.PageContentProcessor;

public class FileBrowserProcessor extends PageContentProcessor<FileBrowser> {

	private static final Logger logger =
			Logger.getLogger( FileBrowserProcessor.class.getName() );

	
	@Override
	public String generateTitle( FileBrowser fileBrowser, HttpServletRequest request ) {
		return "Browse Files";
	}
	
	@Override
	public String generateHtml( FileBrowser fileBrowser, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		String folder = request.getParameter( "folder" );
		String urlPrefix = request.getParameter( "urlPrefix" );
		
		if( folder == null || folder.isEmpty() )
			throw new InvalidArgumentException( "Folder name is missing." );
		else if( urlPrefix == null || urlPrefix.isEmpty() )
			throw new InvalidArgumentException( "Url prefix is missing." );
		else if( ! folder.contains( "-resource/" ) ) // Restricting access to children of *-resource folders.
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		List<String> nameList = null;
		try {
			nameList = blobAccessor.getNameList( folder + "/" );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to fetch file list.", e );
			throw new UnexpectedServerException();
		}
		

		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "nameList", nameList );
		dataModel.put( "urlPrefix", urlPrefix );
		

		// Processing template
		return FreeMarkerUtil.processTemplate( dataModel, getTemplateName() );
	}

}
