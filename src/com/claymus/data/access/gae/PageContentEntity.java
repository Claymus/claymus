package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.PageContent;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PAGE_CONTENT" )
@Discriminator( column = "_TYPE", strategy = DiscriminatorStrategy.CLASS_NAME )
public abstract class PageContentEntity implements PageContent {

	@PrimaryKey
	@Persistent( column = "PAGE_CONTENT_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PAGE_ID" )
	private Long pageId;
	
	@Persistent( column = "POSITION" )
	private String position;

	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Persistent( column = "LAST_UPDATED" )
	private Date lastUpdated;
	
	
	@Override
	public Long getId() {
		return id;
	}

	protected void setId( Long id ) {
		this.id = id;
	}

	@Override
	public Long getPageId() {
		return pageId;
	}

	@Override
	public void setPageId( Long pageId ) {
		this.pageId = pageId;
	}

	@Override
	public String getPosition() {
		return position;
	}

	@Override
	public void setPosition( String position ) {
		this.position = position;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated;
	}

}
