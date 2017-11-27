package com.dhbwGroup.kanban.controllers;

import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MaxTasksAlreadyReachedException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.exceptions.TaskNotReusableException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.views.Column;
import com.dhbwGroup.kanban.views.Task;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

public class ColumnController {

	private List<ColumnData> columnsData;
	
	private List<Column> columns;
	
	private TaskController taskController;
	
	private ColumnData taskUUIDToRemove;

	public ColumnController(CategoryController categoryController) {
		taskController = new TaskController(categoryController);
		columns = new ArrayList<Column>();
	}
	
	public void createColumnViews(List<ColumnData> columnsData) {
		this.columnsData = columnsData;
		createColumnViewForEeachColumnData();
		createDragAndDropHandler();
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
		ColumnData newColumnData = new ColumnData(columnName);
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

//--------------------------------------------------------
//--------------Add Tasks zu Gridpane---------------------
//--------------------------------------------------------
	
	
	public void addEachTaskViewToColumnView() {	//add to columnTaskGridpane
		if(!taskController.getTasks().isEmpty()) {
			columns.forEach((activeColumn) -> {
				activeColumn.getColumnData().getTaskUUIDs().forEach((taskUUIDToCompare) -> {
					taskController.getTasks().forEach((activeTask) -> {
						if(activeTask.getTaskData().getId().equals(taskUUIDToCompare)) {
							//"Add " + taskUUIDToCompare + "to Column " + columns.indexOf(activeColumn)
							activeColumn.getColumnTaskVBox().getColumnTaskVBox().getChildren().add(activeTask.getTaskGridPane());
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
			column.getColumnTaskVBox().getColumnTaskVBox().getChildren().add(taskToAdd.getTaskGridPane());
		}
		return column.getColumnData().getTaskUUIDs().size() >= columns.get(0).getColumnData().getMaxTasks(); //reached max Tasks?
	}
	
//---------------------------------------------------------------------------
//--------------------Drag and Drop Handler----------------------------------
//---------------------------------------------------------------------------
	

	private void createDragAndDropHandler() {
		this.columns.forEach((activeColumn) -> {
			activeColumn.getColumnTaskVBox().getColumnTaskVBox().setOnDragDropped(new EventHandler<DragEvent>() {
			    public void handle(DragEvent event) {
			        /* data dropped */
			        /* if there is a string data on dragboard, read it and use it */
			        Dragboard db = event.getDragboard();
			        boolean success = false;
			        if (db.hasContent(TaskController.taskDataFormat)) {
			        	if(activeColumn.getColumnData().getMaxTasks() > activeColumn.getColumnTaskVBox().getColumnTaskVBox().getChildren().size()) {
				        	System.out.println("DragBoard has Content");
				        	TaskData taskDataToMove = (TaskData) db.getContent(TaskController.taskDataFormat);
				        	columnsData.forEach(activeColumnData -> {
				        		System.out.println("ColumnUUID:" + activeColumnData.getId());
				        		if(!activeColumnData.getTaskUUIDs().isEmpty()) {
					        		activeColumnData.getTaskUUIDs().forEach((activeTaskUUID) -> {
					        			System.out.println("TaskUUID:" + activeTaskUUID);
					        			if(activeTaskUUID.equals(taskDataToMove.getId())) {
					        				System.out.println("activeTaskUUID equals taskDataToMove");
					        				taskUUIDToRemove = activeColumnData;
					        			}
					        			System.out.println("removed UUID");
					        		});
					        		System.out.println("All Task UUIDs from column compared");
				        		}else {
				        			System.out.println("TaskUUIDs are empty");
				        		}
				        		System.out.println("TaskUUIDs from column compared");
				        	});
				        	if(columnsData.indexOf(taskUUIDToRemove) == columnsData.size()-1) {
				        		try {
									throw new TaskNotReusableException();
								} catch (TaskNotReusableException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				        	}else {
					        	taskUUIDToRemove.getTaskUUIDs().remove(taskDataToMove.getId());
					        	
					        	System.out.println("Task UUID removed from old column");
					        	activeColumn.getColumnData().getTaskUUIDs().add(taskDataToMove.getId());
					        	System.out.println(taskController.getTasks().size());
					        	taskController.getTasks().forEach(activeTask -> {
					        		System.out.println(activeTask.getTaskData().getId());
					        		if(activeTask.getTaskData().getId().equals(taskDataToMove.getId())) {
					        			System.out.println("Task with correct TaskId found:" + activeTask.getTaskData().getId());
					        			activeColumn.getColumnTaskVBox().getColumnTaskVBox().getChildren().add(activeTask.getTaskGridPane());
					        		}else {
					        			System.out.println(activeTask.getTaskData().getId());
					        			System.out.println(taskDataToMove.getId());
					        		}
					        	});
					        	
					            success = true;				        		
				        	}
			        	}else {
			        		success = false;
			        		try {
								throw new MaxTasksAlreadyReachedException();
							} catch (MaxTasksAlreadyReachedException e) {
								
							}
			        	}

			        }
			        /* let the source know whether the string was successfully 
			         * transferred and used */
			        event.setDropCompleted(success);
			        
			        event.consume();
			     }
			});
		});
	}	
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
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
