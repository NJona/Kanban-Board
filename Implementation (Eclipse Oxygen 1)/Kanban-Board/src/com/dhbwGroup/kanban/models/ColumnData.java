
package com.dhbwGroup.kanban.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "column")
public class ColumnData {

    public ColumnData() {
    	this.id = UUID.randomUUID();
    }

    public ColumnData(String name) {
        this();
        this.name = name;
    }
    
    public ColumnData(String name, int maxTasks) {
        this(name);
        this.maxTasks = maxTasks;
    }

    @SerializedName("uuid")
    private UUID id;

    @SerializedName("title")
    private String name = "Column";

    @SerializedName("items")
    private List<UUID> taskUUIDs = new ArrayList<UUID>();

    @SerializedName("capacity")
    private int maxTasks = 8;
    
    @SerializedName("type")
    private String type = "optional";
    
  //---------------------------------------------------------------------------
  //--------------------Getter and Setter--------------------------------------
  //---------------------------------------------------------------------------

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<UUID> getTaskUUIDs() {
		return taskUUIDs;
	}

	public void setTaskUUIDs(List<UUID> taskUUIDs) {
		this.taskUUIDs = taskUUIDs;
	}

	public int getMaxTasks() {
		return maxTasks;
	}

	public void setMaxTasks(int maxTasks) {
		this.maxTasks = maxTasks;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
