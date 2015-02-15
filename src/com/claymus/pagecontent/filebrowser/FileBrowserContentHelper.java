package com.claymus.pagecontent.filebrowser;

import com.claymus.commons.server.Access;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.filebrowser.gae.FileBrowserContentEntity;
import com.claymus.pagecontent.filebrowser.shared.FileBrowserContentData;

public class FileBrowserContentHelper extends PageContentHelper<
		FileBrowserContent,
		FileBrowserContentData,
		FileBrowserContentProcessor> {

	@Override
	public String getModuleName() {
		return "File Browser";
	}

	@Override
	public Double getModuleVersion() {
		return 5.1;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {};
	}
	
	
	public static FileBrowserContent newFileBrowserContent() {
		return new FileBrowserContentEntity();
	}
	
}
