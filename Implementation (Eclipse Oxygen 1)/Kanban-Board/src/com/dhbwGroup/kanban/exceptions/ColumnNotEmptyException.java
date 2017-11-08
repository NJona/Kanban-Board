package com.dhbwGroup.kanban.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ColumnNotEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ColumnNotEmptyException() {
		System.err.println("Column Not Emtpy! Please move all Tasks first!");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Column not empty!");
		alert.setContentText("Column not empty! Please move all Tasks first!");

		alert.showAndWait();
	}
}
