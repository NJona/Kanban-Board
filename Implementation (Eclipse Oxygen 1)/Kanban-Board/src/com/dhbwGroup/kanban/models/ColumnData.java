
package com.dhbwGroup.kanban.models;

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
    @SerializedName("id")
    private int id;
    /**
     * The Name of the Column
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "name", canBeNull = true, unique = true)
    @SerializedName("name")
    private String name = "Column";
    /**
     * Number Of Tasks
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "numberOfTasks", canBeNull = true, unique = true)
    @SerializedName("numberOfTasks")
    private int numberOfTasks = 0;
    /**
     * Number Of Tasks
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "maxTasks", canBeNull = true, unique = true)
    @SerializedName("maxTasks")
    private int maxTasks = 8;

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
    public ColumnData(String name, int numberOfTasks) {
        super();
        this.name = name;
        this.numberOfTasks = numberOfTasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
    }

    /**
     * The Name of the Column
     * <p>
     * 
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * The Name of the Column
     * <p>
     * 
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * The Name of the Column
     * <p>
     * 
     * 
     * @return
     *     The name
     */
    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    /**
     * The Name of the Column
     * <p>
     * 
     * 
     * @param name
     *     The name
     */
    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }
    public int getMaxTasks() {
		return maxTasks;
	}

	public void setMaxTasks(int maxTasks) {
		this.maxTasks = maxTasks;
	}

}
