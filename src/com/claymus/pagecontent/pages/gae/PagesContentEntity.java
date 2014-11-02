package com.claymus.pagecontent.pages.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.pages.PagesContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class PagesContentEntity extends PageContentEntity implements PagesContent { }
