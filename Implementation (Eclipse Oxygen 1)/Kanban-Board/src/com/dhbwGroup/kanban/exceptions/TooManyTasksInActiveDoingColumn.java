package com.dhbwGroup.kanban.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TooManyTasksInActiveDoingColumn extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooManyTasksInActiveDoingColumn() {
		System.err.println("Too Many Tasks in active doing Column!");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Can't change doing Column!");
		alert.setContentText("Can't change doing Column! There are too many tasks in active doing Column! Please move tasks and try again!");

		alert.showAndWait();
	}
}
