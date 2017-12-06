package com.dhbwGroup.kanban;

import java.net.URL;
import java.util.ResourceBundle;

import com.dhbwGroup.kanban.controllers.ProjectController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public class ProjectInitializer implements Initializable {
	@FXML private BorderPane fullBorderPane;
	
	ProjectController projectController;
	
	public ProjectInitializer() {
		projectController = new ProjectController();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.fullBorderPane.setCenter(projectController.getProjectView().getProjectScrollPane());
		this.fullBorderPane.setTop(projectController.getMenuController().getMenuView().getMenuHBox());
	}
}
