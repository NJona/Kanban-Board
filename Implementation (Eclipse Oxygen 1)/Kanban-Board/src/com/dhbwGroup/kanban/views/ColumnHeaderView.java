package com.dhbwGroup.kanban.views;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ColumnHeaderView {

	private HBox columnHeaderHBox;
	
	private Text titleLabel;
	private TextField titleTextField;
	private Button editSaveButton;
	private Button removeButton;
	
	private Button changeCapacityButton;
	
	public ColumnHeaderView() {
		this.columnHeaderHBox = new HBox();
		
		this.titleLabel = new Text();
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
		this.columnHeaderHBox.getStyleClass().add("columnHeaderGridPane");
		this.columnHeaderHBox.setMinWidth(450);
		this.columnHeaderHBox.setPrefWidth(450);
		this.columnHeaderHBox.setMaxWidth(450);
		
		this.titleLabel.getStyleClass().add("columnHeaderTitleLabel");
		this.titleTextField.getStyleClass().add("columnHeaderTitleTextField");
		
		this.editSaveButton.getStyleClass().add("columnHeaderEditSaveButton");
		
		this.removeButton.getStyleClass().add("columnHeaderRemoveButton");
		this.changeCapacityButton.getStyleClass().add("columnHeaderChangeCapacityButton");
	}

	private void addNodesToGridPane() {
		columnHeaderHBox.getChildren().add(titleLabel);
		columnHeaderHBox.getChildren().add(titleTextField);
		
		columnHeaderHBox.getChildren().add(editSaveButton);
		
		columnHeaderHBox.getChildren().add(removeButton);
		columnHeaderHBox.getChildren().add(changeCapacityButton);
	}
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public HBox getColumnHeaderHBox() {
		return columnHeaderHBox;
	}

	public Text getTitleLabel() {
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

	public void setColumnHeaderHBox(HBox columnHeaderHBox) {
		this.columnHeaderHBox = columnHeaderHBox;
	}

	public void setTitleLabel(Text titleLabel) {
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
