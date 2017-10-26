
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
     * Is Column Active
     * <p>
     * 
     * 
     */
    @DatabaseField(columnName = "isActive", canBeNull = true, unique = true)
    @SerializedName("isActive")
    private boolean isActive = true;
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
    public ColumnData(String name, boolean isActive) {
        super();
        this.name = name;
        this.isActive = isActive;
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
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * The Name of the Column
     * <p>
     * 
     * 
     * @param name
     *     The name
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
