package com.dhbwGroup.kanban.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

import com.dhbwGroup.kanban.exceptions.CategoryNameAlreadyTakenException;
import com.dhbwGroup.kanban.exceptions.TooManyTasksInActiveDoingColumn;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.models.TaskChangeLog;
import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.views.MenuView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MenuController extends Controller{

	MenuView menuView;
	
	ColumnListController columnListController;
	
	CategoriesListController categoriesListController;
	
	public MenuController(Project projectData, ColumnListController columnListController) {
		super();
		this.setProjectData(projectData);
		this.columnListController = columnListController;
		categoriesListController = new CategoriesListController(projectData);
		initialize();
	}

	private void initialize() {
		menuView = new MenuView();

        menuView.getAddTaskButton().setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					addTask();
               }
        });
        menuView.getAddCategoryButton().setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					addCategory();
               }
        });
        menuView.getChangeDoingColumnButton().setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					try {
						changeDoingColumn();
					} catch (TooManyTasksInActiveDoingColumn e) {
						e.printStackTrace();
					}
               }
        });
        menuView.getShowHistoryButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
						showHistory();
            }
        });
        menuView.getShowStatisticsButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
						showStatistics();
            }
        });
	}
	
	protected void addTask(){
		menuView.getAddTaskButton().setDisable(columnListController.addTask());
    }
	
	private void addCategory() {
		TextInputDialog dialog = new TextInputDialog("Category Title");
		dialog.setTitle("Add Category!");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter a category:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent((title) -> {
			try {
				CategoryData newCategoryData = categoriesListController.addCategoryData(title);
				columnListController.getTaskListController().getTaskControllers().forEach((activeTaskController) -> {
					activeTaskController.addNewCategoryToDropDown(newCategoryData);
				});
			} catch (CategoryNameAlreadyTakenException e) {
				
			}
		});
	}
	
	private void changeDoingColumn() throws TooManyTasksInActiveDoingColumn {
		ColumnController activeDoingColumnController = getActiveDoingColumn();
		if(activeDoingColumnController == null) {
			try {
				activeDoingColumnController = columnListController.getColumnControllers().get(1);
			} catch (NullPointerException e) {
				if(this.getProjectData().getColumnsData().isEmpty()) {
					e.printStackTrace();
				}
					return;
			}			
		}
		
		if(activeDoingColumnController.getColumnTaskListController().getColumnTaskListView().getColumnTaskVBox().getChildren().size() > ProjectController.MAX_DEFAULT_TASKS)
			throw new TooManyTasksInActiveDoingColumn();
		
		ChoiceDialog<ColumnData> dialog = new ChoiceDialog<>(activeDoingColumnController.getColumnHeaderController().getColumnData(), this.getProjectData().getColumnsData().subList(1, this.getProjectData().getColumnsData().size()-1));
		dialog.setTitle("Change Doing Column!");
		dialog.setHeaderText(null);
		dialog.setContentText("Please choose a Column:");

		Optional<ColumnData> result = dialog.showAndWait();
		if(result.isPresent()) {
			activeDoingColumnController.getColumnHeaderController().getColumnData().setType("optional");
			activeDoingColumnController.getColumnHeaderController().getColumnData().setMaxTasks(ProjectController.MAX_DEFAULT_TASKS);
			result.get().setType("doing");
			this.saveProject();
		}
	}
	
	private ColumnController getActiveDoingColumn() {
		Iterator<ColumnController> columnControllerIterator = columnListController.getColumnControllers().iterator();
		while(columnControllerIterator.hasNext()) {
			ColumnController activeColumnController = columnControllerIterator.next();
			if(activeColumnController.getColumnHeaderController().getColumnData().getType().equals("doing")) {
				return activeColumnController;
			}
		}
		return null;
	}
	
	private void showHistory() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../views/HistoryView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("History");
            stage.setScene(new Scene(root, 450, 800));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }		
	}
	
	private void showStatistics() {
		// Create the custom dialog.
		Dialog<Pair<LocalDate, LocalDate>> dialog = new Dialog<>();
		dialog.setTitle("Choose Period for Statistics");
		dialog.setHeaderText(null);
		
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		DatePicker fromDate = new DatePicker();
		fromDate.setValue(LocalDate.now());
		DatePicker toDate = new DatePicker();
		toDate.setValue(LocalDate.now());

		grid.add(new Label("From:"), 0, 0);
		grid.add(fromDate, 1, 0);
		grid.add(new Label("To:"), 0, 1);
		grid.add(toDate, 1, 1);

		dialog.getDialogPane().setContent(grid);

		// Request focus on the fromDate field by default.
		Platform.runLater(() -> fromDate.requestFocus());

		// Convert the result to a fromTime-ToTime Pair when OK Button clicked
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == ButtonType.OK) {
		        return new Pair<>(fromDate.getValue(), toDate.getValue());
		    }
		    return null;
		});

		Optional<Pair<LocalDate, LocalDate>> result = dialog.showAndWait();

		result.ifPresent(fromToDate -> {
			Date from = Date.from(fromToDate.getKey().atStartOfDay(ZoneId.systemDefault()).toInstant());
			String fromString = new SimpleDateFormat("dd.MM.yyyy").format(from);
			Date to = Date.from(fromToDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			String toString = new SimpleDateFormat("dd.MM.yyyy").format(to);
			
			int tasksCreated = 0;
			int tasksMoved = 0;
			int tasksFinished = 0;
			Iterator<TaskData> taskDataIterator = this.getProjectData().getTasksData().iterator();
			while(taskDataIterator.hasNext()) {
				TaskData activeTaskData = taskDataIterator.next();
				if(activeTaskData.getChangeLog() != null && !activeTaskData.getChangeLog().isEmpty()) {
					//search first changeLog (initial changeLog when Task created) and compare Timestamp
					if(activeTaskData.getChangeLog().get(0).getTimestamp() > from.getTime() && activeTaskData.getChangeLog().get(0).getTimestamp() < to.getTime()) {
						tasksCreated++;
					}
					//search last changeLog, compare timestamp to given fromToDate, compare columnName with "Done" (hardcoded -> doesn't work when Last Column Name is changed)
					if(activeTaskData.getChangeLog().get(activeTaskData.getChangeLog().size()-1).getTimestamp() > from.getTime() && activeTaskData.getChangeLog().get(activeTaskData.getChangeLog().size()-1).getTimestamp() < to.getTime() && activeTaskData.getChangeLog().get(activeTaskData.getChangeLog().size()-1).getColumnName().equals("Done")) {
						tasksFinished++;
					}
					//if Task is moved minimum 1 time
					if(activeTaskData.getChangeLog().size() > 1) {
						Iterator<TaskChangeLog> changeLogIterator = activeTaskData.getChangeLog().iterator();
						changeLogIterator.next(); //skip create changeLog
						while(changeLogIterator.hasNext()) {
							TaskChangeLog activeChangeLog = changeLogIterator.next();
							//compare Movement Timestamp to given Time
							if(activeChangeLog.getTimestamp() > from.getTime() && activeChangeLog.getTimestamp() < to.getTime()) {
								tasksMoved++;
							}
						}
					}
				}
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Statistics from " + fromString + " to " + toString);
			alert.setContentText("Tasks created: " + tasksCreated + "\nTasks moved: " + tasksMoved + "\nTasks finished: " + tasksFinished);

			alert.showAndWait();
		});		
	}

	//---------------------------------------------------------------------------
	//--------------------Getter and Setter--------------------------------------
	//---------------------------------------------------------------------------
	
	public MenuView getMenuView() {
		return menuView;
	}

	public ColumnListController getColumnListController() {
		return columnListController;
	}

	public void setMenuView(MenuView menuView) {
		this.menuView = menuView;
	}

	public void setColumnListController(ColumnListController columnListController) {
		this.columnListController = columnListController;
	}

}
