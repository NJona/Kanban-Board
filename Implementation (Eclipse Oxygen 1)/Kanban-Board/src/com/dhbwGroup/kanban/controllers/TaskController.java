package com.dhbwGroup.kanban.controllers;


import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.TaskData;

import com.dhbwGroup.kanban.views.Task;

import javafx.event.EventHandler;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class TaskController{
	
	private List<TaskData> tasksData;
	
	private List<Task> tasks;
	
	private CategoryController categoryController;
	
	public final static DataFormat taskDataFormat = new DataFormat("com.dhbwGroup.kanban.models.TaskData");

	public TaskController(CategoryController categoryController) {
		this.categoryController = categoryController;
		tasks = new ArrayList<Task>();
	}
	
	public void createTaskViews(List<TaskData> tasksData) {
		this.tasksData = tasksData;
		createTaskViewForEeachTaskData();
		createDragAndDropHandlerForTasks();
	}
	
	private void createTaskViewForEeachTaskData() {
		tasks = new ArrayList<Task>();
		if(!tasksData.isEmpty()) {
			tasksData.forEach((activeTask) -> {
				if(activeTask.getCategoryUUID() != null) {
					categoryController.getCategoryData().forEach((activeCategory) -> {
						if(activeTask.getCategoryUUID().equals(activeCategory.getId())) {
							tasks.add(new Task(activeTask, activeCategory, categoryController.getCategoryData()));
						}
					});					
				}else {
					tasks.add(new Task(activeTask, categoryController.getCategoryData()));	
				}
			});;			
		}
	}
	
	public Task addTask(){	
		return addTask(new TaskData());

	}
	
	public Task addTask(TaskData taskData) {
		Task taskToAdd;
		if(taskData.getCategoryUUID() != null) {
			taskToAdd = new Task(taskData, taskData.getCategoryUUID(), categoryController.getCategoryData());
		}else {
			taskToAdd = new Task(taskData, categoryController.getCategoryData());
		}
		taskToAdd.getTaskGridPane().setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
		        Dragboard db = taskToAdd.getTaskGridPane().startDragAndDrop(TransferMode.MOVE);
		        
		        //put UUID as Content
		        ClipboardContent content = new ClipboardContent();
		        content.put(taskDataFormat, taskToAdd.getTaskData());
		        db.setContent(content);
		        
		        mouseEvent.consume();
			}
			
		});
		tasks.add(taskToAdd);
		tasksData.add(taskData);
		return taskToAdd;		
	}

	
//---------------------------------------------------------------------------
//--------------------Drag and Drop Handler----------------------------------
//---------------------------------------------------------------------------
	
	private void createDragAndDropHandlerForTasks() {
		tasks.forEach((activeTask) -> {
			activeTask.getTaskGridPane().setOnDragDetected(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {
			        Dragboard db = activeTask.getTaskGridPane().startDragAndDrop(TransferMode.MOVE);
			        
			        //put UUID as Content
			        ClipboardContent content = new ClipboardContent();
			        content.put(taskDataFormat, activeTask.getTaskData());
			        db.setContent(content);
			        
			        mouseEvent.consume();
				}
				
			});
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
