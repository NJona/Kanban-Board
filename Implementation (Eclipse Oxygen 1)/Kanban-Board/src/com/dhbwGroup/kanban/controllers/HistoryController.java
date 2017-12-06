package com.dhbwGroup.kanban.controllers;

import java.net.URL;
import java.util.ListIterator;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

public class HistoryController extends Controller implements Initializable {
	@FXML private ScrollPane historyScrollPane;
	
	private VBox historyVBox;
	
	private TaskListController taskListController;
	
	public HistoryController() {
		super();
		this.loadProject();
		taskListController = new TaskListController(this.getProjectData());
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    	historyScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	historyVBox = new VBox();
    	historyScrollPane.setContent(historyVBox);
		ListIterator<TaskController> taskControllerIterator = taskListController.getTaskControllers().listIterator(taskListController.getTaskControllers().size());
		while(taskControllerIterator.hasPrevious()) {
			TaskController activeTaskController = taskControllerIterator.previous();
			activeTaskController.getTaskView().getEditSaveButton().setVisible(false);
			activeTaskController.getTaskView().getShowHistoryButton().setVisible(true);
    		historyVBox.getChildren().add(activeTaskController.getTaskView().getTaskGridPane());
		}
    }
}
