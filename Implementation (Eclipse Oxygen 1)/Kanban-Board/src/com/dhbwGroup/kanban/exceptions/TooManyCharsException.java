package com.dhbwGroup.kanban.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TooManyCharsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooManyCharsException() {
		System.err.println("Can't change Name!");
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Can't change Name!");
		alert.setContentText("Can't change Name! Name is too long. You can only use 15 Characters.");

		alert.showAndWait();
	}
}
