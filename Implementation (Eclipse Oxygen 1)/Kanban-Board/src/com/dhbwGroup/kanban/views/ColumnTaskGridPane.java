package com.dhbwGroup.kanban.views;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ColumnTaskGridPane {

	private GridPane columnTaskGridPane;

	private RowConstraints rowConstraints;
	
	public ColumnTaskGridPane() {
		initialize();
	}
	
	
	private void initialize() {
		columnTaskGridPane = new GridPane();
		columnTaskGridPane.getStyleClass().add("columnTaskGridPane");
		rowConstraints = new RowConstraints();
		rowConstraints.setPercentHeight(90);
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(100);
		columnTaskGridPane.getColumnConstraints().add(cc);
	}


	// Getter and Setter
	public GridPane getColumnTaskGridPane() {
		return columnTaskGridPane;
	}

	public void setColumnTaskGridPane(GridPane columnTaskGridPane) {
		this.columnTaskGridPane = columnTaskGridPane;
	}

	public RowConstraints getRowConstraints() {
		return rowConstraints;
	}

	public void setRowConstraints(RowConstraints rowConstraints) {
		this.rowConstraints = rowConstraints;
	}

}
