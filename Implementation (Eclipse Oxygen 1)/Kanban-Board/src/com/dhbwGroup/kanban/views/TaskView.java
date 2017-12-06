package com.dhbwGroup.kanban.views;

import com.dhbwGroup.kanban.models.CategoryData;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TaskView {
	private GridPane taskGridPane;
	
	private Text titleLabel;
	private TextField titleTextField;
	
	private Button editSaveButton;
	private Button deleteButton;
	private Button archiveButton;
	
	private Label descriptionLabel;
	private TextArea descriptionTextArea;
	
	private ColorPicker colorPicker;
	
	private Text categoryLabel;
	private ComboBox<CategoryData> categoryDropDown;
	
	private Button showHistoryButton;
	
	public TaskView() {
		taskGridPane = new GridPane();
		
		titleLabel = new Text();
		titleTextField = new TextField();
		
		editSaveButton = new Button();
		deleteButton = new Button();
		archiveButton = new Button();
		
		descriptionLabel = new Label();
		descriptionTextArea = new TextArea();
		
		colorPicker = new ColorPicker();
		
		categoryLabel = new Text();
		categoryDropDown = new ComboBox<CategoryData>();
		
		showHistoryButton = new Button();
		
		initialize();
	}
	
	private void initialize() {
		titleTextField.setVisible(false);
		
		deleteButton.setVisible(false);
		archiveButton.setVisible(false);
		
		descriptionTextArea.setVisible(false);
		
		colorPicker.setVisible(false);
		
		categoryDropDown.setVisible(false);
		
		showHistoryButton.setVisible(false);
		
		addStyleClasses();
		addNodesToGridPane();
	}
	
	
	private void addStyleClasses() {
		this.taskGridPane.getStyleClass().add("taskGridPane");
		
		this.titleLabel.getStyleClass().add("taskTitleLabel");
		this.titleTextField.getStyleClass().add("taskTitleTextField");
		
		this.editSaveButton.getStyleClass().add("taskEditSaveButton");
		this.deleteButton.getStyleClass().add("taskDeleteButton");
		this.archiveButton.getStyleClass().add("taskArchiveButton");
		
		this.descriptionLabel.getStyleClass().add("taskDescriptionLabel");
		this.descriptionTextArea.getStyleClass().add("taskDescriptionTextArea");
		
		this.colorPicker.getStyleClass().add("taskColorPicker");
		
		this.categoryLabel.getStyleClass().add("taskCategoryLabel");
		this.categoryDropDown.getStyleClass().add("taskCategoryDropDown");
		
		this.showHistoryButton.getStyleClass().add("taskShowHistoryButton");
	}
	
	private void addNodesToGridPane() {
		taskGridPane.add(titleLabel, 0, 0);
		taskGridPane.add(titleTextField, 0, 0);
		
		taskGridPane.add(editSaveButton, 3, 0);
		taskGridPane.add(archiveButton, 2, 0);
		taskGridPane.add(deleteButton, 1, 0);
		
		taskGridPane.add(descriptionLabel, 0, 1, GridPane.REMAINING, 1);
		taskGridPane.add(descriptionTextArea, 0, 1, GridPane.REMAINING, 1);
		
		taskGridPane.add(colorPicker, 0, 2);
		
		taskGridPane.add(categoryLabel, 0, 2, GridPane.REMAINING, 1);
		taskGridPane.add(categoryDropDown, 0, 3, GridPane.REMAINING, 1);
		
		taskGridPane.add(showHistoryButton, 3, 3);
	}
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------	

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

		public ComboBox<CategoryData> getCategoryDropDown() {
			return categoryDropDown;
		}

		public void setCategoryDropDown(ComboBox<CategoryData> categoryDropDown) {
			this.categoryDropDown = categoryDropDown;
		}

		public Button getDeleteButton() {
			return deleteButton;
		}

		public void setDeleteButton(Button deleteButton) {
			this.deleteButton = deleteButton;
		}

		public Button getArchiveButton() {
			return archiveButton;
		}

		public void setArchiveButton(Button archiveButton) {
			this.archiveButton = archiveButton;
		}

		public ColorPicker getColorPicker() {
			return colorPicker;
		}

		public void setColorPicker(ColorPicker colorPicker) {
			this.colorPicker = colorPicker;
		}

		public Button getShowHistoryButton() {
			return showHistoryButton;
		}

		public void setShowHistoryButton(Button showHistoryButton) {
			this.showHistoryButton = showHistoryButton;
		}
}
