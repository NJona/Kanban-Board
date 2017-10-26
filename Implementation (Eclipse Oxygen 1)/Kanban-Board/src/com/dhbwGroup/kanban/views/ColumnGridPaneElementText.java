package com.dhbwGroup.kanban.views;

import javafx.geometry.HPos;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.text.Text;

public class ColumnGridPaneElementText {
	private Text columnTextLabel;
	private TextField columnTextField;
	private ColumnConstraints columnConstraints;
	
	private double percentWidth;
	private String columnName;
	
	public ColumnGridPaneElementText(String columnName, double percentWidth) {
		this.columnName = columnName;
		this.percentWidth = percentWidth;
		
		columnTextLabel = new Text(this.columnName);
		columnTextLabel.setVisible(true);
		columnTextField = new TextField(this.columnName);
		columnTextField.setVisible(false);
		columnConstraints = new ColumnConstraints();
		
		setColumnConstraintsDefaults(this.percentWidth);
	}
	
	private void setColumnConstraintsDefaults(double percentWidth) {
		columnConstraints.setPercentWidth(percentWidth);
		columnConstraints.setHalignment(HPos.CENTER);
	}
	
	public Text getColumnTextLabel() {
		return columnTextLabel;
	}
	
	public void setColumnTextLabel(Text columnTextLabel) {
		this.columnTextLabel = columnTextLabel;
	}
	
	public TextField getColumnTextField() {
		return columnTextField;
	}
	
	public void setColumnTextField(TextField columnTextField) {
		this.columnTextField = columnTextField;
	}
	
	public ColumnConstraints getColumnConstraints() {
		return columnConstraints;
	}
	
	public void setColumnConstraints(ColumnConstraints columnConstraints) {
		this.columnConstraints = columnConstraints;
	}
	
	public double getPercentWidth() {
		return percentWidth;
	}

	public void setPercentWidth(double percentWidth) {
		this.percentWidth = percentWidth;
		this.columnConstraints.setPercentWidth(this.percentWidth);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
		this.columnTextLabel.setText(this.columnName);
		this.columnTextField.setText(this.columnName);
	}
	
}
