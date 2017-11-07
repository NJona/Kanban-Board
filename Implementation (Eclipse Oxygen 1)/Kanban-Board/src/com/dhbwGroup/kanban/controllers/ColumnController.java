package com.dhbwGroup.kanban.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.services.KanbanService;
import com.dhbwGroup.kanban.views.Column;
import com.dhbwGroup.kanban.views.Task;

public class ColumnController {

	private List<ColumnData> columnsData;
	private KanbanService kanbanService;
	
	private List<Column> columns = new ArrayList<Column>();
	
	private TaskController taskController;

	public ColumnController() {
		kanbanService = new KanbanService();
		columnsData = kanbanService.loadColumnsFromDB();
		createColumnViewForEeachColumnData();
		taskController = new TaskController();
		addEachTaskViewToBoardGridpane();
	}

	private void createColumnViewForEeachColumnData() {
		if(!columnsData.isEmpty()) {
			columnsData.forEach((activeColumnData) -> {
				activeColumnData.setNumberOfTasks(0);
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
			if(columnToRemove.getColumnData().getNumberOfTasks() == 0 && columns.size() > 3)
			{
				columnsData.remove(columnToRemove.getColumnData());
				columns.remove(columnToRemove);
				return columnToRemove;				
			}else if(columnToRemove.getColumnData().getNumberOfTasks() == 0){
				throw new MinColumnsException();
			}else {
					throw new ColumnNotEmptyException();
			}
	}
	
//------------------Save Kanban-Board-----------------------------------------
	
	
	public void updateDataBase() {
		try {
			kanbanService.saveColumnsToDB(columnsData);
		} catch (IOException e) {
			System.err.println("ColumnController.updateDataBase()");
			System.err.println("Can't update Database");
		}
	}
	
	
//--------------Add Tasks zu Gridpane---------------------
	
	
	private void addEachTaskViewToBoardGridpane() {	//add to columnTaskGridpane
		if(!taskController.getTasks().isEmpty()) {
			taskController.getTasks().forEach((activeTask) -> {
				Column columnToAdd = columns.get(activeTask.getTaskData().getColumnIndex());
	    		int rowIndex = getRowIndex(columnToAdd);
	    		columnToAdd.getColumnTaskGridPane().getColumnTaskGridPane().add(activeTask.getTaskGridPane(), 0, rowIndex);
	    		setNewRowIndex(columnToAdd, rowIndex);
	    	});			
		}
	}
	
    private void setNewRowIndex(Column columnToAdd, int rowIndex) {
    	columnToAdd.getColumnData().setNumberOfTasks(rowIndex + 1);
		
	}

	private int getRowIndex(Column columnToAdd) {
		return columnToAdd.getColumnData().getNumberOfTasks();
    }
    
	
	public boolean addTask() {
		Column column = columns.get(0);
		if(column.getColumnData().getNumberOfTasks() < columns.get(0).getColumnData().getMaxTasks()) {
			Task taskToAdd = taskController.addTask(columns.indexOf(column));
			int rowIndex = getRowIndex(columns.get(0));
			column.getColumnTaskGridPane().getColumnTaskGridPane().add(taskToAdd.getTaskGridPane(), 0, rowIndex);
			setNewRowIndex(column, rowIndex);
		}
		return column.getColumnData().getNumberOfTasks() >= columns.get(0).getColumnData().getMaxTasks(); //reached max Tasks?
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
