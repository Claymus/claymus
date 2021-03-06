package com.claymus.data.access.gcs;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Map;

import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;

@SuppressWarnings("serial")
public class BlobEntryGcsImpl implements BlobEntry {
	
	private String fileName;
	private byte[] data;
	private long length;
	private String mimeType;
	private String cacheControl;
	private String eTag;
	private Date lastModified;
	private Map<String, String> metaData;
	
	
	public BlobEntryGcsImpl( String fileName ) {
		this.fileName = fileName;
	}
	
	public BlobEntryGcsImpl( String fileName, byte[] data, String mimeType ) {
		this.fileName = fileName;
		this.data = data;
		this.mimeType = mimeType;
	}
	
	public BlobEntryGcsImpl( ByteBuffer byteBuffer, GcsFileMetadata gcsFileMetadata ) {
		this.fileName = gcsFileMetadata.getFilename().getObjectName();
		this.data = byteBuffer.array();
		this.length = gcsFileMetadata.getLength();
		this.mimeType = gcsFileMetadata.getOptions().getMimeType();
		this.cacheControl = gcsFileMetadata.getOptions().getCacheControl();
		this.eTag = gcsFileMetadata.getEtag();
		this.lastModified = gcsFileMetadata.getLastModified();
		this.metaData = gcsFileMetadata.getOptions().getUserMetadata();
	}

	
	@Override
	public String getName() {
		return fileName;
	}
	
	@Override
	public void setName( String fileName ) {
		this.fileName = fileName;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public void setData( byte[] byteArray ) {
		this.data = byteArray;
	}
	
	@Override
	public long getDataLength() {
		return length;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}
	
	@Override
	public void setMimeType( String mimeType ) {
		this.mimeType = mimeType;
	}
	
	@Override
	public String getCacheControl() {
		return cacheControl;
	}

	@Override
	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}
	
	@Override
	public String getETag() {
		return eTag;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

	@Override
	public Map<String, String> getMetaData() {
		return metaData;
	}

	@Override
	public String getMetaData( String key ) {
		return metaData.get( key );
	}

}
