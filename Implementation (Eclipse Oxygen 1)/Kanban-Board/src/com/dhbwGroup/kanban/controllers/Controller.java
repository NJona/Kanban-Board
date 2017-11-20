package com.dhbwGroup.kanban.controllers;




import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
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
	
	
	private ScrollPane scrollPane;
	private HBox columnHBox;
	
	private Project project;
	
	private KanbanService kanbanService;

	private ColumnController columnController;
	
	final static int MAXCOLUMNS = 10;
	
	public Controller() throws FileNotFoundException {
		columnController = new ColumnController();
		kanbanService = new KanbanService();
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {   	
        this.openFile();
    }
    
    private void initializeBoard() {
    	initializeMenu();
        
    	initializeScrollPane();
        
        fullBorderPane.setCenter(scrollPane);
    	
        columnHBox = new HBox();
        
        scrollPane.setContent(columnHBox);
        
        columnHBox.getStyleClass().add("columnHBox");    	
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
        edit.getItems().addAll(addColumn, addTask);
	}

	private void createNewFile() {
		columnHBox = new HBox();
		scrollPane.setContent(columnHBox);
		columnHBox.getStyleClass().add("columnHBox");
		project = kanbanService.createNewBoard();
		initializeColumnAndTasks();
	}	
	
	private void openFile() {
		columnController = new ColumnController();
		kanbanService = new KanbanService();
		initializeBoard();
		project = kanbanService.loadProject();
		initializeColumnAndTasks();
	}
	
	private void initializeColumnAndTasks() {
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
			columnToAdd.getColumnHeaderGridPane().getColumnGridPaneElementRemoveButton().getButton().setOnAction(new EventHandler<ActionEvent>() {
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
			activeColumn.getColumnHeaderGridPane().getColumnGridPaneElementRemoveButton().getButton().setOnAction(new EventHandler<ActionEvent>() {
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
