package com.dhbwGroup.kanban.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.dhbwGroup.kanban.exceptions.TooManyCharsException;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.models.TaskChangeLog;
import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.views.TaskView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

public class TaskController extends Controller{
	private TaskView taskView;
	
	private TaskData taskData;
	
	private ColumnData columnData;
	
	private CategoryData categoryData;
	
	public TaskController(Project projectData, TaskData taskData) {
		super();
		this.setProjectData(projectData);
		this.taskData = taskData;
		if(taskData.getCategoryUUID() != null) {
			Iterator<CategoryData> categoryDataIterator = this.getProjectData().getCategoriesData().iterator();
			while(categoryDataIterator.hasNext()) {
				CategoryData activeCategoryData = categoryDataIterator.next();
				if(activeCategoryData.getId().equals(taskData.getCategoryUUID())) {
					this.categoryData = activeCategoryData;
					break;
				}
					
			}
		}
		initialize();
	}

	
	
	private void initialize() {
		this.taskView = new TaskView();
		
		taskView.getTitleLabel().setText(this.taskData.getTitle());
		taskView.getTitleTextField().setText(this.taskData.getTitle());
		
		taskView.getEditSaveButton().setText("Edit");
		taskView.getEditSaveButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	try {
					handleEditSaveButtonEvent();
				} catch (TooManyCharsException e1) {
					e1.printStackTrace();
				}
    	    }
		});
		
		taskView.getDeleteButton().setText("Delete");
		taskView.getDeleteButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleDeleteButton();
			}
		});
		taskView.getArchiveButton().setText("Archive");
		taskView.getArchiveButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				handleArchiveButton();
			}
		});
		
		taskView.getDescriptionLabel().setText(this.taskData.getDescription());
		taskView.getDescriptionTextArea().setText(this.taskData.getDescription());
		
		taskView.getColorPicker().setValue(Color.web(this.taskData.getColor()));
		taskView.getColorPicker().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				taskView.getTaskGridPane().setStyle("-fx-background-color: #" + getRGBCode(taskView.getColorPicker().getValue()));
			}
        });
		
		this.getProjectData().getCategoriesData().forEach((activeCategory) -> {
			taskView.getCategoryDropDown().getItems().add(activeCategory);
		});
		
		taskView.getCategoryDropDown().setConverter(new StringConverter<CategoryData>() {

			@Override
			public CategoryData fromString(String string) {
				return taskView.getCategoryDropDown().getItems().stream().filter(categoryData -> 
						categoryData.getTitle().equals(string)).findFirst().orElse(null);
			}

			@Override
			public String toString(CategoryData categoryData) {
					return categoryData.getTitle();
			}
			
		});
		
		if(categoryData != null) {
			taskView.getCategoryLabel().setText(categoryData.getTitle());
			taskView.getCategoryDropDown().setValue(categoryData);
		}
		
		taskView.getShowHistoryButton().setText("Show History");
		taskView.getShowHistoryButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
					showHistory();
    	    }
		});
		
		taskView.getDescriptionLabel().setWrapText(true);
		taskView.getDescriptionTextArea().setWrapText(true);
		taskView.getDescriptionTextArea().setPrefRowCount(3);
		taskView.getDescriptionTextArea().setPrefColumnCount(20);
		
		taskView.getTaskGridPane().setStyle("-fx-background-color: #" + this.taskData.getColor());
		taskView.getTaskGridPane().setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				handleDragAndDrop(mouseEvent);
			}			
		});
	}
	
	private void handleEditSaveButtonEvent() throws TooManyCharsException {
        if(this.taskView.getEditSaveButton().getText().equals("Edit")) {
        	this.taskView.getEditSaveButton().setText("Save");
        	toggleAllVisibilitys();
		}else {
			if(taskView.getTitleTextField().getText().length() > ProjectController.MAX_ALLOWED_CHARS_IN_TITEL)
				throw new TooManyCharsException();			
			this.taskView.getEditSaveButton().setText("Edit");
			updateLabels();
			updateTaskData();
			toggleAllVisibilitys();
		}		
	}
	
	public void addNewCategoryToDropDown(CategoryData newCategoryData) {
		this.taskView.getCategoryDropDown().getItems().add(newCategoryData);
	}

	private void updateTaskData() {
		this.taskData.setTitle(taskView.getTitleLabel().getText());
		this.taskData.setDescription(taskView.getDescriptionLabel().getText());
		this.taskData.setColor(getRGBCode(taskView.getColorPicker().getValue()));
		if(taskView.getCategoryDropDown().getValue() != null)
			this.taskData.setCategoryUUID(this.taskView.getCategoryDropDown().getValue().getId());
		this.saveProject();
	}

	private void updateLabels() {
		taskView.getTitleLabel().setText(taskView.getTitleTextField().getText());
		taskView.getDescriptionLabel().setText(taskView.getDescriptionTextArea().getText());
		if(taskView.getCategoryDropDown().getValue() != null)
			taskView.getCategoryLabel().setText(taskView.getCategoryDropDown().getValue().getTitle());
	}

	private void toggleAllVisibilitys() {
		taskView.getTitleLabel().setVisible(!taskView.getTitleLabel().isVisible());
		taskView.getTitleTextField().setVisible(!taskView.getTitleTextField().isVisible());
		
		taskView.getDeleteButton().setVisible(!taskView.getDeleteButton().isVisible());
		taskView.getArchiveButton().setVisible(!taskView.getArchiveButton().isVisible());
		
		taskView.getDescriptionLabel().setVisible(!taskView.getDescriptionLabel().isVisible());
		taskView.getDescriptionTextArea().setVisible(!taskView.getDescriptionTextArea().isVisible());
		
		taskView.getColorPicker().setVisible(!taskView.getColorPicker().isVisible());
		
		taskView.getCategoryLabel().setVisible(!taskView.getCategoryLabel().isVisible());
		taskView.getCategoryDropDown().setVisible(!taskView.getCategoryDropDown().isVisible());
		
		taskView.getShowHistoryButton().setVisible(!taskView.getShowHistoryButton().isVisible());
	}
	
	private String getRGBCode(Color color) {
		return String.format( "%02X%02X%02X%02X",
	            (int)( color.getRed() * 255 ),
	            (int)( color.getGreen() * 255 ),
	            (int)( color.getBlue() * 255 ),
	            (int)(color.getOpacity() * 255));
	}
	
	private void showHistory() {
		if(taskData.getChangeLog() != null && !taskData.getChangeLog().isEmpty()) {
			String message = "History: \n- Task added in ";
			Iterator<TaskChangeLog> changeLogIterator = taskData.getChangeLog().iterator();
			while(changeLogIterator.hasNext()) {
				TaskChangeLog activeChangeLog = changeLogIterator.next();
				String changeLogDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(activeChangeLog.getTimestamp()));
				message += activeChangeLog.getColumnName() + " at " + changeLogDate + " \n";
				if(changeLogIterator.hasNext())
					message += "- Task moved from " + activeChangeLog.getColumnName() + " to ";
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(taskData.getTitle() + " History");
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.setResizable(true);
			alert.getDialogPane().setMinWidth(500);

			alert.showAndWait();	
		}
	}
	
	private void handleDeleteButton() {
		this.getProjectData().getTasksData().remove(this.taskData);
		this.saveProject();
		if(this.columnData != null) {
			columnData.getTaskUUIDs().remove(taskData.getId());
			((VBox) taskView.getTaskGridPane().getParent()).getChildren().remove(taskView.getTaskGridPane());
		}else {
			((VBox) taskView.getTaskGridPane().getParent()).getChildren().remove(taskView.getTaskGridPane());
		}
	}
	
	private void handleArchiveButton() {
		if(columnData != null) {
			columnData.getTaskUUIDs().remove(taskData.getId());
			this.saveProject();
			((VBox) taskView.getTaskGridPane().getParent()).getChildren().remove(taskView.getTaskGridPane());
		}
	}
	
	private void handleDragAndDrop(MouseEvent mouseEvent) {
        Dragboard db = taskView.getTaskGridPane().startDragAndDrop(TransferMode.MOVE);
        
        ClipboardContent content = new ClipboardContent();
        content.put(ProjectController.TASK_DATA_FORMAT, taskData);
        db.setContent(content);
        
        mouseEvent.consume();
	}

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------

	public TaskView getTaskView() {
		return taskView;
	}

	public TaskData getTaskData() {
		return taskData;
	}

	public void setTaskView(TaskView taskView) {
		this.taskView = taskView;
	}

	public void setTaskData(TaskData taskData) {
		this.taskData = taskData;
	}

	public ColumnData getColumnData() {
		return columnData;
	}

	public void setColumnData(ColumnData columnData) {
		this.columnData = columnData;
	}
}
