package com.dhbwGroup.kanban.views;

import com.dhbwGroup.kanban.models.TaskData;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Task {
	private TaskData taskData;
	
	private GridPane taskGridPane;
	
	private Text titleLabel;
	private Text descriptionLabel;
	private Text priorityLabel;
	
	public Task(TaskData taskData) {
		this.taskData = taskData;
		
		titleLabel = new Text(this.taskData.getTitle());
		descriptionLabel = new Text(this.taskData.getDescription());
		priorityLabel = new Text(this.taskData.getPriority());
		
		taskGridPane = new GridPane();
		
		addNodesToGridPane();
		addStyleClasses();
		
	}

	private void addStyleClasses() {
		this.taskGridPane.getStyleClass().add("task");
		this.titleLabel.getStyleClass().add("taskTitle");
		this.descriptionLabel.getStyleClass().add("taskDescription");
		this.priorityLabel.getStyleClass().add("taskPriority");
	}

	private void addNodesToGridPane() {
		taskGridPane.add(titleLabel, 0, 0);
		taskGridPane.add(descriptionLabel, 0, 1);
		taskGridPane.add(priorityLabel, 0, 2);
	}

	public TaskData getTaskData() {
		return taskData;
	}

	public void setTaskData(TaskData taskData) {
		this.taskData = taskData;
	}

	public GridPane getTaskGridPane() {
		return taskGridPane;
	}

	public void setTaskGridPane(GridPane taskGridPane) {
		this.taskGridPane = taskGridPane;
	}

	public Text getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(Text titleLabel) {
		this.titleLabel = titleLabel;
	}

	public Text getDescriptionLabel() {
		return descriptionLabel;
	}

	public void setDescriptionLabel(Text descriptionLabel) {
		this.descriptionLabel = descriptionLabel;
	}

	public Text getPriorityLabel() {
		return priorityLabel;
	}

	public void setPriorityLabel(Text priorityLabel) {
		this.priorityLabel = priorityLabel;
	}
}
