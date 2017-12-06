package com.dhbwGroup.kanban.controllers;

import java.util.Optional;

import com.dhbwGroup.kanban.exceptions.TooManyCharsException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.views.ColumnHeaderView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class ColumnHeaderController extends Controller{

	private ColumnHeaderView columnHeaderView;
	
	private ColumnData columnData;
	
	public ColumnHeaderController(Project projectData, ColumnData columnData) {
		super();
		this.setProjectData(projectData);
		this.columnData = columnData;
		initialize();
	}

	private void initialize() {
		columnHeaderView = new ColumnHeaderView();
		
		columnHeaderView.getTitleLabel().setText(columnData.getName());
		columnHeaderView.getTitleTextField().setText(columnData.getName());
		
		columnHeaderView.getEditSaveButton().setText("Edit");
		columnHeaderView.getEditSaveButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	try {
					handleEditSaveButtonEvent();
				} catch (TooManyCharsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	    }
		});
		
		columnHeaderView.getRemoveButton().setText("Remove");
		columnHeaderView.getChangeCapacityButton().setText("Max Tasks");
		columnHeaderView.getChangeCapacityButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	handleChangeCapacityButton();
    	    }
		});
	}
	
	//---------------------------------------------------------------------------
	//--------------------Handle Edit Column Name--------------------------------
	//---------------------------------------------------------------------------
		
		private void handleEditSaveButtonEvent() throws TooManyCharsException {
	        if(columnHeaderView.getEditSaveButton().getText().equals("Edit")) {
	        	columnHeaderView.getEditSaveButton().setText("Save");
	        	toggleAllVisibilitys();
			}else {
				if(columnHeaderView.getTitleTextField().getText().length() > ProjectController.MAX_ALLOWED_CHARS_IN_TITEL)
					throw new TooManyCharsException();
				columnHeaderView.getEditSaveButton().setText("Edit");
				updateLabels();
				updateTaskData();
				toggleAllVisibilitys();
			}		
		}
		
		private void updateTaskData() {
			this.columnData.setName(columnHeaderView.getTitleLabel().getText());
			this.saveProject();
		}

		private void updateLabels() {
			columnHeaderView.getTitleLabel().setText(columnHeaderView.getTitleTextField().getText());
		}
		
		private void toggleAllVisibilitys() {
			columnHeaderView.getTitleLabel().setVisible(!columnHeaderView.getTitleLabel().isVisible());
			columnHeaderView.getTitleTextField().setVisible(!columnHeaderView.getTitleTextField().isVisible());
			if(this.columnData.getType().equals("doing")) {
				columnHeaderView.getRemoveButton().setVisible(!columnHeaderView.getRemoveButton().isVisible());
				columnHeaderView.getChangeCapacityButton().setVisible(!columnHeaderView.getChangeCapacityButton().isVisible());	
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
					if(Integer.parseInt(title) > 100) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Number too high!");
						alert.setContentText("Number too high! Maximum 100 Tasks allowed!");

						alert.showAndWait();					
					}else if(Integer.parseInt(title) < columnData.getMaxTasks()) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Too many Tasks in Column!");
						alert.setContentText("Too many Tasks in Column! Please move Tasks first!");

						alert.showAndWait();							
					}else {
						this.columnData.setMaxTasks(Integer.parseInt(title));
						this.saveProject();
					}
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

	public ColumnHeaderView getColumnHeaderView() {
		return columnHeaderView;
	}

	public ColumnData getColumnData() {
		return columnData;
	}

	public void setColumnHeaderView(ColumnHeaderView columnHeaderView) {
		this.columnHeaderView = columnHeaderView;
	}

	public void setColumnData(ColumnData columnData) {
		this.columnData = columnData;
	}
}
