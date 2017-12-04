package com.dhbwGroup.kanban.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MaxTasksAlreadyReachedException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.exceptions.TaskNotReusableException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.TaskChangeLog;
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

	public ColumnController(CategoryController categoryController) {
		taskController = new TaskController(categoryController);
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
				createNewColumnView(activeColumnData);
			});
		}
	}
	
	public Column createNewColumnDataAndColumnView(String columnName){	
		ColumnData newColumnData = new ColumnData(columnName);
		columnsData.add(columnsData.size()-1, newColumnData);
		return createNewColumnViewNextToLastColumn(newColumnData);
	}
	
	public Column createNewColumnViewNextToLastColumn(ColumnData columnData) {
		Column columnToAdd = new Column(columnData);
		columns.add(columns.size()-1, columnToAdd);
		createDragAndDropHandlerForColumnTaskVBox(columnToAdd);
		return columnToAdd;
	}

	public Column createNewColumnView(ColumnData columnData) {
		Column columnToAdd = new Column(columnData);
		columns.add(columnToAdd);
		createDragAndDropHandlerForColumnTaskVBox(columnToAdd);
		return columnToAdd;
	}
	
	public Column handleRemoveColumn(Column columnToRemove) throws ColumnNotEmptyException, MinColumnsException{
			if(columnToRemove.getColumnData().getTaskUUIDs().isEmpty() && columns.size() > Controller.MIN_COLUMNS)
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
//--------------Add Tasks to Gridpane---------------------
//--------------------------------------------------------
	
	
	public void addEachTaskViewToColumnView() {	//add to columnTaskGridpane
		if(!taskController.getTasks().isEmpty()) {
			columns.forEach((activeColumn) -> {
				activeColumn.getColumnData().getTaskUUIDs().forEach((taskUUIDToCompare) -> {
					taskController.getTasks().forEach((activeTask) -> {
						if(activeTask.getTaskData().getId().equals(taskUUIDToCompare)) {
							activeTask.setColumnData(activeColumn.getColumnData());
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
			Task taskToAdd = taskController.createNewTaskDataAndTaskView(column.getColumnData().getName());
			taskToAdd.setColumnData(column.getColumnData());
			column.getColumnData().getTaskUUIDs().add(taskToAdd.getTaskData().getId());
			column.getColumnTaskVBox().getColumnTaskVBox().getChildren().add(taskToAdd.getTaskGridPane());
		}
		return column.getColumnData().getTaskUUIDs().size() >= columns.get(0).getColumnData().getMaxTasks(); //reached max Tasks?
	}
	
//---------------------------------------------------------------------------
//--------------------Drag and Drop Handler----------------------------------
//---------------------------------------------------------------------------
	
	private void createDragAndDropHandlerForColumnTaskVBox(Column column) {
		column.getColumnTaskVBox().getColumnTaskVBox().setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard dragBoard = event.getDragboard();
				boolean success = false;
				if(dragBoard.hasContent(TaskController.TASK_DATA_FORMAT)) {
					if(hasColumnSpaceForAnotherTask(column)) {
						TaskData taskDataToMove = (TaskData) dragBoard.getContent(TaskController.TASK_DATA_FORMAT);
						ColumnData oldColumn = getOldColumnFromTask(taskDataToMove);
						if(oldColumn != null) {
							if(isNotLastColumn(oldColumn)) {
								column.getColumnData().getTaskUUIDs().add(taskDataToMove.getId());
								oldColumn.getTaskUUIDs().remove(taskDataToMove.getId());
								Task taskView = getTaskViewFromTaskData(taskDataToMove);
								if(taskView != null) {
									taskView.setColumnData(column.getColumnData());
									taskView.getTaskData().getChangeLog().add(new TaskChangeLog(column.getColumnData().getName()));
									column.getColumnTaskVBox().getColumnTaskVBox().getChildren().add(taskView.getTaskGridPane());
								}
								success = true;
							}else {
								success = false;
								try {
									throw new TaskNotReusableException();
								} catch (TaskNotReusableException e) {
									e.printStackTrace();
								}
							}
						}
					}else {
						success = false;
						try {
							throw new MaxTasksAlreadyReachedException();
						} catch (MaxTasksAlreadyReachedException e) {
							e.printStackTrace();
						}
					}
				}
				
		        event.setDropCompleted(success);
		        
		        event.consume();
			}	
		});
	}
	
	private boolean hasColumnSpaceForAnotherTask(Column column) {
		return column.getColumnData().getMaxTasks() > column.getColumnTaskVBox().getColumnTaskVBox().getChildren().size();
	}
	
	private boolean isNotLastColumn(ColumnData oldColumn) {
		return columnsData.indexOf(oldColumn) != columnsData.size()-1;
	}
	
	private ColumnData getOldColumnFromTask(TaskData taskDataToMove) {
		Iterator<ColumnData> columnDataIterator = columnsData.iterator();
		while(columnDataIterator.hasNext()) {
			ColumnData activeColumnData = columnDataIterator.next();
			if(!activeColumnData.getTaskUUIDs().isEmpty()) {
				Iterator<UUID> columnTasksUUIDIterator = activeColumnData.getTaskUUIDs().iterator();
				while(columnTasksUUIDIterator.hasNext()) {
					UUID taskUUID = columnTasksUUIDIterator.next();
					if(taskUUID.equals(taskDataToMove.getId()))
						return activeColumnData;
				}
			}
		}
		return null;
	}
	
	private Task getTaskViewFromTaskData(TaskData taskDataToMove) {
		Iterator<Task> taskIterator = taskController.getTasks().iterator();
		while(taskIterator.hasNext()) {
			Task activeTask = taskIterator.next();
			if(activeTask.getTaskData().getId().equals(taskDataToMove.getId()))
				return activeTask;
		}
		return null;
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
