package com.dhbwGroup.kanban.views;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;

public class ColumnGridPaneElementButton {

	private ColumnConstraints columnConstraints;

	private Button button;

	private String buttonText;

	private double percentWidth;

	public ColumnGridPaneElementButton(String buttonText, double percentWidth) {
		this.buttonText = buttonText;
		this.percentWidth = percentWidth;
		
		button = new Button(buttonText);
		button.getStyleClass().add("columnButton");
		columnConstraints = new ColumnConstraints();
		
		setColumnConstraintsDefaults(this.percentWidth);
	}
	
	private void setColumnConstraintsDefaults(double percentWidth) {
		columnConstraints.setPercentWidth(percentWidth);
		columnConstraints.setHalignment(HPos.CENTER);
	}
	
	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
		this.button.setText(this.buttonText);
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

}
