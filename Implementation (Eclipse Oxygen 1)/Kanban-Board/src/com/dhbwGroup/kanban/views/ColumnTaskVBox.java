package com.dhbwGroup.kanban.views;

import javafx.scene.layout.VBox;

public class ColumnTaskVBox {

	private VBox columnTaskVBox;
	
	public ColumnTaskVBox() {
		initialize();
	}
	
	
	private void initialize() {
		columnTaskVBox = new VBox();
		columnTaskVBox.getStyleClass().add("columnTaskGridPane");
	}


	// Getter and Setter
	public VBox getColumnTaskVBox() {
		return columnTaskVBox;
	}

	public void setColumnTaskVBox(VBox columnTaskVBox) {
		this.columnTaskVBox = columnTaskVBox;
	}
}
