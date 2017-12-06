package com.dhbwGroup.kanban.controllers;

import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.services.KanbanService;

public class Controller {
	private Project projectData;
	private KanbanService kanbanService;
	
	public Controller() {
		kanbanService = new KanbanService();
	}
	
	protected void loadProject() {
		this.projectData = kanbanService.loadProject();
	}
	
	protected void saveProject() {
		kanbanService.saveProject(this.projectData);
	}

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------		
	
	protected Project getProjectData() {
		return projectData;
	}

	protected void setProjectData(Project projectData) {
		this.projectData = projectData;
	}
	
}
