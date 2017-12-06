package com.dhbwGroup.kanban.views;

import javafx.scene.layout.VBox;

public class ColumnTaskListView {

	private VBox columnTaskVBox;
	
	public ColumnTaskListView() {
		columnTaskVBox = new VBox();
		initialize();
	}
	
	private void initialize() {
		columnTaskVBox.setMinHeight(900);
		columnTaskVBox.setMinWidth(450);
		columnTaskVBox.setPrefWidth(450);
		columnTaskVBox.setMaxWidth(450);
		columnTaskVBox.getStyleClass().add("columnTaskVBox");		
	}
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------

	public VBox getColumnTaskVBox() {
		return columnTaskVBox;
	}

	public void setColumnTaskVBox(VBox columnTaskVBox) {
		this.columnTaskVBox = columnTaskVBox;
	}
}
