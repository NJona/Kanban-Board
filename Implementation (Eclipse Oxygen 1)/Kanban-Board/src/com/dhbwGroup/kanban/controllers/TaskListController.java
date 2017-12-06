package com.dhbwGroup.kanban.controllers;

import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.models.TaskData;

public class TaskListController extends Controller{

	private List<TaskController> taskControllers;
	
	public TaskListController(Project projectData) {
		super();
		this.setProjectData(projectData);
		this.taskControllers = new ArrayList<TaskController>();
		initialize();
	}

	private void initialize() {
		if(!this.getProjectData().getTasksData().isEmpty()) {
			this.getProjectData().getTasksData().forEach(taskData -> {
				TaskController taskControllerToAdd = new TaskController(this.getProjectData(), taskData);
				this.taskControllers.add(taskControllerToAdd);
			});			
		}
	}
	
//Task User Interaction
	
	public TaskController createNewTask(String columnNameInWhichTaskIsAdded) {	
		TaskData newTaskData = new TaskData(columnNameInWhichTaskIsAdded);
		if(this.getProjectData().getTasksData().size() < ProjectController.MAX_TASKS_IN_HISTORY) {
			this.getProjectData().getTasksData().add(newTaskData);
		}else{
			this.getProjectData().getTasksData().remove(0);
			this.getProjectData().getTasksData().add(newTaskData);
		}
		this.saveProject();
		return createNewTaskController(newTaskData);
	}
	
	public TaskController createNewTaskController(TaskData taskData) {
		TaskController taskToAdd = new TaskController(this.getProjectData(), taskData);
		this.taskControllers.add(taskToAdd);
		return taskToAdd;		
	}

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public List<TaskController> getTaskControllers() {
		return taskControllers;
	}

	public void setTaskControllers(List<TaskController> taskControllers) {
		this.taskControllers = taskControllers;
	}

}
