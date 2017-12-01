package com.dhbwGroup.kanban.unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.dhbwGroup.kanban.controllers.CategoryController;
import com.dhbwGroup.kanban.controllers.TaskController;
import com.dhbwGroup.kanban.exceptions.CategoryNameAlreadyTakenException;
import com.dhbwGroup.kanban.models.CategoryData;
import com.dhbwGroup.kanban.models.TaskData;

class TaskControllerTest {

	/*
	 *  Test für den Task Controller
	 * 	
	 * 	1. createTaskViews
	 * 	2. createNewTaskDataAndTaskView
	 * 	3. createNewTaskView
	 * 
	 */
	
	/*
	 *  Test momentan nur in abgespeckter Version möglich, da auf den view nicht zugegriffen werden kann
	 *  Entsprechend Teile in TaskController auskommentiert
	 *  Test auf createTaskViews
	 */
	@Test
	void createTaskViewsTest1() {
		
		// Controller initialisieren
		List<CategoryData> categoriesData = new ArrayList<CategoryData>();
		CategoryData cData1 = new CategoryData("test");
		categoriesData.add(cData1);
		
		CategoryController categoryController = new CategoryController();
		categoryController.initializeData(categoriesData);
		
		
		TaskController taskController = new TaskController(categoryController);
		
		List<TaskData> tasksData = new ArrayList<TaskData>();
		TaskData tData1 = new TaskData();
		tData1.setTitle("TestTask");
		tData1.setCategoryUUID(categoryController.getCategoryData().get(0).getId());
		
		tasksData.add(tData1);
		
		// create Task View
		
		taskController.createTaskViews(tasksData);
		
		// Ergebnis abgleichen
		
		assertEquals(null, taskController.getTasks().get(0), "createTaskViewsTest1-1");
		
		
	}

}
