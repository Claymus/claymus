package com.claymus.pagecontent.filebrowser.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.filebrowser.FileBrowserContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class FileBrowserContentEntity extends PageContentEntity implements FileBrowserContent { }