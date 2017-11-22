package com.dhbwGroup.kanban.views;

import java.util.Optional;

import com.dhbwGroup.kanban.models.ColumnData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class ColumnHeaderGridPane {
	private ColumnData columnData;
	
	private GridPane columnHeaderGridPane;
	
	private Text titleLabel;
	private TextField titleTextField;
	private Button editSaveButton;
	private Button removeButton;

	private Button changeCapacityButton;

	private RowConstraints rowConstraints;

	public ColumnHeaderGridPane(ColumnData columnData) {
		this.columnData = columnData;
		initialize();
	}

	private void initialize() {
		rowConstraints = new RowConstraints();
		rowConstraints.setPrefHeight(100);
		
		this.titleLabel = new Text(columnData.getName());
		
		this.titleTextField = new TextField(columnData.getName());
		this.titleTextField.setVisible(false);
		
		this.editSaveButton = new Button("Edit");
		this.editSaveButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	handleEditSaveButtonEvent();
    	    }
		});
		
		this.removeButton = new Button("Remove");
		
		this.changeCapacityButton = new Button("Change Column Capacity");
		this.changeCapacityButton.setVisible(false);
		this.changeCapacityButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	handleChangeCapacityButton();
    	    }
		});
		
		this.columnHeaderGridPane = new GridPane();
		
		addNodesToGridPane();
		addStyleClasses();
	}
	
	private void addStyleClasses() {
		this.columnHeaderGridPane.getStyleClass().add("columnHeaderGridPane");
		this.columnHeaderGridPane.setMinWidth(450);
		this.columnHeaderGridPane.setPrefWidth(450);
		this.columnHeaderGridPane.setMaxWidth(450);
		
		this.titleLabel.getStyleClass().add("columnHeaderTitleLabel");
		this.titleTextField.getStyleClass().add("columnHeaderTitleTextField");
		
		this.editSaveButton.getStyleClass().add("columnHeaderEditSaveButton");
		
		this.removeButton.getStyleClass().add("columnHeaderRemoveButton");
		this.changeCapacityButton.getStyleClass().add("columnHeaderChangeCapacityButton");
	}

	private void addNodesToGridPane() {
		columnHeaderGridPane.add(titleLabel, 0, 0);
		columnHeaderGridPane.add(titleTextField, 0, 0);
		
		columnHeaderGridPane.add(editSaveButton, 1, 0);
		
		columnHeaderGridPane.add(removeButton, 2, 0);
		columnHeaderGridPane.add(changeCapacityButton, 2, 0);
	}
	
//---------------------------------------------------------------------------
//--------------------Handle Edit Column Name--------------------------------
//---------------------------------------------------------------------------
	
	private void handleEditSaveButtonEvent() {
        if(this.editSaveButton.getText().equals("Edit")) {
        	this.editSaveButton.setText("Save");
        	toggleAllVisibilitys();
		}else {
			this.editSaveButton.setText("Edit");
			updateLabels();
			updateTaskData();
			toggleAllVisibilitys();
		}		
	}
	
	private void updateTaskData() {
		this.columnData.setName(titleLabel.getText());
	}

	private void updateLabels() {
		titleLabel.setText(titleTextField.getText());
	}
	
	private void toggleAllVisibilitys() {
		titleLabel.setVisible(!titleLabel.isVisible());
		titleTextField.setVisible(!titleTextField.isVisible());
		
		if(this.columnData.getType().equals("doing")) {
			removeButton.setVisible(!removeButton.isVisible());
			changeCapacityButton.setVisible(!changeCapacityButton.isVisible());	
		}
	}

//---------------------------------------------------------------------------
//--------------------Handle Change Capacity---------------------------------
//---------------------------------------------------------------------------
	
	private void handleChangeCapacityButton() {
		TextInputDialog dialog = new TextInputDialog(Integer.toString(this.columnData.getMaxTasks()));
		dialog.setTitle("Change Column Capacity!");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter the maximal number oft tasks in the column:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent((title) -> {
			try {
				this.columnData.setMaxTasks(Integer.parseInt(title));
			}catch(NumberFormatException e) {
				System.err.println("Not a numeric number!");
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Input Format is wrong!");
				alert.setContentText("Input Format is wrong! Please only type numeric values!");

				alert.showAndWait();				
			}
			
		});		
	}
	
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public GridPane getColumnHeaderGridPane() {
		return columnHeaderGridPane;
	}

	public void setColumnHeaderGridPane(GridPane columnHeaderGridPane) {
		this.columnHeaderGridPane = columnHeaderGridPane;
	}
	
	public RowConstraints getRowConstraints() {
		return rowConstraints;
	}

	public void setRowConstraints(RowConstraints rowConstraints) {
		this.rowConstraints = rowConstraints;
	}
	
	public ColumnData getColumnData() {
		return columnData;
	}

	public void setColumnData(ColumnData columnData) {
		this.columnData = columnData;
	}

	public Text getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(Text titleLabel) {
		this.titleLabel = titleLabel;
	}

	public TextField getTitleTextField() {
		return titleTextField;
	}

	public void setTitleTextField(TextField titleTextField) {
		this.titleTextField = titleTextField;
	}

	public Button getEditSaveButton() {
		return editSaveButton;
	}

	public void setEditSaveButton(Button editSaveButton) {
		this.editSaveButton = editSaveButton;
	}

	public Button getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(Button removeButton) {
		this.removeButton = removeButton;
	}

	public Button getChangeCapacityButton() {
		return changeCapacityButton;
	}

	public void setChangeCapacityButton(Button changeCapacityButton) {
		this.changeCapacityButton = changeCapacityButton;
	}
}
