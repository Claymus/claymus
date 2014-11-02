package com.claymus.data.access;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DataListCursorTuple<T> implements Serializable {

	private List<T> dataList;
	private String cursor;
	
	
	public DataListCursorTuple( List<T> dataList, String cursor ) {
		this.dataList = dataList;
		this.cursor = cursor;
	}

	
	public List<T> getDataList() {
		return dataList;
	}
	
	public String getCursor() {
		return cursor;
	}
	
}
