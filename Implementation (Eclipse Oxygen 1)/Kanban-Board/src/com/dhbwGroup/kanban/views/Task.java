package com.dhbwGroup.kanban.views;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.dhbwGroup.kanban.controllers.Controller;
import com.dhbwGroup.kanban.exceptions.TooManyCharsException;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.TaskChangeLog;
import com.dhbwGroup.kanban.models.TaskData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class Task{
	private TaskData taskData;
	private CategoryData categoryData;
	private ColumnData columnData;
	private List<CategoryData> categoriesData;
	
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

	public Task(TaskData taskData, List<CategoryData> categoriesData) {
		this.taskData = taskData;
		this.categoriesData = categoriesData;
		initialize();
	}

	public Task(TaskData taskData, CategoryData categoryData, List<CategoryData> categoriesData) {
		this.taskData = taskData;
		this.categoryData = categoryData;
		this.categoriesData = categoriesData;
		initialize();
	}
	
	public Task(TaskData taskData, UUID categoryDataUUID, List<CategoryData> categoriesData) {
		this.taskData = taskData;
		categoriesData.forEach(categoryData -> {
			if(categoryData.getId().equals(categoryDataUUID))
				this.categoryData = categoryData;
		});
		this.categoriesData = categoriesData;
		initialize();
	}

	private void initialize() {
		titleLabel = new Text(this.taskData.getTitle());
		titleTextField = new TextField(this.taskData.getTitle());
		titleTextField.setVisible(false);
		
		editSaveButton = new Button("Edit");
		editSaveButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	try {
					handleEditSaveButtonEvent();
				} catch (TooManyCharsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	    }
		});
		
		deleteButton = new Button("Delete");
		deleteButton.setVisible(false);
		archiveButton = new Button("Archive");
		archiveButton.setVisible(false);
		
		descriptionLabel = new Label(this.taskData.getDescription());
		descriptionTextArea = new TextArea(this.taskData.getDescription());
		descriptionTextArea.setVisible(false);
		
		colorPicker = new ColorPicker();
		colorPicker.setValue(Color.web(this.taskData.getColor()));
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				taskGridPane.setStyle("-fx-background-color: #" + getRGBCode(colorPicker.getValue()));
			}
        });
		colorPicker.setVisible(false);
		
		categoryDropDown = new ComboBox<CategoryData>();
		categoriesData.forEach((activeCategory) -> {
			categoryDropDown.getItems().add(activeCategory);
		});
		categoryDropDown.setVisible(false);
		categoryDropDown.setConverter(new StringConverter<CategoryData>() {

			@Override
			public CategoryData fromString(String string) {
				return categoryDropDown.getItems().stream().filter(categoryData -> 
						categoryData.getTitle().equals(string)).findFirst().orElse(null);
			}

			@Override
			public String toString(CategoryData categoryData) {
				return categoryData.getTitle();
			}
			
		});
		if(categoryData == null) {
			categoryLabel = new Text();
		}else {
			categoryLabel = new Text(categoryData.getTitle());
			categoryDropDown.setValue(categoryData);
		}
		
		showHistoryButton = new Button("Show History");
		showHistoryButton.setVisible(false);
		showHistoryButton.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
					showHistory();
    	    }
		});
		
		
		
		descriptionLabel.setWrapText(true);
		descriptionTextArea.setWrapText(true);
		descriptionTextArea.setPrefRowCount(3);
		descriptionTextArea.setPrefColumnCount(20);
		
		taskGridPane = new GridPane();
		
		addNodesToGridPane();
		addStyleClasses();
	}

	private void addStyleClasses() {
		this.taskGridPane.getStyleClass().add("taskGridPane");
		this.taskGridPane.setStyle("-fx-background-color: #" + this.taskData.getColor());
		
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
	
	private void handleEditSaveButtonEvent() throws TooManyCharsException {
        if(this.editSaveButton.getText().equals("Edit")) {
        	this.editSaveButton.setText("Save");
        	toggleAllVisibilitys();
		}else {
			if(titleTextField.getText().length() > Controller.MAX_ALLOWED_CHARS)
				throw new TooManyCharsException();			
			this.editSaveButton.setText("Edit");
			updateLabels();
			updateTaskData();
			toggleAllVisibilitys();
		}		
	}
	
	public void addNewCategoryToDropDown(CategoryData newCategoryData) {
		this.categoryDropDown.getItems().add(newCategoryData);
	}

	private void updateTaskData() {
		this.taskData.setTitle(titleLabel.getText());
		this.taskData.setDescription(descriptionLabel.getText());
		this.taskData.setColor(getRGBCode(colorPicker.getValue()));
		if(categoryDropDown.getValue() != null)
			this.taskData.setCategoryUUID(this.categoryDropDown.getValue().getId());
	}

	private void updateLabels() {
		titleLabel.setText(titleTextField.getText());
		descriptionLabel.setText(descriptionTextArea.getText());
		if(categoryDropDown.getValue() != null)
			categoryLabel.setText(categoryDropDown.getValue().getTitle());
	}

	private void toggleAllVisibilitys() {
		titleLabel.setVisible(!titleLabel.isVisible());
		titleTextField.setVisible(!titleTextField.isVisible());
		
		deleteButton.setVisible(!deleteButton.isVisible());
		archiveButton.setVisible(!archiveButton.isVisible());
		
		descriptionLabel.setVisible(!descriptionLabel.isVisible());
		descriptionTextArea.setVisible(!descriptionTextArea.isVisible());
		
		colorPicker.setVisible(!colorPicker.isVisible());
		
		categoryLabel.setVisible(!categoryLabel.isVisible());
		categoryDropDown.setVisible(!categoryDropDown.isVisible());
		
		showHistoryButton.setVisible(!showHistoryButton.isVisible());
	}
	
	private String getRGBCode(Color color) {
		return String.format( "%02X%02X%02X",
	            (int)( color.getRed() * 255 ),
	            (int)( color.getGreen() * 255 ),
	            (int)( color.getBlue() * 255 ) );
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
			alert.setContentText(message);
			alert.setResizable(true);
			alert.getDialogPane().setMinWidth(500);

			alert.showAndWait();	
		}
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

	public ComboBox<CategoryData> getCategoryDropDown() {
		return categoryDropDown;
	}

	public void setCategoryDropDown(ComboBox<CategoryData> categoryDropDown) {
		this.categoryDropDown = categoryDropDown;
	}

	public CategoryData getCategoryData() {
		return categoryData;
	}

	public void setCategoryData(CategoryData categoryData) {
		this.categoryData = categoryData;
	}

	public List<CategoryData> getCategoriesData() {
		return categoriesData;
	}

	public void setCategoriesData(List<CategoryData> categoriesData) {
		this.categoriesData = categoriesData;
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

	public ColumnData getColumnData() {
		return columnData;
	}

	public void setColumnData(ColumnData columnData) {
		this.columnData = columnData;
	}
}
