package com.dhbwGroup.kanban.controllers;




import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.dhbwGroup.kanban.exceptions.CategoryNameAlreadyTakenException;
import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.services.KanbanService;
import com.dhbwGroup.kanban.views.Column;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Controller implements Initializable {
	@FXML private BorderPane fullBorderPane;
	
	private MenuBar menuBar;
	
	private Menu file;
	private Menu edit;
	
	private MenuItem newFile;
	private MenuItem openFile;
	private MenuItem saveFile;
	private MenuItem addColumn;
	private MenuItem addTask;
	private MenuItem addCategory;
	
	
	private ScrollPane scrollPane;
	private HBox columnHBox;
	
	private Project project;
	
	private KanbanService kanbanService;

	private ColumnController columnController;
	private CategoryController categoryController;
	
	final static int MAXCOLUMNS = 10;
	
	public Controller() throws FileNotFoundException {
		categoryController = new CategoryController();
		columnController = new ColumnController(categoryController);
		kanbanService = new KanbanService();
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
        this.openFile();
    }
    
    private void initializeBoard() {
    	fullBorderPane.getStyleClass().add("fullBorderPane");
    	initializeMenu();
        
    	initializeScrollPane();
    	
        fullBorderPane.setCenter(scrollPane);
    	
        columnHBox = new HBox();
        
        scrollPane.setContent(columnHBox);
        
        columnHBox.getStyleClass().add("columnsHBox");    	
    }
    
    private void initializeScrollPane() {
        scrollPane = new ScrollPane();
    	scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
    	scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
    	scrollPane.getStyleClass().add("scrollPane");    	
    }

    
  //-----------------------------------------------------------------------------------	
  //-------------------------------Menu Bar--------------------------------------------
  //-----------------------------------------------------------------------------------
    
	private void initializeMenu() {
		createMenuBar();
		createMenus();
		createMenuItems();  
		fullBorderPane.setTop(menuBar);
	}
	
	private void createMenuBar() {
		menuBar = new MenuBar();
	}

	private void createMenus() {
        file = new Menu("File");
        edit = new Menu("Edit");
        
        menuBar.getMenus().addAll(file, edit);
	}

	private void createMenuItems() {
		//----------File Menu----------------
        newFile = new MenuItem("New");
        newFile.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
                   createNewFile();
               }
        });
        openFile = new MenuItem("Open");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
                   openFile();
               }
        });
        saveFile = new MenuItem("Save");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
                   saveFile();
               }
        });
        file.getItems().addAll(newFile, openFile, saveFile);
        
        //----------Edit Menu----------------
        addColumn = new MenuItem("Add Column");
        addColumn.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
            	try {
					addColumn();
				} catch (IOException e) {
					e.printStackTrace();
				}
               }
        }); 
        addTask = new MenuItem("Add Task");
        addTask.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					addTask();
               }
        });
        addCategory = new MenuItem("Add Category");
        addCategory.setOnAction(new EventHandler<ActionEvent>() {
               public void handle(ActionEvent t) {
					addCategory();
               }
        });
        edit.getItems().addAll(addColumn, addTask, addCategory);
	}

	private void createNewFile() {
		columnHBox = new HBox();
		scrollPane.setContent(columnHBox);
		columnHBox.getStyleClass().add("columnHBox");
		project = kanbanService.createNewBoard();
		initializeColumnAndTasks();
	}	
	
	private void openFile() {
		initializeBoard();
		project = kanbanService.loadProject();
		initializeColumnAndTasks();
	}
	
	private void initializeColumnAndTasks() {
		categoryController.initializeData(project.getCategoriesData());
		columnController.createColumnViews(project.getColumnsData());
		columnController.getTaskController().createTaskViews(project.getTasksData());
		columnController.addEachTaskViewToColumnView();
    	addEachColumnViewToMainHBox();
    	createEventHandlerForRemoveColumnButton();
	}
	
	protected void saveFile() {
		kanbanService.saveProject(project);
	}
	
	protected void addColumn() throws IOException{
		if(columnController.getColumns().size() <= MAXCOLUMNS) {
			Column columnToAdd = columnController.addColumn("new Column");
			columnHBox.getChildren().add(columnController.getColumns().indexOf(columnToAdd), columnToAdd.getColumnGridPane());
			columnToAdd.getColumnHeaderGridPane().getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	removeColumn(columnToAdd);
    	    }
		});
			if(columnController.getColumns().size() == MAXCOLUMNS) {
				addColumn.setDisable(true);
			}
		}
    }
	
	protected void addTask(){
		addTask.setDisable(columnController.addTask());
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
