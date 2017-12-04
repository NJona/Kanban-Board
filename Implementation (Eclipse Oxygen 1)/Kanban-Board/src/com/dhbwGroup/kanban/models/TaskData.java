
package com.dhbwGroup.kanban.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "task")
public class TaskData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskData(String columnName) {
    	this.id = UUID.randomUUID();
    	this.timestamp = System.currentTimeMillis();
    	this.changeLog = new ArrayList<TaskChangeLog>();
    	this.changeLog.add(new TaskChangeLog(columnName));
    }

    @SerializedName("uuid")
    private UUID id;

    @SerializedName("title")
    private String title = "Task";
    
    @SerializedName("content")
    private String description = "Description of the Task";
    
    @SerializedName("category")
    private UUID categoryUUID;
    
    @SerializedName("color")
    private String color = "C98E18FF";
    
    @SerializedName("timestamp")
    private long timestamp;
    
    @SerializedName("changeLog")
    private List<TaskChangeLog> changeLog;

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public UUID getCategoryUUID() {
		return categoryUUID;
	}

	public void setCategoryUUID(UUID categoryUUID) {
		this.categoryUUID = categoryUUID;
	}
	
    public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
    public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public List<TaskChangeLog> getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(List<TaskChangeLog> changeLog) {
		this.changeLog = changeLog;
	}

}
