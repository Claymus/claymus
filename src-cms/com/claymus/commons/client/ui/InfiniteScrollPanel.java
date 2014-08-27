package com.claymus.commons.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public abstract class InfiniteScrollPanel extends Composite implements Window.ScrollHandler {

	private final Panel panel = new FlowPanel();

	private int pagesToPreLoad = 5;
	private boolean loadingItems = false;

	
	public InfiniteScrollPanel() {
		
		initWidget( panel );
		
		Window.addWindowScrollHandler( this );
	}

	
	public void add( Widget child ) {
		panel.add( child );
	}

	public void add( IsWidget child ) {
		panel.add( child );
	}

	@Override
	protected void onLoad() {
		loadingItems = true;
		loadItems();
	}

	@Override
	public void onWindowScroll( Window.ScrollEvent event ) {
		int currentPanelHeight = panel.getOffsetHeight();
		int requiredPanelHeight =
				// Hidden portion above visible area
				Window.getScrollTop() - panel.getAbsoluteTop()
				// Visible portion
				+ Window.getClientHeight()
				// Hidden portion below visible area
				+ pagesToPreLoad * Window.getClientHeight();

		if( !loadingItems && currentPanelHeight < requiredPanelHeight ) {
			loadingItems = true;
			loadItems();
		}
	}
	
	protected abstract void loadItems();
	
	protected void loadSuccessful() {
		loadingItems = false;
	}

	protected void loadFailed() {
		loadingItems = false;
	}
	
}