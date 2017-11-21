package com.dhbwGroup.kanban.controllers;


import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.TaskData;

import com.dhbwGroup.kanban.views.Task;

public class TaskController{
	
	private List<TaskData> tasksData;
	
	private List<Task> tasks;
	
	private CategoryController categoryController;

	public TaskController(CategoryController categoryController) {
		this.categoryController = categoryController;
		tasks = new ArrayList<Task>();
	}
	
	public void createTaskViews(List<TaskData> tasksData) {
		this.tasksData = tasksData;
		createTaskViewForEeachTaskData();
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
		TaskData taskData = new TaskData();
		Task taskToAdd = new Task(taskData, categoryController.getCategoryData());
		tasks.add(taskToAdd);
		tasksData.add(taskData);
		return taskToAdd;
	}

	//Getter and Setter Methods
	
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
}
