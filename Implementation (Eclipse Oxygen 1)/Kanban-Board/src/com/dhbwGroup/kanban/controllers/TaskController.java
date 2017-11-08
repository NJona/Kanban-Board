package com.dhbwGroup.kanban.controllers;


import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.TaskData;

import com.dhbwGroup.kanban.views.Task;

public class TaskController{
	
	private List<TaskData> tasksData;
	
	private List<Task> tasks;

	public TaskController() {
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
				tasks.add(new Task(activeTask));
			});;			
		}
	}
	
	public Task addTask(){	
		TaskData taskData = new TaskData();
		Task taskToAdd = new Task(taskData);
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
