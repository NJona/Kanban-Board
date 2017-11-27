package com.dhbwGroup.kanban.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MaxTasksAlreadyReachedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaxTasksAlreadyReachedException() {
		System.err.println("Number of Tasks per Column already reached maximum!");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Too many Tasks in Column!");
		alert.setContentText("There are already too many Tasks in the Column! Please move other Tasks first!");

		alert.showAndWait();
	}
}
