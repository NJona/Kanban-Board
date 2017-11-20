package com.dhbwGroup.kanban.views;

import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.TaskData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Task {
	private TaskData taskData;
	private CategoryData categoryData;
	
	private GridPane taskGridPane;
	
	private Text titleLabel;
	private TextField titleTextField;
	
	private Button editSaveButton;
	
	private Label descriptionLabel;
	private TextArea descriptionTextArea;
	
	private Text categoryLabel;

	public Task(TaskData taskData) {
		this.taskData = taskData;
		initialize();
	}

	public Task(TaskData taskData, CategoryData categoryData) {
		this.taskData = taskData;
		this.categoryData = categoryData;
		initialize();
	}

	private void initialize() {
		titleLabel = new Text(this.taskData.getTitle());
		titleTextField = new TextField(this.taskData.getTitle());
		titleTextField.setVisible(false);
		
		editSaveButton = new Button("Edit");
		editSaveButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	handleEditSaveButtonEvent();
    	    }
		});
		
		descriptionLabel = new Label(this.taskData.getDescription());
		descriptionTextArea = new TextArea(this.taskData.getDescription());
		descriptionTextArea.setVisible(false);
		//descriptionLabel.setVisible(false);
		
		if(categoryData == null) {
			categoryLabel = new Text();
		}else {
			categoryLabel = new Text(categoryData.getTitle());
		}
		
		
		descriptionLabel.setWrapText(true);
		descriptionTextArea.setWrapText(true);
		descriptionTextArea.setPrefRowCount(3);
		descriptionTextArea.setPrefColumnCount(20);
		
		taskGridPane = new GridPane();
		
		addNodesToGridPane();
		addStyleClasses();
	}

	private void addStyleClasses() {
		this.taskGridPane.getStyleClass().add("task");
		this.taskGridPane.setStyle("-fx-background-color: #" + this.taskData.getColor());
		
		this.titleLabel.getStyleClass().add("taskTitleLabel");
		this.titleTextField.getStyleClass().add("taskTitleTextField");
		
		this.editSaveButton.getStyleClass().add("taskEditSaveButton");
		
		this.descriptionLabel.getStyleClass().add("taskDescriptionLabel");
		this.descriptionTextArea.getStyleClass().add("taskDescriptionTextArea");
		
		this.categoryLabel.getStyleClass().add("taskCategoryLabel");
	}

	private void addNodesToGridPane() {
		taskGridPane.add(titleLabel, 0, 0);
		taskGridPane.add(titleTextField, 0, 0);
		
		taskGridPane.add(editSaveButton, 1, 0);
		
		taskGridPane.add(descriptionLabel, 0, 1, GridPane.REMAINING, 1);
		taskGridPane.add(descriptionTextArea, 0, 1, GridPane.REMAINING, 1);
		
		taskGridPane.add(categoryLabel, 0, 2, GridPane.REMAINING, 1);
	}
	
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
		this.taskData.setTitle(titleLabel.getText());
		this.taskData.setDescription(descriptionLabel.getText());
	}

	private void updateLabels() {
		titleLabel.setText(titleTextField.getText());
		descriptionLabel.setText(descriptionTextArea.getText());
	}

	private void toggleAllVisibilitys() {
		titleLabel.setVisible(!titleLabel.isVisible());
		titleTextField.setVisible(!titleTextField.isVisible());
		
		descriptionLabel.setVisible(!descriptionLabel.isVisible());
		descriptionTextArea.setVisible(!descriptionTextArea.isVisible());
		
		categoryLabel.setVisible(!categoryLabel.isVisible());
	}
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------	

	public TaskData getTaskData() {
		return taskData;
	}

	public void setTaskData(TaskData taskData) {
		this.taskData = taskData;
	}

	public GridPane getTaskGridPane() {
		return taskGridPane;
	}

	public void setTaskGridPane(GridPane taskGridPane) {
		this.taskGridPane = taskGridPane;
	}

	public Text getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(Text titleLabel) {
		this.titleLabel = titleLabel;
	}

	public Label getDescriptionLabel() {
		return descriptionLabel;
	}

	public void setDescriptionLabel(Label descriptionLabel) {
		this.descriptionLabel = descriptionLabel;
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

	public TextArea getDescriptionTextArea() {
		return descriptionTextArea;
	}

	public void setDescriptionTextArea(TextArea descriptionTextArea) {
		this.descriptionTextArea = descriptionTextArea;
	}

	public Text getCategoryLabel() {
		return categoryLabel;
	}

	public void setCategoryLabel(Text categoryLabel) {
		this.categoryLabel = categoryLabel;
	}
}
