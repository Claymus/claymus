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
	
	@Persistent( column = "PAGE_TYPES_BASIC_MODE" )
	private String[] basicModePageTypes;

	@Persistent( column = "PAGE_TYPES_BASIC_MODE_INCLUSIVE" )
	private Boolean basicModePageTypesInclusive;

	@Persistent( column = "PAGE_TYPES_STANDARD_MODE" )
	private String[] standardModePageTypes;
	
	@Persistent( column = "PAGE_TYPES_STANDARD_MODE_INCLUSIVE" )
	private Boolean standardModePageTypesInclusive;
	
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

	@Override
	public String[] getBasicModePageTypes() {
		return basicModePageTypes == null ? new String[0] : basicModePageTypes;
	}
	
	@Override
	public boolean isBasicModePageTypeListInclusive() {
		return basicModePageTypesInclusive == null ? false : basicModePageTypesInclusive;
	}
	
	@Override
	public void setBasicModePageTypes( String[] pageTypes ) {
		setBasicModePageTypes( pageTypes, true );
	}
	
	@Override
	public void setBasicModePageTypes( String[] pageTypes, boolean include ) {
		this.basicModePageTypes = pageTypes;
		this.basicModePageTypesInclusive = include;
	}

	@Override
	public String[] getStandardModePageTypes() {
		return standardModePageTypes == null ? new String[0] : standardModePageTypes;
	}
	
	@Override
	public boolean isStandardModePageTypeListInclusive() {
		return standardModePageTypesInclusive == null ? false : standardModePageTypesInclusive;
	}
	
	@Override
	public void setStandardModePageTypes( String[] pageTypes ) {
		setStandardModePageTypes( pageTypes, true );
	}
	
	@Override
	public void setStandardModePageTypes( String[] pageTypes, boolean include ) {
		this.standardModePageTypes = pageTypes;
		this.standardModePageTypesInclusive = include;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

	@Override
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.lastUpdated = lastUpdated;
	}

}
