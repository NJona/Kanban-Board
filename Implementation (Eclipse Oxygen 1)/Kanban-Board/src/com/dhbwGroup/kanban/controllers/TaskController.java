package com.dhbwGroup.kanban.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.services.KanbanService;
import com.dhbwGroup.kanban.views.Task;

public class TaskController{
	
	private List<TaskData> tasksData;
	private KanbanService kanbanService;
	
	private List<Task> tasks = new ArrayList<Task>();

	public TaskController() {
		kanbanService = new KanbanService();
		tasksData = kanbanService.loadTasksFromDB();
		createTaskViewForEeachTaskData();
	}
	
	private void createTaskViewForEeachTaskData() {
		if(!tasksData.isEmpty()) {
			tasksData.forEach((activeTask) -> {
				tasks.add(new Task(activeTask));
			});;			
		}
	}
	
	public Task addTask(int columnIndex){	
		TaskData taskData = new TaskData();
		taskData.setColumnIndex(columnIndex);
		Task taskToAdd = new Task(taskData);
		tasks.add(taskToAdd);
		tasksData.add(taskData);
		return taskToAdd;
	}
	
	public void updateDataBase() {
		try {
			kanbanService.saveTasksToDB(tasksData);
		} catch (IOException e) {
			System.err.println("ColumnController.updateDataBase()");
			System.err.println("Can't update Database");
		}
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
