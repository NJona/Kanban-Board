package com.dhbwGroup.kanban.controllers;


import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.services.KanbanService;
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
	
	private KanbanService kanbanService;
	
	private Project project;
	
	public final static DataFormat TASK_DATA_FORMAT = new DataFormat("com.dhbwGroup.kanban.models.TaskData");

	public TaskController(CategoryController categoryController, KanbanService kanbanService, Project project) {
		this.categoryController = categoryController;
		this.kanbanService = kanbanService;
		this.project = project;
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
	
	public Task createNewTaskDataAndTaskView(String columnName){	
		TaskData newTaskData = new TaskData(columnName);
		if(tasksData.size() < Controller.MAX_TASKS_IN_HISTORY) {
			tasksData.add(newTaskData);
		}else{
			tasksData.remove(0);
			tasksData.add(newTaskData);
		}
		kanbanService.saveProject(project);
		return createNewTaskView(newTaskData);

	}
	
	public Task createNewTaskView(TaskData taskData) {
		Task taskToAdd;
		if(taskData.getCategoryUUID() != null) {
			taskToAdd = new Task(taskData, taskData.getCategoryUUID(), categoryController.getCategoryData(), kanbanService, project);
		}else {
			taskToAdd = new Task(taskData, categoryController.getCategoryData(), kanbanService, project);
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
				kanbanService.saveProject(project);
				if(taskToAdd.getColumnData() != null) {
					taskToAdd.getColumnData().getTaskUUIDs().remove(taskToAdd.getTaskData().getId());
					((VBox) taskToAdd.getTaskGridPane().getParent()).getChildren().remove(taskToAdd.getTaskGridPane());
				}else {
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
					kanbanService.saveProject(project);
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
