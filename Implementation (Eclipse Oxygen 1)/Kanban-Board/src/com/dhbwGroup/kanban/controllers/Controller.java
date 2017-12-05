package com.dhbwGroup.kanban.controllers;




import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import com.dhbwGroup.kanban.exceptions.CategoryNameAlreadyTakenException;
import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.exceptions.TooManyTasksInActiveDoingColumn;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.models.TaskChangeLog;
import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.services.KanbanService;
import com.dhbwGroup.kanban.views.Column;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;


public class Controller implements Initializable {
	@FXML private BorderPane fullBorderPane;
	
	private HBox menuHBox;
	
	private Button addColumnButton;
	private Button addTaskButton;
	private Button addCategoryButton;
	private Button changeDoingColumnButton;
	private Button showHistoryButton;
	private Button showStatisticsButton;
	
	private ScrollPane scrollPane;
	private HBox columnHBox;
	
	private Project project;
	
	private KanbanService kanbanService;

	private ColumnController columnController;
	private CategoryController categoryController;
	
	public final static int MAXCOLUMNS = 10;
	public final static int MAX_DEFAULT_TASKS = 8;
	public final static int MAX_ALLOWED_CHARS = 15;
	public final static int MIN_COLUMNS = 3;
	public static final int MAX_TASKS_IN_HISTORY = 3;
	
	public Controller() throws FileNotFoundException {
		kanbanService = new KanbanService();
		project = kanbanService.loadProject();
		categoryController = new CategoryController(kanbanService, project);
		columnController = new ColumnController(categoryController, kanbanService, project);
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
		initializeBoard();
		initializeColumnAndTasks();
    }
    
    private void initializeBoard() {
    	fullBorderPane.getStyleClass().add("fullBorderPane");
    	initializeMenu();
        
    	initializeScrollPane();
    	
        fullBorderPane.setCenter(scrollPane);
        fullBorderPane.getCenter().getStyleClass().add("borderPaneCenter");
    	
        columnHBox = new HBox();
        
        scrollPane.setContent(columnHBox);
        
        columnHBox.setFillHeight(true);
        columnHBox.getStyleClass().add("columnsHBox");    	
    }
    
    private void initializeScrollPane() {
        scrollPane = new ScrollPane();
    	scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	scrollPane.getStyleClass().add("scrollPane");    	
    }

    
  //-----------------------------------------------------------------------------------	
  //-------------------------------Menu Bar--------------------------------------------
  //-----------------------------------------------------------------------------------
    
	private void initializeMenu() {
		createMenuHBox();
		createMenuButtons();  
		fullBorderPane.setTop(menuHBox);
	}
	
	private void createMenuHBox() {
		menuHBox = new HBox();
	}

	private void createMenuButtons() {
        addColumnButton = new Button("Add Column");
        addColumnButton.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
            	try {
					addColumn();
				} catch (IOException e) {
					e.printStackTrace();
				}
               }
        }); 
        addTaskButton = new Button("Add Task");
        addTaskButton.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					addTask();
               }
        });
        addCategoryButton = new Button("Add Category");
        addCategoryButton.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					addCategory();
               }
        });
        changeDoingColumnButton = new Button("Change Doing Column");
        changeDoingColumnButton.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					try {
						changeDoingColumn();
					} catch (TooManyTasksInActiveDoingColumn e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               }
        });
        showHistoryButton = new Button("Show old Tasks");
        showHistoryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
						showHistory();
            }
        });
        showStatisticsButton = new Button("Show Statistics");
        showStatisticsButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
						showStatistics();
            }
        });
        menuHBox.getChildren().addAll(addColumnButton, addTaskButton, addCategoryButton, changeDoingColumnButton, showHistoryButton, showStatisticsButton);
	}
	
	private void initializeColumnAndTasks() {
		categoryController.initializeData(project.getCategoriesData());
		columnController.createColumnViews(project.getColumnsData());
		columnController.getTaskController().createTaskViews(project.getTasksData());
		columnController.addEachTaskViewToColumnView();
    	addEachColumnViewToMainHBox();
    	createEventHandlerForRemoveColumnButton();
	}

	protected void addColumn() throws IOException{
		if(columnController.getColumns().size() <= MAXCOLUMNS) {
			Column columnToAdd = columnController.createNewColumnDataAndColumnView("new Column");
			columnHBox.getChildren().add(columnController.getColumns().indexOf(columnToAdd), columnToAdd.getColumnGridPane());
			columnToAdd.getColumnHeaderGridPane().getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	removeColumn(columnToAdd);
    	    }
		});
			if(columnController.getColumns().size() == MAXCOLUMNS) {
				addColumnButton.setDisable(true);
			}
		}
    }
	
	protected void addTask(){
		addTaskButton.setDisable(columnController.addTask());
    }
	
	private void addCategory() {
		TextInputDialog dialog = new TextInputDialog("Category Title");
		dialog.setTitle("Add Category!");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter a category:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent((title) -> {
			try {
				CategoryData newCategoryData = categoryController.addCategoryData(title);
				columnController.getTaskController().getTasks().forEach((activeTask) -> {
					activeTask.addNewCategoryToDropDown(newCategoryData);
				});
			} catch (CategoryNameAlreadyTakenException e) {
				
			}
		});
	}
	
	private void changeDoingColumn() throws TooManyTasksInActiveDoingColumn {
		Column activeDoingColumn = getActiveDoingColumn();
		if(activeDoingColumn == null) {
			try {
				activeDoingColumn = columnController.getColumns().get(1);
			} catch (NullPointerException e) {
				if(columnController.getColumnsData().isEmpty()) {
					e.printStackTrace();
				}
					return;
			}			
		}
		
		if(activeDoingColumn.getColumnTaskVBox().getColumnTaskVBox().getChildren().size() > MAX_DEFAULT_TASKS)
			throw new TooManyTasksInActiveDoingColumn();
		
		ChoiceDialog<ColumnData> dialog = new ChoiceDialog<>(activeDoingColumn.getColumnData(), columnController.getColumnsData().subList(1, columnController.getColumnsData().size()-1));
		dialog.setTitle("Change Doing Column!");
		dialog.setHeaderText(null);
		dialog.setContentText("Please choose a Column:");

		Optional<ColumnData> result = dialog.showAndWait();
		if(result.isPresent()) {
			activeDoingColumn.getColumnData().setType("optional");
			activeDoingColumn.getColumnData().setMaxTasks(MAX_DEFAULT_TASKS);
			result.get().setType("doing");
			kanbanService.saveProject(project);
		}
	}
	
	private Column getActiveDoingColumn() {
		Iterator<Column> columnIterator = columnController.getColumns().iterator();
		while(columnIterator.hasNext()) {
			Column activeColumn = columnIterator.next();
			if(activeColumn.getColumnData().getType().equals("doing")) {
				return activeColumn;
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
			Iterator<TaskData> taskDataIterator = columnController.getTaskController().getTasksData().iterator();
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
    
//-----------------------------------------------------------------------------------	
//-------------------------------Add Views to GridPane-------------------------------
//-----------------------------------------------------------------------------------

	public void addEachColumnViewToMainHBox() {
		if(!columnController.getColumns().isEmpty()) {
			columnController.getColumns().forEach((activeColumn) -> {
				columnHBox.getChildren().add(activeColumn.getColumnGridPane());
	    	});	
		}
	}
	
	private void createEventHandlerForRemoveColumnButton() {		
		columnController.getColumns().forEach((activeColumn) -> {
			activeColumn.getColumnHeaderGridPane().getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	removeColumn(activeColumn);
    	    }
		});
		});
	}
	
	private void removeColumn(Column columnToRemove) {
		try {
			columnController.handleRemoveColumn(columnToRemove);
			columnHBox.getChildren().remove(columnToRemove.getColumnGridPane());
		} catch (ColumnNotEmptyException e) {
			e.printStackTrace();
		} catch (MinColumnsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
