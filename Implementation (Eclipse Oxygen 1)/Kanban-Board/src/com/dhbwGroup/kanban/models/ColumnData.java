
package com.dhbwGroup.kanban.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "column")
public class ColumnData {

    /**
     * This is compulsory for both the database and to access models
     * 
     */
    @DatabaseField(generatedId = true, columnName = "id")
    @SerializedName("uuid")
    private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	/**
     * The Name of the Column
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "name", canBeNull = true, unique = true)
    @SerializedName("title")
    private String name = "Column";
    /**
     * List Of TaskIDs
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "numberOfTasks", canBeNull = true, unique = true)
    @SerializedName("items")
    private List<UUID> taskUUIDs = new ArrayList<UUID>();
    
	public List<UUID> getTaskUUIDs() {
		return taskUUIDs;
	}

	public void setTaskUUIDs(List<UUID> taskUUIDs) {
		this.taskUUIDs = taskUUIDs;
	}
	/**
     * Number Of Tasks
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "maxTasks", canBeNull = true, unique = true)
    @SerializedName("capacity")
    private int maxTasks = 8;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxTasks() {
		return maxTasks;
	}

	public void setMaxTasks(int maxTasks) {
		this.maxTasks = maxTasks;
	}

	/**
     * No args constructor for use in serialization
     * 
     */
    public ColumnData() {
    }

    /**
     * 
     * @param name
     */
    public ColumnData(String name) {
        super();
        this.name = name;
    }
    public ColumnData(String name, int maxTasks) {
        super();
        this.name = name;
        this.maxTasks = maxTasks;
    }


}
