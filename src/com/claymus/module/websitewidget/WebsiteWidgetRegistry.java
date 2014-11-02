package com.claymus.module.websitewidget;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.WebsiteWidget;

public final class WebsiteWidgetRegistry {

	private static final Logger logger = 
			Logger.getLogger( WebsiteWidgetRegistry.class.getName() );

	
	private final Map<
			Class<? extends WebsiteWidget>,
			WebsiteWidgetProcessor<? extends WebsiteWidget> > map = new HashMap<>();


	@SuppressWarnings("unchecked")
	public <
			P extends WebsiteWidget,
			Q extends WebsiteWidgetProcessor<P>,
			R extends WebsiteWidgetFactory<P, Q> > void register( Class<R> websiteWidgetFactoryClass ) {
		
		ParameterizedType parameterizedType =
				(ParameterizedType) websiteWidgetFactoryClass.getGenericInterfaces()[0];
		
		Class<P> websiteWidgetClass =
				(Class<P>) parameterizedType.getActualTypeArguments()[0];
		Class<Q> websiteWidgetProcessorClass = 
				(Class<Q>) parameterizedType.getActualTypeArguments()[1];
		
		try {
			Q websiteWidgetProcessor = websiteWidgetProcessorClass.newInstance();
			map.put( websiteWidgetClass, websiteWidgetProcessor );
		} catch ( InstantiationException | IllegalAccessException e ) {
			logger.log( Level.SEVERE, "Module registration failed.", e );
		}
	}
	
	@SuppressWarnings("unchecked")
	public <P extends WebsiteWidget> WebsiteWidgetProcessor<P> getWebsiteWidgetProcessor(
			Class<P> websiteWidgetClass ) {
		
		if( websiteWidgetClass.isInterface() )
			return (WebsiteWidgetProcessor<P>) map.get( websiteWidgetClass );
		else
			return (WebsiteWidgetProcessor<P>) map.get( websiteWidgetClass.getInterfaces()[0] );
	}
	
}
