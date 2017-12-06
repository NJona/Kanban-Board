package com.dhbwGroup.kanban.controllers;

import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.views.ColumnView;

public class ColumnController extends Controller{

	private ColumnView columnView;
	
	private ColumnHeaderController columnHeaderController;
	private ColumnTaskListController columnTaskListController;
	
	
	public ColumnController(Project projectData, ColumnData columnData, TaskListController taskListController) {
		super();
		this.setProjectData(projectData);
		columnHeaderController = new ColumnHeaderController(projectData, columnData);
		columnTaskListController = new ColumnTaskListController(projectData, columnData, taskListController);
		initialize();
	}


	private void initialize() {
		columnView = new ColumnView();
		
		columnView.getColumnVBox().getChildren().add(columnHeaderController.getColumnHeaderView().getColumnHeaderHBox());
		columnView.getColumnVBox().getChildren().add(columnTaskListController.getColumnTaskListView().getColumnTaskVBox());
	}

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public ColumnView getColumnView() {
		return columnView;
	}


	public ColumnHeaderController getColumnHeaderController() {
		return columnHeaderController;
	}


	public ColumnTaskListController getColumnTaskListController() {
		return columnTaskListController;
	}


	public void setColumnView(ColumnView columnView) {
		this.columnView = columnView;
	}


	public void setColumnHeaderController(ColumnHeaderController columnHeaderController) {
		this.columnHeaderController = columnHeaderController;
	}


	public void setColumnTaskListController(ColumnTaskListController columnTaskListController) {
		this.columnTaskListController = columnTaskListController;
	}

}
