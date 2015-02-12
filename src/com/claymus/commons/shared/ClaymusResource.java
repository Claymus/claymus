package com.claymus.commons.shared;

public enum ClaymusResource implements Resource {

	CKEDITOR( "<script src='//cdn-asia.pratilipi.com/third-party/ckeditor-4.4.5-full/ckeditor.js' charset='utf-8'></script>" ),

	JQUERY_1( "" ),
	JQUERY_2( "" ),

	POLYMER( "<script src='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/webcomponentsjs/webcomponents.js'></script>"
			+ "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/polymer/polymer.html'>" ),

	POLYMER_CORE_AJAX				( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-ajax/core-ajax.html'>" ),
	POLYMER_CORE_COLLAPSE			( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-collapse/core-collapse.html'>" ),
	POLYMER_CORE_HEADER_PANEL		( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-header-panel/core-header-panel.html'>" ),
	POLYMER_CORE_ICON_BUTTON		( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-icon-button/core-icon-button.html'>" ),
	POLYMER_CORE_ICONS				( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-icons/core-icons.html'>" ),
	POLYMER_CORE_ITEM				( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-item/core-item.html'>" ),
	POLYMER_CORE_MENU				( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-menu/core-menu.html'>" ),
	POLYMER_CORE_SCROLL_HEADER_PANEL( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-scroll-header-panel/core-scroll-header-panel.html'>" ),
	POLYMER_CORE_SCROLL_THREHSOLD	( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-scroll-threshold/core-scroll-threshold.html'>" ),
	POLYMER_CORE_TOOLBAR			( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/core-toolbar/core-toolbar.html'>" ),
	
	POLYMER_PAPER_ACTION_DIALOG		( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-dialog/paper-action-dialog.html'>" ),
	POLYMER_PAPER_BUTTON			( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-button/paper-button.html'>" ),
	POLYMER_PAPER_DIALOG			( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-dialog/paper-dialog.html'>" ),
	POLYMER_PAPER_FAB				( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-fab/paper-fab.html'>" ),
	POLYMER_PAPER_ICON_BUTTON		( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-icon-button/paper-icon-button.html'>" ),
	POLYMER_PAPER_INPUT_DECORATOR	( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-input/paper-input-decorator.html'>" ),
	POLYMER_PAPER_ITEM				( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-item/paper-item.html'>" ),
	POLYMER_PAPER_MENU_BUTTON		( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-menu-button/paper-menu-button.html'>" ),
	POLYMER_PAPER_SLIDER			( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-slider/paper-slider.html'>" ),
	POLYMER_PAPER_SPINNER			( "<link rel='import' href='//cdn-asia.pratilipi.com/third-party/polymer-0.5.2/paper-spinner/paper-spinner.html'>" ),
	;
	

	private String tag;
	
	
	private ClaymusResource( String tag ) {
		this.tag = tag;
	}
	
	public String getTag() {
		return this.tag;
	}
	
}
