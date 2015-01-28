package com.claymus.pagecontent.audit;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.data.access.DataAccessor;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AuditLog;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.audit.gae.AuditLogContentEntity;
import com.claymus.pagecontent.audit.shared.AuditLogData;

public class AuditLogContentHelper extends PageContentHelper<
		AuditLogContent,
		AuthorContentData,
		AuditLogContentProcessor> {
	
	public static final Access ACCESS_TO_READ_AUDITLOG_CONTENT =
			new Access( "auditlog_data_read_content", false, "View Audit Log Content" );
	
	
	@Override
	public String getModuleName() {
		return "AuditLog";
	}

	@Override
	public Double getModuleVersion() {
		return 4.0;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_READ_AUDITLOG_CONTENT
		};
	}
	
	public static AuditLogContent newAuditLogContent() {
		return new AuditLogContentEntity();
	}
	
	public static boolean hasRequestAccessToReadAuditLogContent( HttpServletRequest request, AuditLog auditLog ) {
		return false;
	}	
	
	private static AuditLogData createAuditLogData( AuditLog auditLog ) {
		AuditLogData auditLogData = new AuditLogData();
		
		auditLogData.setId( auditLog.getId() );
		auditLogData.setCreationDate( auditLog.getCreationDate() );
		auditLogData.setAccessId( auditLog.getAccessId() );
		auditLogData.setEventDataNew( auditLog.getEventDataNew() );
		auditLogData.setEventDataOld( auditLog.getEventDataOld() );
		auditLogData.setEventId( auditLog.getEventId() );
		
		return auditLogData;
	}

	public static DataListCursorTuple<AuditLogData> getAuditLog( String cursor, int resultCount, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<AuditLog> auditLogListCursorTuple = 
				dataAccessor.getAuditLogList( cursor, resultCount );
		List<AuditLogData> auditLogData = new ArrayList<AuditLogData>();
		for( AuditLog auditLog : auditLogListCursorTuple.getDataList() )
			auditLogData.add( createAuditLogData( auditLog ));
		
		return new DataListCursorTuple<AuditLogData>( auditLogData, auditLogListCursorTuple.getCursor() );
	}
}
