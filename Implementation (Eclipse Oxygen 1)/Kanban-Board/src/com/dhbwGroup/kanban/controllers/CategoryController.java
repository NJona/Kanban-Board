package com.dhbwGroup.kanban.controllers;

import java.util.List;

import com.dhbwGroup.kanban.models.CategoryData;

public class CategoryController {

	private List<CategoryData> categoriesData;

	public CategoryController() {
		
	}
	
	public void initializeData(List<CategoryData> categoryData) {
		this.categoriesData = categoryData;
	}
	
	public CategoryData addCategoryData(String title, String color) {
		CategoryData newCategoryData = new CategoryData(title, color);
		categoriesData.add(newCategoryData);
		return newCategoryData;
	}
	
	public CategoryData addCategoryData(String title) {
		CategoryData newCategoryData = new CategoryData(title);
		categoriesData.add(newCategoryData);
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
