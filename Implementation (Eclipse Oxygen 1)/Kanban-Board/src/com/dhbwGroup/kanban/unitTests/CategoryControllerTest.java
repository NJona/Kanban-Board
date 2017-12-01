package com.dhbwGroup.kanban.unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.dhbwGroup.kanban.controllers.CategoryController;
import com.dhbwGroup.kanban.exceptions.CategoryNameAlreadyTakenException;
import com.dhbwGroup.kanban.models.CategoryData;

class CategoryControllerTest {

	/*
	 *  Tests für den Category Controller
	 * 	
	 *  1. initialize Data
	 *  2. Add Data 
	 *  
	 *  Bei den Exceptions muss der GUI Teil auskommentiert werden
	 * 
	 */
	@Test
	void initializeDataTest1() {
		
		//Controler und Liste initialisieren
		List<CategoryData> categoriesData = new ArrayList<CategoryData>();
		
		CategoryData cData1 = new CategoryData("TestData");
		categoriesData.add(cData1);
		
		CategoryController categoryController = new CategoryController();
		categoryController.initializeData(categoriesData);
		
		//Initialisierte Liste abgleichen
		
		assertEquals("TestData", categoryController.getCategoryData().get(0).getTitle(), "initializeDataTest1");
		
	}
	
	/*
	 *  add Data mit Name
	 */
	@Test
	void addDataTest1() {
		
		//Liste initialisieren
		List<CategoryData> categoriesData = new ArrayList<CategoryData>();
		
		CategoryData cData1 = new CategoryData("TestData");
		categoriesData.add(cData1);
		
		CategoryController categoryController = new CategoryController();
		categoryController.initializeData(categoriesData);
		
		//Neue Kategorie hinzufügen
		try {
			categoryController.addCategoryData("TestDataTwo");
		} catch (CategoryNameAlreadyTakenException e) {
			e.printStackTrace();
		}
		
		//Liste abgleichen
		
		assertEquals("TestDataTwo", categoryController.getCategoryData().get(1).getTitle(),"addDataTest1-1");
		
		// Neue Kategorie mit gleichem Namen hinzufügen
		
		assertThrows(CategoryNameAlreadyTakenException.class , ()-> {
			categoryController.addCategoryData("TestDataTwo");
		});
		
	}
	
	
	/*
	 * Add Data mit Name und Farbe 
	 */
	@Test
	void addDataTest2() {
		
		//Liste initialisieren
		List<CategoryData> categoriesData = new ArrayList<CategoryData>();
				
		CategoryData cData1 = new CategoryData("TestData");
		categoriesData.add(cData1);
				
		CategoryController categoryController = new CategoryController();
		categoryController.initializeData(categoriesData);
		
		//Kategorie hinzufügen
		try {
			categoryController.addCategoryData("TestDataTwo", "green");
		} catch (CategoryNameAlreadyTakenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Liste abgleichen
		
		assertEquals("TestDataTwo", categoryController.getCategoryData().get(1).getTitle(),"addDataTest2-1");
		assertEquals("green", categoryController.getCategoryData().get(1).getColor(),"addDataTest2-2");
		
		//Neue Kategorie mit gleichem Namen hinzufügen
		
		assertThrows(CategoryNameAlreadyTakenException.class , ()-> {
			categoryController.addCategoryData("TestDataTwo");
		});
		
	}

}
