package com.dhbwGroup.kanban.unitTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dhbwGroup.kanban.controllers.ColumnController;
import com.dhbwGroup.kanban.controllers.CategoryController;
import com.dhbwGroup.kanban.exceptions.ColumnNotEmptyException;
import com.dhbwGroup.kanban.exceptions.MinColumnsException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.views.Column;

import javafx.embed.swing.JFXPanel;

class ColumnControllerTest {

	// Workaround: Initialisiert internal-graphics
	JFXPanel jfxpanel = new JFXPanel();
	
	
	
	/*
	 *  Tests für das Drag and Drop Feature:
	 * 
	 * 
	 */
	@Test
	void testDragDrop1() {
		
		// Column Data erstellen
		List<ColumnData> columnsData = new ArrayList<ColumnData>();
		
		ColumnData cData0 = new ColumnData();
		cData0.setName("TestSpalte0");
		cData0.setMaxTasks(5);
		ColumnData cData1 = new ColumnData();
		cData1.setName("TestSpalte1");
		cData1.setMaxTasks(7);
		ColumnData cData2 = new ColumnData();
		cData2.setName("TestSpalte2");
		cData2.setMaxTasks(9);
		
		columnsData.add(cData0);
		columnsData.add(cData1);
		columnsData.add(cData2);
		
		// Column Views erzeugen
		
		CategoryController categoryController = new CategoryController();
		
		ColumnController columnController = new ColumnController(categoryController);
		columnController.createColumnViews(columnsData);
		
	}

}
