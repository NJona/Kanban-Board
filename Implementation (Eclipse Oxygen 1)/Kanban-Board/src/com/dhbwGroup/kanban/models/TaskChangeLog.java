package com.dhbwGroup.kanban.models;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TaskChangeLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TaskChangeLog(String columnName) {
		this.timestamp = System.currentTimeMillis();
		this.columnName = columnName;
	}
	
	@SerializedName("timestamp")
    private long timestamp;
	
	@SerializedName("columnName")
    private String columnName;

	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------	
	
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
