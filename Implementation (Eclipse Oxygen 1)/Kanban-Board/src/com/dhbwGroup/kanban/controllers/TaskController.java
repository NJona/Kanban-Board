package com.dhbwGroup.kanban.controllers;


import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.TaskData;

import com.dhbwGroup.kanban.views.Task;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class TaskController{
	
	private List<TaskData> tasksData;
	
	private List<Task> tasks;
	
	private CategoryController categoryController;
	
	public final static DataFormat TASK_DATA_FORMAT = new DataFormat("com.dhbwGroup.kanban.models.TaskData");

	public TaskController(CategoryController categoryController) {
		this.categoryController = categoryController;
		tasks = new ArrayList<Task>();
	}
	
	public void createTaskViews(List<TaskData> tasksData) {
		this.tasksData = tasksData;
		tasks = new ArrayList<Task>();
		if(!tasksData.isEmpty()) {
			tasksData.forEach((activeTaskData) -> {
				createNewTaskView(activeTaskData);
			});			
		}
	}
	
	public Task createNewTaskDataAndTaskView(){	
		TaskData newTaskData = new TaskData();
		tasksData.add(newTaskData);
		return createNewTaskView(newTaskData);

	}
	
	public Task createNewTaskView(TaskData taskData) {
		Task taskToAdd;
		if(taskData.getCategoryUUID() != null) {
			taskToAdd = new Task(taskData, taskData.getCategoryUUID(), categoryController.getCategoryData());
		}else {
			taskToAdd = new Task(taskData, categoryController.getCategoryData());
		}
		createDragAndDropHandlerForTask(taskToAdd);
		createArchiveHandlerForTask(taskToAdd);
		createDeleteHandlerForTask(taskToAdd);
		tasks.add(taskToAdd);
		return taskToAdd;		
	}

	
//---------------------------------------------------------------------------
//--------------------Drag and Drop Handler----------------------------------
//---------------------------------------------------------------------------
	
	private void createDeleteHandlerForTask(Task taskToAdd) {
		taskToAdd.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				tasksData.remove(taskToAdd.getTaskData());
				if(taskToAdd.getColumnData() != null) {
					taskToAdd.getColumnData().getTaskUUIDs().remove(taskToAdd.getTaskData().getId());
					((VBox) taskToAdd.getTaskGridPane().getParent()).getChildren().remove(taskToAdd.getTaskGridPane());
				}
			}
			
		});
		
	}

	private void createArchiveHandlerForTask(Task taskToAdd) {
		taskToAdd.getArchiveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(taskToAdd.getColumnData() != null) {
					taskToAdd.getColumnData().getTaskUUIDs().remove(taskToAdd.getTaskData().getId());
					((VBox) taskToAdd.getTaskGridPane().getParent()).getChildren().remove(taskToAdd.getTaskGridPane());
				}
			}
			
		});	
	}

	private void createDragAndDropHandlerForTask(Task task) {
		task.getTaskGridPane().setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
		        Dragboard db = task.getTaskGridPane().startDragAndDrop(TransferMode.MOVE);
		        
		        ClipboardContent content = new ClipboardContent();
		        content.put(TASK_DATA_FORMAT, task.getTaskData());
		        db.setContent(content);
		        
		        mouseEvent.consume();
			}
			
		});
	}
	
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public List<TaskData> getTasksData() {
		return tasksData;
	}

	public void setTasksData(List<TaskData> tasksData) {
		this.tasksData = tasksData;
	}	
	
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public CategoryController getCategoryController() {
		return categoryController;
	}

	public void setCategoryController(CategoryController categoryController) {
		this.categoryController = categoryController;
	}
}
