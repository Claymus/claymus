package com.claymus.commons.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.data.transfer.BlobEntry;

public class ResizeBlob {

	private static final Logger logger = 
			Logger.getLogger( ResizeBlob.class.getName() );
	
	private final static Long MAX_CACHABLE_SIZE = (long) ( 1000*1024 );
	
	private BlobEntry blobEntry;
	private byte[] blobData;

	public ResizeBlob( BlobEntry blobEntry ){
		this.blobEntry = blobEntry;
		this.blobData = blobEntry.getData();
	}

	public Long getSegmentCount(){
		Long numberOfSegments = blobEntry.getSize() % MAX_CACHABLE_SIZE == 0L ? 
										( blobEntry.getSize() / MAX_CACHABLE_SIZE ) : ( blobEntry.getSize() / MAX_CACHABLE_SIZE + 1 );
		logger.log( Level.INFO, "Number of segments : " + numberOfSegments );
		return numberOfSegments;
	}
	
	public BlobEntry getMemcacheOptimizedBlob(){
		blobEntry.setData( Arrays.copyOfRange( blobData, 0, (int)(long)MAX_CACHABLE_SIZE ));
		return blobEntry;
	}
	
	public List<byte[]> getSegmentList(){
		int segmentNo = 1;
		List<byte[]> segmentList = new ArrayList<byte[]> ();
		while( segmentNo < getSegmentCount() ){
			int startIndex = segmentNo * (int)(long)MAX_CACHABLE_SIZE;
			int endIndex = startIndex + (int)(long)MAX_CACHABLE_SIZE;
			if( endIndex > this.blobData.length )
				endIndex = this.blobData.length - 1;
			byte[] temp = Arrays.copyOfRange( blobData, startIndex, endIndex );
			segmentList.add( temp );
			segmentNo++;
		}
		return segmentList;
	}
	
	public void appendSegment( byte[] byteSegment ){
		blobEntry.appendData( byteSegment );
	}
	
	public BlobEntry getBlobEntry(){
		return this.blobEntry;
	}
	
	
}
