package com.dhbwGroup.kanban.views;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MenuView {
	private HBox menuHBox;
	
	private Button addColumnButton;
	private Button addTaskButton;
	private Button addCategoryButton;
	private Button changeDoingColumnButton;
	private Button showHistoryButton;
	private Button showStatisticsButton;
	
	public MenuView() {
		menuHBox = new HBox();
		
        addColumnButton = new Button("Add Column");
        addTaskButton = new Button("Add Task");
        addCategoryButton = new Button("Add Category");
        changeDoingColumnButton = new Button("Change Doing Column");
        showHistoryButton = new Button("Show old Tasks");
        showStatisticsButton = new Button("Show Statistics");
        
        initialize();
	}

	private void initialize() {
		addStyleClasses();
		menuHBox.getChildren().addAll(addColumnButton, addTaskButton, addCategoryButton, changeDoingColumnButton, showHistoryButton, showStatisticsButton);
	}

	private void addStyleClasses() {
		menuHBox.getStyleClass().add("menuHBox");
		addColumnButton.getStyleClass().add("menuAddColumnButton");
		addTaskButton.getStyleClass().add("menuAddTaskButton");
		addCategoryButton.getStyleClass().add("menuAddCategoryButton");
		changeDoingColumnButton.getStyleClass().add("menuChangeDoingColumnButton");
		showHistoryButton.getStyleClass().add("menuShowHistoryButton");
		showStatisticsButton.getStyleClass().add("menuShowStatisticsButton");
	}
	

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------

	public HBox getMenuHBox() {
		return menuHBox;
	}

	public Button getAddColumnButton() {
		return addColumnButton;
	}

	public Button getAddTaskButton() {
		return addTaskButton;
	}

	public Button getAddCategoryButton() {
		return addCategoryButton;
	}

	public Button getChangeDoingColumnButton() {
		return changeDoingColumnButton;
	}

	public Button getShowHistoryButton() {
		return showHistoryButton;
	}

	public Button getShowStatisticsButton() {
		return showStatisticsButton;
	}

	public void setMenuHBox(HBox menuHBox) {
		this.menuHBox = menuHBox;
	}

	public void setAddColumnButton(Button addColumnButton) {
		this.addColumnButton = addColumnButton;
	}

	public void setAddTaskButton(Button addTaskButton) {
		this.addTaskButton = addTaskButton;
	}

	public void setAddCategoryButton(Button addCategoryButton) {
		this.addCategoryButton = addCategoryButton;
	}

	public void setChangeDoingColumnButton(Button changeDoingColumnButton) {
		this.changeDoingColumnButton = changeDoingColumnButton;
	}

	public void setShowHistoryButton(Button showHistoryButton) {
		this.showHistoryButton = showHistoryButton;
	}

	public void setShowStatisticsButton(Button showStatisticsButton) {
		this.showStatisticsButton = showStatisticsButton;
	}
}
