package com.dhbwGroup.kanban.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ColumnHeaderGridPane {
	private GridPane columnHeaderGridPane;

	private ColumnGridPaneElementText columnGridPaneElementText;
	private ColumnGridPaneElementButton columnGridPaneElementEditButton;
	private ColumnGridPaneElementButton columnGridPaneElementRemoveButton;

	private RowConstraints rowConstraints;

	public ColumnHeaderGridPane(String columnName) {
		columnHeaderGridPane = new GridPane();
		
		columnGridPaneElementText = new ColumnGridPaneElementText(columnName, 60);
		columnGridPaneElementEditButton = new ColumnGridPaneElementButton("Edit", 20);
		columnGridPaneElementRemoveButton = new ColumnGridPaneElementButton("Remove", 20);
		
		
		initialize();
	}

	private void initialize() {
		setColumnGridPaneConstraints();
		rowConstraints = new RowConstraints();
		rowConstraints.setPercentHeight(10);
		this.columnHeaderGridPane.add(columnGridPaneElementText.getColumnTextLabel(), 0, 0);
		this.columnHeaderGridPane.add(columnGridPaneElementText.getColumnTextField(), 0, 0);
		this.columnHeaderGridPane.add(columnGridPaneElementEditButton.getButton(), 1, 0);
		this.columnHeaderGridPane.add(columnGridPaneElementRemoveButton.getButton(), 2, 0);
		this.columnHeaderGridPane.getStyleClass().add("columnHeader");
	}
	
	public String handleEditNameEvent() {
        if(this.getColumnGridPaneElementText().getColumnTextLabel().isVisible()) {
        	columnGridPaneElementEditButton.setButtonText("Save");
        	this.getColumnGridPaneElementText().getColumnTextLabel().setVisible(false);
        	this.getColumnGridPaneElementText().getColumnTextField().setVisible(true);
		}else {
			columnGridPaneElementEditButton.setButtonText("Edit");
			this.getColumnGridPaneElementText().getColumnTextLabel().setVisible(true);
			this.getColumnGridPaneElementText().getColumnTextField().setVisible(false);
			this.getColumnGridPaneElementText().getColumnTextLabel().setText(this.getColumnGridPaneElementText().getColumnTextField().getText());
		}
        return this.getColumnGridPaneElementText().getColumnTextField().getText();
	}
	
	private void setColumnGridPaneConstraints() {
		this.columnHeaderGridPane.getColumnConstraints().add(columnGridPaneElementText.getColumnConstraints());
		this.columnHeaderGridPane.getColumnConstraints().add(columnGridPaneElementEditButton.getColumnConstraints());
	}
	
	
	// Getter and Setter
	public GridPane getColumnHeaderGridPane() {
		return columnHeaderGridPane;
	}

	public void setColumnHeaderGridPane(GridPane columnHeaderGridPane) {
		this.columnHeaderGridPane = columnHeaderGridPane;
	}
	
	public ColumnGridPaneElementText getColumnGridPaneElementText() {
		return columnGridPaneElementText;
	}

	public void setColumnGridPaneElementText(ColumnGridPaneElementText columnGridPaneElementText) {
		this.columnGridPaneElementText = columnGridPaneElementText;
	}

	
	public ColumnGridPaneElementButton getColumnGridPaneElementEditButton() {
		return columnGridPaneElementEditButton;
	}

	public void setColumnGridPaneElementEditButton(ColumnGridPaneElementButton columnGridPaneElementEditButton) {
		this.columnGridPaneElementEditButton = columnGridPaneElementEditButton;
	}

	public ColumnGridPaneElementButton getColumnGridPaneElementRemoveButton() {
		return columnGridPaneElementRemoveButton;
	}

	public void setColumnGridPaneElementRemoveButton(ColumnGridPaneElementButton columnGridPaneElementRemoveButton) {
		this.columnGridPaneElementRemoveButton = columnGridPaneElementRemoveButton;
	}	
	
	public RowConstraints getRowConstraints() {
		return rowConstraints;
	}

	public void setRowConstraints(RowConstraints rowConstraints) {
		this.rowConstraints = rowConstraints;
	}
}
