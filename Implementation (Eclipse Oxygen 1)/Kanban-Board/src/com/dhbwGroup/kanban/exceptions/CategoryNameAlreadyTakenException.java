package com.dhbwGroup.kanban.exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CategoryNameAlreadyTakenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CategoryNameAlreadyTakenException() {
		System.err.println("Category Name already taken! Please choose another one!");
		/*Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Category Name already taken!");
		alert.setContentText("Category Name already taken! Please choose another one!");

		alert.showAndWait();
		*/
	}
}
