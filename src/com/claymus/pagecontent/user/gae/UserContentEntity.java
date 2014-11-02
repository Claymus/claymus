package com.claymus.pagecontent.user.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.user.UserContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class UserContentEntity extends PageContentEntity implements UserContent { }
