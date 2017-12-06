package com.dhbwGroup.kanban.controllers;

import java.io.IOException;

import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.views.ProjectView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.DataFormat;

public class ProjectController extends Controller{
	private ProjectView projectView;
	
	private ColumnListController columnListController;
	private MenuController menuController;
	
	public final static int MAXCOLUMNS = 10;
	public final static int MAX_DEFAULT_TASKS = 8;
	public final static int MAX_ALLOWED_CHARS_IN_TITEL = 20;
	public final static int MIN_COLUMNS = 3;
	public static final int MAX_TASKS_IN_HISTORY = 10000;
	public final static DataFormat TASK_DATA_FORMAT = new DataFormat("com.dhbwGroup.kanban.models.TaskData");
	
	public ProjectController() {
		super();
		this.loadProject();
		columnListController = new ColumnListController(this.getProjectData());
		menuController = new MenuController(this.getProjectData(), this.columnListController);
		initialize();
	}
	
	private void initialize() {
		projectView = new ProjectView();
		
		if(!columnListController.getColumnControllers().isEmpty()) {
			columnListController.getColumnControllers().forEach((activeColumnController) -> {
				projectView.getProjectHBox().getChildren().add(activeColumnController.getColumnView().getColumnVBox());
	    	});	
		}
		columnListController.getColumnControllers().forEach((activeColumnController) -> {
			activeColumnController.getColumnHeaderController().getColumnHeaderView().getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
	    	    @Override public void handle(ActionEvent e) {
	    	    	removeColumn(activeColumnController);
	    	    }
			});
		});
		menuController.getMenuView().getAddColumnButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
         	try {
					addColumn();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ColumnNotEmptyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MinColumnsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
     });
	}
	
	private void removeColumn(ColumnController columnControllerToRemove) {
		try {
			columnListController.handleRemoveColumn(columnControllerToRemove);
			projectView.getProjectHBox().getChildren().remove(columnControllerToRemove.getColumnView().getColumnVBox());
		} catch (ColumnNotEmptyException e) {
			e.printStackTrace();
		} catch (MinColumnsException e) {
			e.printStackTrace();
		}		
	}
	
	private void addColumn() throws IOException, ColumnNotEmptyException, MinColumnsException{
		if(this.columnListController.getColumnControllers().size() <= ProjectController.MAXCOLUMNS) {
			ColumnController columnToAdd = columnListController.createNewColumn("new Column");
			projectView.getProjectHBox().getChildren().add(columnListController.getColumnControllers().indexOf(columnToAdd), columnToAdd.getColumnView().getColumnVBox());
			columnToAdd.getColumnHeaderController().getColumnHeaderView().getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	removeColumn(columnToAdd);
    	    }
		});
			if(this.columnListController.getColumnControllers().size() == MAXCOLUMNS) {
				menuController.getMenuView().getAddColumnButton().setDisable(true);
			}
		}
    }

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public ProjectView getProjectView() {
		return projectView;
	}

	public void setProjectView(ProjectView projectView) {
		this.projectView = projectView;
	}

	public ColumnListController getColumnListController() {
		return columnListController;
	}

	public MenuController getMenuController() {
		return menuController;
	}

	public void setColumnListController(ColumnListController columnListController) {
		this.columnListController = columnListController;
	}

	public void setMenuController(MenuController menuController) {
		this.menuController = menuController;
	}
}
