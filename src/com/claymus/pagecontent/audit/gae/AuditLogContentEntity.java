package com.claymus.pagecontent.audit.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.audit.AuditLogContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class AuditLogContentEntity extends PageContentEntity
		implements AuditLogContent {

}
 