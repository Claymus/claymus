package com.claymus.data.access.gae;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PAGE_CONTENT" )
public class PageContentEntityStub implements Serializable {

	@PrimaryKey
	@Persistent( column = "PAGE_CONTENT_ID" )
	private Long id;
	
	@Persistent( column = "PAGE_ID" )
	private Long pageId;
	
	@Persistent( column = "POSITION" )
	private String position;
	
	@Persistent( column = "_TYPE" )
	private String type;

	
	public Long getId() {
		return id;
	}

	public Long getPageId() {
		return pageId;
	}

	public String getPosition() {
		return position;
	}

	public String getType() {
		return type;
	}

}
