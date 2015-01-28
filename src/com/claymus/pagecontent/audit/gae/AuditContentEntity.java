package com.claymus.pagecontent.audit.gae;

import javax.jdo.annotations.PersistenceCapable;

import com.claymus.data.access.gae.PageContentEntity;
import com.claymus.pagecontent.audit.AuditContent;

@SuppressWarnings("serial")
@PersistenceCapable
public class AuditContentEntity extends PageContentEntity implements AuditContent {}
