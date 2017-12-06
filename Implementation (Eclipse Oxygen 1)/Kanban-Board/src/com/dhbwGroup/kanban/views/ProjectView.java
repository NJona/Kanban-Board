package com.dhbwGroup.kanban.views;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;

public class ProjectView {
	private ScrollPane projectScrollPane;
	private HBox projectHBox;
	
	public ProjectView() {
		projectScrollPane = new ScrollPane();
		projectHBox = new HBox();
		initialize();
	}	

	private void initialize() {
		projectHBox.setFillHeight(true);
		projectScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		projectScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		projectScrollPane.setContent(projectHBox);
		
		addStyleClasses();
	}
	
	private void addStyleClasses() {
		projectScrollPane.getStyleClass().add("scrollPane");
		projectHBox.getStyleClass().add("projectHBox");
	}

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	public ScrollPane getProjectScrollPane() {
		return projectScrollPane;
	}
	public HBox getProjectHBox() {
		return projectHBox;
	}
	public void setProjectScrollPane(ScrollPane projectScrollPane) {
		this.projectScrollPane = projectScrollPane;
	}
	public void setProjectHBox(HBox projectHBox) {
		this.projectHBox = projectHBox;
	}
}
