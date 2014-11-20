package com.claymus.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.claymus.data.transfer.WebsiteWidget;

@SuppressWarnings("serial")
@PersistenceCapable( table = "WEBSITE_WIDGET" )
@Discriminator( column = "TYPE", strategy = DiscriminatorStrategy.CLASS_NAME )
public abstract class WebsiteWidgetEntity implements WebsiteWidget {

	@PrimaryKey
	@Persistent( column = "WEBSITE_WIDGET_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
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
