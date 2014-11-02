package com.claymus.pagecontent.roleaccess.client;

import com.claymus.service.client.ClaymusService;
import com.claymus.service.client.ClaymusServiceAsync;
import com.claymus.service.shared.SaveRoleAccessRequest;
import com.claymus.service.shared.SaveRoleAccessResponse;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class RoleAccessContent implements EntryPoint {

	private static final ClaymusServiceAsync claymusService =
			GWT.create( ClaymusService.class );
	

	public void onModuleLoad() {
		
		for( int i = 0; ; i++ ) {
			RootPanel rootPanel = RootPanel.get( "accessId-" + i );
			if( rootPanel == null )
				break;
			
			final String roleId = rootPanel.getElement().getAttribute( "roleId" );
			final String accessId = rootPanel.getElement().getAttribute( "accessId" );
			final Boolean hasAccess = Boolean.parseBoolean(
					rootPanel.getElement().getAttribute( "hasAccess" ) );
			
			final Button grantButton = new Button( "Grant" );
			final Button revokeButton = new Button( "Revoke" );
			
			grantButton.setEnabled( ! hasAccess );
			grantButton.setVisible( ! hasAccess );
			
			revokeButton.setEnabled( hasAccess );
			revokeButton.setVisible( hasAccess );

			grantButton.addClickHandler( new ClickHandler() {
				
				@Override
				public void onClick( ClickEvent event ) {
					grantButton.setEnabled( false );
					claymusService.saveRoleAccess(
							new SaveRoleAccessRequest( roleId, accessId, true ),
							new AsyncCallback<SaveRoleAccessResponse>() {
								
								@Override
								public void onSuccess( SaveRoleAccessResponse response ) {
									grantButton.setVisible( false );
									revokeButton.setEnabled( true );
									revokeButton.setVisible( true );
								}
								
								@Override
								public void onFailure(Throwable caught) {
									grantButton.setEnabled( true );
								}
								
							});
				}
				
			});

			revokeButton.addClickHandler( new ClickHandler() {
				
				@Override
				public void onClick( ClickEvent event ) {
					revokeButton.setEnabled( false );
					claymusService.saveRoleAccess(
							new SaveRoleAccessRequest( roleId, accessId, false ),
							new AsyncCallback<SaveRoleAccessResponse>() {
								
								@Override
								public void onSuccess( SaveRoleAccessResponse response ) {
									revokeButton.setVisible( false );
									grantButton.setEnabled( true );
									grantButton.setVisible( true );
								}
								
								@Override
								public void onFailure(Throwable caught) {
									revokeButton.setEnabled( true );
								}
								
							});
				}
				
			});

			rootPanel.getElement().setInnerHTML( "" );
			rootPanel.add( grantButton );
			rootPanel.add( revokeButton );
			
			grantButton.setStyleName( "btn btn-info btn-xs" );
			revokeButton.setStyleName( "btn btn-warning btn-xs" );
		}
		
	}
	
}