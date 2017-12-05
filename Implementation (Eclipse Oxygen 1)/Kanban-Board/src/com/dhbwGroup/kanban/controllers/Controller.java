package com.dhbwGroup.kanban.controllers;




import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
import com.dhbwGroup.kanban.services.KanbanService;
import com.dhbwGroup.kanban.views.Column;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Controller implements Initializable {
	@FXML private BorderPane fullBorderPane;
	
	private HBox menuHBox;
	
	private Button addColumnButton;
	private Button addTaskButton;
	private Button addCategoryButton;
	private Button changeDoingColumnButton;
	private Button showHistoryButton;
	
	
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
        menuHBox.getChildren().addAll(addColumnButton, addTaskButton, addCategoryButton, changeDoingColumnButton, showHistoryButton);
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
