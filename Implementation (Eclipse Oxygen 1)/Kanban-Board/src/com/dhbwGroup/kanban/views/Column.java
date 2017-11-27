package com.dhbwGroup.kanban.views;

import com.dhbwGroup.kanban.models.ColumnData;

import javafx.scene.layout.VBox;

public class Column {
	private ColumnData columnData;
	
	private VBox columnVBox;
	
	private ColumnHeaderGridPane columnHeaderGridPane;

	private ColumnTaskVBox columnTaskVBox;
	
	public Column(ColumnData columnData) {
		this.columnData = columnData;
		initialize();
	}
	
	public void initialize() {
		columnVBox = new VBox();
		
		columnHeaderGridPane = new ColumnHeaderGridPane(columnData);
		
		columnTaskVBox = new ColumnTaskVBox();
		columnTaskVBox.getColumnTaskVBox().setId(columnData.getId().toString());
		
		addNodesToGridPane();
		addStyleClasses();
	}

	private void addStyleClasses() {
		this.columnVBox.getStyleClass().add("columnVBox");
	}

	private void addNodesToGridPane() {
		this.columnVBox.getChildren().add(columnHeaderGridPane.getColumnHeaderGridPane());
		this.columnVBox.getChildren().add(columnTaskVBox.getColumnTaskVBox());
	}

	
	  //---------------------------------------------------------------------------
	  //--------------------Getter and Setter--------------------------------------
	  //---------------------------------------------------------------------------
	
	
	public ColumnHeaderGridPane getColumnHeaderGridPane() {
		return columnHeaderGridPane;
	}

	public void setColumnHeaderGridPane(ColumnHeaderGridPane columnHeaderGridPane) {
		this.columnHeaderGridPane = columnHeaderGridPane;
	}

	public ColumnTaskVBox getColumnTaskVBox() {
		return columnTaskVBox;
	}

	public void setColumnTaskVBox(ColumnTaskVBox columnTaskVBox) {
		this.columnTaskVBox = columnTaskVBox;
	}
	
	public ColumnData getColumnData() {
		return columnData;
	}

	public void setColumnData(ColumnData columnData) {
		this.columnData = columnData;
	}

	public VBox getColumnGridPane() {
		return columnVBox;
	}

	public void setColumnGridPane(VBox columnVBox) {
		this.columnVBox = columnVBox;
	}
	
}
