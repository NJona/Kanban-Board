package com.dhbwGroup.kanban.controllers;

import java.util.List;

import com.dhbwGroup.kanban.exceptions.CategoryNameAlreadyTakenException;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.services.KanbanService;

public class CategoryController {

	private List<CategoryData> categoriesData;
	
	private KanbanService kanbanService;
	
	private Project project;

	public CategoryController(KanbanService kanbanService, Project project) {
		this.kanbanService = kanbanService;
		this.project = project;
	}
	
	public void initializeData(List<CategoryData> categoryData) {
		this.categoriesData = categoryData;
	}
	
	public CategoryData addCategoryData(String title, String color) throws CategoryNameAlreadyTakenException{
		checkIfTitleAlreadyTaken(title);
		CategoryData newCategoryData = new CategoryData(title, color);
		categoriesData.add(newCategoryData);
		kanbanService.saveProject(project);
		return newCategoryData;
	}
	
	private void checkIfTitleAlreadyTaken(String newCategoryTitle) throws CategoryNameAlreadyTakenException {
		boolean isTitleAlreadyTaken = categoriesData.stream().anyMatch(activeCategory -> activeCategory.getTitle().equals(newCategoryTitle));
		if(isTitleAlreadyTaken) {
			throw new CategoryNameAlreadyTakenException();
		}
	}
	
	public CategoryData addCategoryData(String title) throws CategoryNameAlreadyTakenException {
		checkIfTitleAlreadyTaken(title);
		CategoryData newCategoryData = new CategoryData(title);
		categoriesData.add(newCategoryData);
		kanbanService.saveProject(project);
		return newCategoryData;
	}
	
	
//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	
	public List<CategoryData> getCategoryData() {
		return categoriesData;
	}

	public void setCategoryData(List<CategoryData> categoriesData) {
		this.categoriesData = categoriesData;
	}
}
