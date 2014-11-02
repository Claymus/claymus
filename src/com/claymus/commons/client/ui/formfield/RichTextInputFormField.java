package com.claymus.commons.client.ui.formfield;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class RichTextInputFormField extends FormField {

	private final Panel panel = new FlowPanel();
	private final Panel htmlPanel = new SimplePanel();
	private String ckEditorName;

	
	public RichTextInputFormField() {
		
		panel.add( htmlPanel );

		initWidget( panel );
	}

	
	public String getHtml() {
		String html = isAttached()
				? getHtmlFromEditor( ckEditorName ).trim()
				: htmlPanel.getElement().getInnerHTML().trim();
		return html.isEmpty() ? null : html;
	}
	
	public void setHtml( String html ) {
		if( isAttached() )
			setHtmlInEditor( ckEditorName, html == null ? "" : html );
		else
			htmlPanel.getElement().setInnerHTML( html == null ? "" : html );
	}
	
	@Override
	public void setEnabled( boolean enabled ) {
		if( isAttached() )
			setEditorEnabled( ckEditorName, enabled );
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void resetValidation() {
		// Do nothing
	}

	
	@Override
	protected void onLoad() {
		ckEditorName = loadEditor( htmlPanel.getElement() );
	}

	@Override
	protected void onUnload() {
		unloadEditor( ckEditorName );
	}

	
	private native String loadEditor( Element element ) /*-{
		return $wnd.CKEDITOR.replace( element ).name;
	}-*/;

	private native String unloadEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].destroy();
	}-*/;

	private native String getHtmlFromEditor( String editorName ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].getData();
	}-*/;

	private native String setHtmlInEditor( String editorName, String html ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].setData( html );
	}-*/;

	private native String setEditorEnabled( String editorName, boolean enabled ) /*-{
		return $wnd.CKEDITOR.instances[ editorName ].setReadOnly( enabled );
	}-*/;

}