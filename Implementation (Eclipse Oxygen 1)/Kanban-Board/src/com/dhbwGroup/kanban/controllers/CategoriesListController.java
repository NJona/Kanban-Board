package com.dhbwGroup.kanban.controllers;

import com.dhbwGroup.kanban.exceptions.CategoryNameAlreadyTakenException;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.Project;

public class CategoriesListController extends Controller{

	public CategoriesListController(Project projectData) {
		super();
		this.setProjectData(projectData);
	}
	
	public CategoryData addCategoryData(String title, String color) throws CategoryNameAlreadyTakenException{
		checkIfTitleAlreadyTaken(title);
		CategoryData newCategoryData = new CategoryData(title, color);
		this.getProjectData().getCategoriesData().add(newCategoryData);
		this.saveProject();
		return newCategoryData;
	}
	
	private void checkIfTitleAlreadyTaken(String newCategoryTitle) throws CategoryNameAlreadyTakenException {
		boolean isTitleAlreadyTaken = this.getProjectData().getCategoriesData().stream().anyMatch(activeCategory -> activeCategory.getTitle().equals(newCategoryTitle));
		if(isTitleAlreadyTaken) {
			throw new CategoryNameAlreadyTakenException();
		}
	}
	
	public CategoryData addCategoryData(String title) throws CategoryNameAlreadyTakenException {
		checkIfTitleAlreadyTaken(title);
		CategoryData newCategoryData = new CategoryData(title);
		this.getProjectData().getCategoriesData().add(newCategoryData);
		this.saveProject();
		return newCategoryData;
	}
	
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------

}
