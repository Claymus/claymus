package com.claymus.commons.client;

import com.google.gwt.dom.client.Element;

public class SocialUtil {

	public static native void renderSocial( Element element ) /*-{
		$wnd.FB.XFBML.parse( element );
		$wnd.twttr.widgets.load( element );
	}-*/;
	
}
