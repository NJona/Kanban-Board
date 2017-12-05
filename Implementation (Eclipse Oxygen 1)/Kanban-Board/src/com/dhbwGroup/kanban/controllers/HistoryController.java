package com.dhbwGroup.kanban.controllers;

import java.net.URL;
import java.util.ListIterator;
import java.util.ResourceBundle;

import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.services.KanbanService;
import com.dhbwGroup.kanban.views.Task;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

public class HistoryController implements Initializable {
	@FXML private ScrollPane historyScrollPane;
	
	private VBox historyVBox;
	
	private KanbanService kanbanService;
	
	private Project project;
	
	private TaskController taskController;
	private CategoryController categoryController;
	
	public HistoryController() {
		kanbanService = new KanbanService();
		project = kanbanService.loadProject();
		categoryController = new CategoryController(kanbanService, project);
		taskController = new TaskController(categoryController, kanbanService, project);
		categoryController.initializeData(project.getCategoriesData());
		taskController.createTaskViews(project.getTasksData());
	}
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    	historyScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
    	historyVBox = new VBox();
    	historyScrollPane.setContent(historyVBox);
		ListIterator<Task> taskIterator = taskController.getTasks().listIterator(taskController.getTasks().size());
		while(taskIterator.hasPrevious()) {
			Task activeTask = taskIterator.previous();
			activeTask.getEditSaveButton().setVisible(false);
			activeTask.getShowHistoryButton().setVisible(true);
    		historyVBox.getChildren().add(activeTask.getTaskGridPane());
		}
    }
}
