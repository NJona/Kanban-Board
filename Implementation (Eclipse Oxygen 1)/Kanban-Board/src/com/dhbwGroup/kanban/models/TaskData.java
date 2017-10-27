
package com.dhbwGroup.kanban.models;

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
    @SerializedName("id")
    private int id;
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
    @SerializedName("description")
    private String description = "Description of the Task";
    /**
     * The Priority of the Task
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "priority", canBeNull = true, unique = false)
    @SerializedName("priority")
    private String priority;
    
    /**
     * The ColumnIndex of the Task
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "columnIndex", canBeNull = false, unique = false)
    @SerializedName("columnIndex")
    private int columnIndex;

	/**
     * No args constructor for use in serialization
     * 
     */
    public TaskData() {
    }

    /**
     * 
     * @param description
     * @param title
     * @param priority
     */
    public TaskData(String title, String description, String priority) {
        super();
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    /**
     * The Priority of the Task
     * <p>
     * 
     * 
     * @return
     *     The priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * The Priority of the Task
     * <p>
     * 
     * 
     * @param priority
     *     The priority
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

}
