package com.dhbwGroup.kanban.views;

import javafx.scene.layout.VBox;

public class ColumnView {

	private VBox columnVBox;
	
	public ColumnView() {
		columnVBox = new VBox();
		initialize();
	}

	private void initialize() {
		this.columnVBox.getStyleClass().add("columnVBox");
	}
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------

	public VBox getColumnVBox() {
		return columnVBox;
	}

	public void setColumnVBox(VBox columnVBox) {
		this.columnVBox = columnVBox;
	}
}
