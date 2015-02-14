package com.claymus.pagecontent.filebrowser.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.filebrowser.FileBrowser;

@SuppressWarnings("serial")
@PersistenceCapable
public class FileBrowserEntity extends PageContentEntity implements FileBrowser { }