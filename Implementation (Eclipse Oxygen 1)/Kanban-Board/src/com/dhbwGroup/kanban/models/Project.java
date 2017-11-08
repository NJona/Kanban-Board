package com.dhbwGroup.kanban.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Project {
	
	public Project(List<ColumnData> columnsData) {
		this.source = 5;
		this.columnsData = columnsData;
		this.tasksData = new ArrayList<TaskData>();
		this.categoriesData = new ArrayList<CategoryData>();
	}
	
	@SerializedName("source")
    private int source;
	
	@SerializedName("items")
    private List<TaskData> tasksData;
	
	@SerializedName("columns")
    private List<ColumnData> columnsData;
	
	@SerializedName("categories")
    private List<CategoryData> categoriesData;
	
	//---------------------------------------------------------------------------
	//--------------------Getter and Setter--------------------------------------
	//---------------------------------------------------------------------------
	
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public List<TaskData> getTasksData() {
		return tasksData;
	}
	public void setTasksData(List<TaskData> tasksData) {
		this.tasksData = tasksData;
	}
	public List<ColumnData> getColumnsData() {
		return columnsData;
	}
	public void setColumnsData(List<ColumnData> columnsData) {
		this.columnsData = columnsData;
	}
	public List<CategoryData> getCategoriesData() {
		return categoriesData;
	}
	public void setCategoriesData(List<CategoryData> categoriesData) {
		this.categoriesData = categoriesData;
	}
}
