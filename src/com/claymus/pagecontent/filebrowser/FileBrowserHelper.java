package com.claymus.pagecontent.filebrowser;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.filebrowser.gae.FileBrowserEntity;
import com.claymus.pagecontent.filebrowser.shared.FileBrowserData;


public class FileBrowserHelper extends PageContentHelper<FileBrowser, FileBrowserData, FileBrowserProcessor> {

	@Override
	public String getModuleName() {
		return "File Browser";
	}

	@Override
	public Double getModuleVersion() {
		return 3.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static FileBrowser newFileBrowser() {
		return new FileBrowserEntity();
	}
	
}
