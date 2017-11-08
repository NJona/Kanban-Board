package com.dhbwGroup.kanban.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MinColumnsException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MinColumnsException() {
		System.err.println("Only 3 Columns left, can't delete more!");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Only 3 Columns left!");
		alert.setContentText("Can't delete more Columns! 3 are minimum!");

		alert.showAndWait();
	}
}
