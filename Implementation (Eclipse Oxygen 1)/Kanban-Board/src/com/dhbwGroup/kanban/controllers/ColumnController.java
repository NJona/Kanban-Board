package com.dhbwGroup.kanban.controllers;

import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.views.Column;
import com.dhbwGroup.kanban.views.Task;

public class ColumnController {

	private List<ColumnData> columnsData;
	
	private List<Column> columns;
	
	private TaskController taskController;

	public ColumnController() {
		taskController = new TaskController();
		columns = new ArrayList<Column>();
	}
	
	public void createColumnViews(List<ColumnData> columnsData) {
		this.columnsData = columnsData;
		createColumnViewForEeachColumnData();
	}

	private void createColumnViewForEeachColumnData() {
		columns = new ArrayList<Column>();
		if(!columnsData.isEmpty()) {
			columnsData.forEach((activeColumnData) -> {
				columns.add(new Column(activeColumnData));
			});
		}
	}
	
	public Column addColumn(String columnName){	
		ColumnData newColumnData = new ColumnData();
		Column columnToAdd = new Column(newColumnData);
		columns.add(columns.size()-1, columnToAdd);
		columnsData.add(columnsData.size()-1, newColumnData);
		return columnToAdd;
	}
	
	public Column handleRemoveColumn(Column columnToRemove) throws ColumnNotEmptyException, MinColumnsException{
			if(columnToRemove.getColumnData().getTaskUUIDs().isEmpty() && columns.size() > 3)
			{
				columnsData.remove(columnToRemove.getColumnData());
				columns.remove(columnToRemove);
				return columnToRemove;				
			}else if(columnToRemove.getColumnData().getTaskUUIDs().size() == 0){
				throw new MinColumnsException();
			}else {
					throw new ColumnNotEmptyException();
			}
	}

	
//--------------Add Tasks zu Gridpane---------------------
	
	
	public void addEachTaskViewToColumnView() {	//add to columnTaskGridpane
		if(!taskController.getTasks().isEmpty()) {
			columns.forEach((activeColumn) -> {
				activeColumn.getColumnData().getTaskUUIDs().forEach((taskUUIDToCompare) -> {
					taskController.getTasks().forEach((activeTask) -> {
						if(activeTask.getTaskData().getId().equals(taskUUIDToCompare)) {
							//"Add " + taskUUIDToCompare + "to Column " + columns.indexOf(activeColumn)
							activeColumn.getColumnTaskGridPane().getColumnTaskGridPane().add(activeTask.getTaskGridPane(), 0, activeColumn.getColumnData().getTaskUUIDs().indexOf(taskUUIDToCompare));
						}
					});
				});
	    	});			
		}
	}
    
	
	public boolean addTask() {
		Column column = columns.get(0);
		if(column.getColumnData().getTaskUUIDs().size() < columns.get(0).getColumnData().getMaxTasks()) {
			Task taskToAdd = taskController.addTask();
			column.getColumnData().getTaskUUIDs().add(taskToAdd.getTaskData().getId());
			column.getColumnTaskGridPane().getColumnTaskGridPane().add(taskToAdd.getTaskGridPane(), 0, column.getColumnData().getTaskUUIDs().indexOf(taskToAdd.getTaskData().getId()));
		}
		return column.getColumnData().getTaskUUIDs().size() >= columns.get(0).getColumnData().getMaxTasks(); //reached max Tasks?
	}
//-------------------------------------------------------------
	
	//Getter and Setter Methods
	
	public TaskController getTaskController() {
		return taskController;
	}

	public void setTaskController(TaskController taskController) {
		this.taskController = taskController;
	}
	
	public List<ColumnData> getColumnsData() {
		return columnsData;
	}

	public void setColumnsData(List<ColumnData> columnsData) {
		this.columnsData = columnsData;
	}
	
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
