package com.dhbwGroup.kanban.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TaskNotReusableException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskNotReusableException() {
		System.err.println("Can't reuse Task!");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Can't reuse Task!");
		alert.setContentText("Can't reuse done Task!");

		alert.showAndWait();
	}
}
