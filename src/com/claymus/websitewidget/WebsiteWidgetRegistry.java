package com.claymus.websitewidget;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.WebsiteWidget;

public final class WebsiteWidgetRegistry {

	private static final Logger logger = 
			Logger.getLogger( WebsiteWidgetRegistry.class.getName() );

	
	@SuppressWarnings("rawtypes")
	private static final List<WebsiteWidgetHelper> helperList = new LinkedList<>();
	
	private static final Map<
			Class<? extends WebsiteWidget>,
			WebsiteWidgetProcessor<? extends WebsiteWidget> > mapWidgetToProcessor = new HashMap<>();


	public static <
			P extends WebsiteWidget,
			Q extends WebsiteWidgetProcessor<P>,
			R extends WebsiteWidgetHelper<P, Q> > void register( Class<R> websiteWidgetHelperClass ) {
		
		ParameterizedType parameterizedType =
				(ParameterizedType) websiteWidgetHelperClass.getGenericSuperclass();
		
		@SuppressWarnings("unchecked")
		Class<P> websiteWidgetClass =
				(Class<P>) parameterizedType.getActualTypeArguments()[0];
		@SuppressWarnings("unchecked")
		Class<Q> websiteWidgetProcessorClass = 
				(Class<Q>) parameterizedType.getActualTypeArguments()[1];
		
		try {
			Q websiteWidgetProcessor = websiteWidgetProcessorClass.newInstance();
			R websiteWidgetHelper = websiteWidgetHelperClass.newInstance();
			helperList.add( websiteWidgetHelper );
			mapWidgetToProcessor.put( websiteWidgetClass, websiteWidgetProcessor );
		} catch ( InstantiationException | IllegalAccessException e ) {
			logger.log( Level.SEVERE, "Module registration failed.", e );
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static List<WebsiteWidgetHelper> getWebsiteWidgetHelperList() {
		return helperList;
	}
	
	@SuppressWarnings("unchecked")
	public static <P extends WebsiteWidget> WebsiteWidgetProcessor<P> getWebsiteWidgetProcessor(
			Class<P> websiteWidgetClass ) {
		
		if( websiteWidgetClass.isInterface() )
			return (WebsiteWidgetProcessor<P>) mapWidgetToProcessor.get( websiteWidgetClass );
		else
			return (WebsiteWidgetProcessor<P>) mapWidgetToProcessor.get( websiteWidgetClass.getInterfaces()[0] );
	}
	
}
