package com.dhbwGroup.kanban.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ColumnHeaderView {

	private GridPane columnHeaderGridPane;
	
	private Label titleLabel;
	private TextField titleTextField;
	private Button editSaveButton;
	private Button removeButton;
	
	private Button changeCapacityButton;
	
	public ColumnHeaderView() {
		this.columnHeaderGridPane = new GridPane();
		
		this.titleLabel = new Label();
		this.titleTextField = new TextField();
		
		this.editSaveButton = new Button();
		this.removeButton = new Button();
		
		this.changeCapacityButton = new Button();
		
		initialize();
	}

	private void initialize() {
		
		this.titleTextField.setVisible(false);
		
		this.changeCapacityButton.setVisible(false);
		
		addStyleClasses();
		addNodesToGridPane();
	}
	
	private void addStyleClasses() {
		this.columnHeaderGridPane.getStyleClass().add("columnHeaderGridPane");
		this.columnHeaderGridPane.setMinWidth(450);
		this.columnHeaderGridPane.setPrefWidth(450);
		this.columnHeaderGridPane.setMaxWidth(450);
		
		this.titleLabel.getStyleClass().add("columnHeaderTitleLabel");
		this.titleLabel.setMinWidth(240);
		this.titleLabel.setAlignment(Pos.CENTER);
		this.titleTextField.getStyleClass().add("columnHeaderTitleTextField");
		this.titleTextField.setMinWidth(240);
		this.titleTextField.setAlignment(Pos.CENTER);
		
		this.editSaveButton.getStyleClass().add("columnHeaderEditSaveButton");
		this.editSaveButton.setMinWidth(90);
		
		this.removeButton.getStyleClass().add("columnHeaderRemoveButton");
		this.removeButton.setMinWidth(90);
		this.changeCapacityButton.getStyleClass().add("columnHeaderChangeCapacityButton");
		this.changeCapacityButton.setMinWidth(90);
	}

	private void addNodesToGridPane() {
		columnHeaderGridPane.add(titleLabel, 0, 0);
		columnHeaderGridPane.add(titleTextField, 0, 0);
		
		columnHeaderGridPane.add(editSaveButton, 1, 0);
		
		columnHeaderGridPane.add(removeButton, 2, 0);
		columnHeaderGridPane.add(changeCapacityButton, 2, 0);
	}
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public GridPane getColumnHeaderGridPane() {
		return columnHeaderGridPane;
	}

	public Label getTitleLabel() {
		return titleLabel;
	}

	public TextField getTitleTextField() {
		return titleTextField;
	}

	public Button getEditSaveButton() {
		return editSaveButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public Button getChangeCapacityButton() {
		return changeCapacityButton;
	}

	public void setColumnHeaderGridPane(GridPane columnHeaderGridPane) {
		this.columnHeaderGridPane = columnHeaderGridPane;
	}

	public void setTitleLabel(Label titleLabel) {
		this.titleLabel = titleLabel;
	}

	public void setTitleTextField(TextField titleTextField) {
		this.titleTextField = titleTextField;
	}

	public void setEditSaveButton(Button editSaveButton) {
		this.editSaveButton = editSaveButton;
	}

	public void setRemoveButton(Button removeButton) {
		this.removeButton = removeButton;
	}

	public void setChangeCapacityButton(Button changeCapacityButton) {
		this.changeCapacityButton = changeCapacityButton;
	}
}
