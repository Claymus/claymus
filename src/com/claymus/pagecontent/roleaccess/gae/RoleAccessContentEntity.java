package com.claymus.pagecontent.roleaccess.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.roleaccess.RoleAccessContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class RoleAccessContentEntity extends PageContentEntity
		implements RoleAccessContent { }
