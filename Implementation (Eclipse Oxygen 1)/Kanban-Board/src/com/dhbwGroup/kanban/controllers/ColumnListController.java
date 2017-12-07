package com.dhbwGroup.kanban.controllers;

import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.models.TaskChangeLog;

public class ColumnListController extends Controller{
	
	private List<ColumnController> columnControllers;
	
	private TaskListController taskListController;
	
	public ColumnListController(Project projectData) {
		super();
		this.setProjectData(projectData);
		columnControllers = new ArrayList<ColumnController>();
		taskListController = new TaskListController(projectData);
		initialize();
	}

	private void initialize() {
		if(!this.getProjectData().getColumnsData().isEmpty()) {
			this.getProjectData().getColumnsData().forEach(columnData -> {
				ColumnController columnControllerToAdd = new ColumnController(this.getProjectData(), columnData, taskListController);
				this.columnControllers.add(columnControllerToAdd);
			});	
			if(!taskListController.getTaskControllers().isEmpty()) {
				columnControllers.forEach((activeColumn) -> {
					activeColumn.getColumnHeaderController().getColumnData().getTaskUUIDs().forEach((taskUUIDToCompare) -> {
						taskListController.getTaskControllers().forEach((activeTaskController) -> {
							if(activeTaskController.getTaskData().getId().equals(taskUUIDToCompare)) {
								activeTaskController.setColumnData(activeColumn.getColumnHeaderController().getColumnData());
								if(activeTaskController.getTaskData().getChangeLog() == null) {
									activeTaskController.getTaskData().setChangeLog(new ArrayList<TaskChangeLog>());
									activeTaskController.getTaskData().getChangeLog().add(new TaskChangeLog(activeColumn.getColumnHeaderController().getColumnData().getName()));
								}else if(activeTaskController.getTaskData().getChangeLog().isEmpty()) {
									activeTaskController.getTaskData().getChangeLog().add(new TaskChangeLog(activeColumn.getColumnHeaderController().getColumnData().getName()));
								}
								activeColumn.getColumnTaskListController().getColumnTaskListView().getColumnTaskVBox().getChildren().add(activeTaskController.getTaskView().getTaskGridPane());
							}
						});
					});
		    	});			
			}
		}
	}
	
//Column User Interaction
	
	public ColumnController createNewColumn(String columnName) throws ColumnNotEmptyException, MinColumnsException{	
		ColumnData newColumnData = new ColumnData(columnName);
		this.getProjectData().getColumnsData().add(this.getProjectData().getColumnsData().size()-1, newColumnData);
		this.saveProject();
		return createNewColumnController(newColumnData);
	}
	
	private ColumnController createNewColumnController(ColumnData columnData) {
		ColumnController columnControllerToAdd = new ColumnController(this.getProjectData(), columnData, taskListController);
		this.columnControllers.add(columnControllers.size()-1, columnControllerToAdd);
		return columnControllerToAdd;
	}
	
	public ColumnController handleRemoveColumn(ColumnController columnToRemove) throws ColumnNotEmptyException, MinColumnsException{
		if(columnToRemove.getColumnHeaderController().getColumnData().getTaskUUIDs().isEmpty() && columnControllers.size() > ProjectController.MIN_COLUMNS)
		{
			this.getProjectData().getColumnsData().remove(columnToRemove.getColumnHeaderController().getColumnData());
			columnControllers.remove(columnToRemove);
			this.saveProject();
			return columnToRemove;				
		}else if(columnToRemove.getColumnHeaderController().getColumnData().getTaskUUIDs().size() == 0){
			throw new MinColumnsException();
		}else {
				throw new ColumnNotEmptyException();
		}
	}	
	
//addTask
	
	public boolean addTask() {
		ColumnController firstColumn = this.columnControllers.get(0);
		if(firstColumn.getColumnHeaderController().getColumnData().getTaskUUIDs().size() < firstColumn.getColumnHeaderController().getColumnData().getMaxTasks()) {
			TaskController taskControllerToAdd = this.taskListController.createNewTask(firstColumn.getColumnHeaderController().getColumnData().getName());
			taskControllerToAdd.setColumnData(firstColumn.getColumnHeaderController().getColumnData());
			firstColumn.getColumnHeaderController().getColumnData().getTaskUUIDs().add(taskControllerToAdd.getTaskData().getId());
			this.saveProject();
			firstColumn.getColumnTaskListController().getColumnTaskListView().getColumnTaskVBox().getChildren().add(taskControllerToAdd.getTaskView().getTaskGridPane());
			return false;
		}else {
			return true;
		}
	}

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------	

	public List<ColumnController> getColumnControllers() {
		return columnControllers;
	}

	public TaskListController getTaskListController() {
		return taskListController;
	}

	public void setColumnControllers(List<ColumnController> columnControllers) {
		this.columnControllers = columnControllers;
	}

	public void setTaskListController(TaskListController taskListController) {
		this.taskListController = taskListController;
	}
}
