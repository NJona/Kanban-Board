package com.dhbwGroup.kanban.views;

import com.dhbwGroup.kanban.models.ColumnData;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Column {
	private ColumnData columnData;
	
	private int numberOfColumns;
	private int columnIndex;
	
	private GridPane columnGridPane;
	private ColumnGridPaneElementText columnGridPaneElementText;
	private ColumnGridPaneElementButton columnGridPaneElementButton;
	private ColumnConstraints columnConstraints;
	
	public Column(int numberOfColumns, int columnIndex, ColumnData columnData) {
		this.columnData = columnData;
		this.numberOfColumns = numberOfColumns;
		this.columnIndex = columnIndex;
		
		columnConstraints = new ColumnConstraints();
		columnGridPane = new GridPane();
		
		columnGridPaneElementText = new ColumnGridPaneElementText(columnData.getName(), 80);
		columnGridPaneElementButton = new ColumnGridPaneElementButton("Edit", 20);
		
		initialize();
	}
	
	public Column(int numberOfColumns, int columnIndex, ColumnData columnData, String buttonText) {
		this.columnData = columnData;
		this.numberOfColumns = numberOfColumns;
		this.columnIndex = columnIndex;
		
		columnConstraints = new ColumnConstraints();
		columnGridPane = new GridPane();
		
		columnGridPaneElementText = new ColumnGridPaneElementText(columnData.getName(), 80);
		columnGridPaneElementButton = new ColumnGridPaneElementButton(buttonText, 20);
	
		initialize();
	}
	
	private void initialize() {
		setColumnConstraintsDefaults();
		setVisibilityOfColumn();
		setColumnGridPaneConstraints();
		addToGridPane(columnGridPaneElementText.getColumnTextLabel(), 0, 0);
		addToGridPane(columnGridPaneElementText.getColumnTextField(), 0, 0);
		addToGridPane(columnGridPaneElementButton.getToggleChangeColumnName(), 1, 0);
		this.columnGridPane.getStyleClass().add("column");
	}

	private void setColumnGridPaneConstraints() {
		this.columnGridPane.getColumnConstraints().add(columnGridPaneElementText.getColumnConstraints());
		this.columnGridPane.getColumnConstraints().add(columnGridPaneElementButton.getColumnConstraints());
	}

	private void setVisibilityOfColumn() {
		columnGridPane.setVisible(columnData.getIsActive());
	}
	
	public void setColumnConstraintsDefaults() {
		columnConstraints.setPercentWidth((100 / numberOfColumns));
		//columnConstraints.setHalignment(HPos.CENTER);
	}
	
	private void addToGridPane(Text columnNameLabel, int columnIndex, int rowIndex) {
		this.columnGridPane.add(columnNameLabel, columnIndex, rowIndex);
	}
	
	private void addToGridPane(TextField columnTextField, int columnIndex, int rowIndex) {
		this.columnGridPane.add(columnTextField, columnIndex, rowIndex);
	}
	
	private void addToGridPane(Button columnToggleChangeNameButton, int columnIndex, int rowIndex) {
		this.columnGridPane.add(columnToggleChangeNameButton, columnIndex, rowIndex);
	}

	public ColumnData getColumnData() {
		return columnData;
	}

	public void setColumnData(ColumnData columnData) {
		this.columnData = columnData;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
		this.setColumnConstraintsDefaults();
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public GridPane getColumnGridPane() {
		return columnGridPane;
	}

	public void setColumnGridPane(GridPane columnGridPane) {
		this.columnGridPane = columnGridPane;
	}

	public ColumnGridPaneElementText getColumnGridPaneElementText() {
		return columnGridPaneElementText;
	}

	public void setColumnGridPaneElementText(ColumnGridPaneElementText columnGridPaneElementText) {
		this.columnGridPaneElementText = columnGridPaneElementText;
	}

	public ColumnGridPaneElementButton getColumnGridPaneElementButton() {
		return columnGridPaneElementButton;
	}

	public void setColumnGridPaneElementButton(ColumnGridPaneElementButton columnGridPaneElementButton) {
		this.columnGridPaneElementButton = columnGridPaneElementButton;
	}

	public ColumnConstraints getColumnConstraints() {
		return columnConstraints;
	}

	public void setColumnConstraints(ColumnConstraints columnConstraints) {
		this.columnConstraints = columnConstraints;
	}
	
}
