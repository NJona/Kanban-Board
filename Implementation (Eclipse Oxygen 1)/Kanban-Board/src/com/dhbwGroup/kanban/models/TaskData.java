
package com.dhbwGroup.kanban.models;

import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "task")
public class TaskData {

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
     * The Title of the Task
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "title", canBeNull = true, unique = false)
    @SerializedName("title")
    private String title = "Task";
    /**
     * The Description of the Task
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "description", canBeNull = true, unique = false)
    @SerializedName("content")
    private String description = "Description of the Task";
    
    @SerializedName("category")
    private UUID categoryUUID;

	public UUID getCategoryUUID() {
		return categoryUUID;
	}

	public void setCategoryUUID(UUID categoryUUID) {
		this.categoryUUID = categoryUUID;
	}

	/**
     * No args constructor for use in serialization
     * 
     */
    public TaskData() {
    	this.id = UUID.randomUUID();
    }



    /**
     * The Title of the Task
     * <p>
     * 
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The Title of the Task
     * <p>
     * 
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The Description of the Task
     * <p>
     * 
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * The Description of the Task
     * <p>
     * 
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
