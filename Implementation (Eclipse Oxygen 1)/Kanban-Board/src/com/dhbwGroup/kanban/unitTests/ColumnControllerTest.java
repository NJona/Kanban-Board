package com.dhbwGroup.kanban.unitTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dhbwGroup.kanban.controllers.ColumnController;
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
	 * Tests für createColumnViewforEachColumnData
	 * Test 1: Gleich viele columnsData wie columns(Views) vorhanden?
	 * Test 2: columns[0].getColumnData = columnsData[0], etc. ?
	 */
	
	@Test
	void testCreateColumnViewforEachColumnData1() {
		
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
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		// Anzahl vergleichen
		
		assertEquals( columnsData.size() , columnController.getColumns().size()  ,"CreateColumnView Test1");
	}
	 
	@Test
	void testCreateColumnViewforEachColumnData2() {
		
		// Column Data erstellen (UUID, name, List<UUID> taskUUIDs, maxTasks)
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
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		
		// Data und View vergleichen
		for(int i = 0; i < columnsData.size(); i++) {
			assertEquals(columnsData.get(i) , columnController.getColumns().get(i).getColumnData(), "CreateColumnView Test2");
		}
		
	}
	
	
	/*
	 * Test für addColumn
	 * Test 1: nach Aufruf muss neues column sowie neues columnData vorhanden sein
	 * Test 2: Rückgabewert muss neuem column entsprechen
	 */
	@Test
	void testAddColumn1(){
		
		// ColumnViews erzeugen
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
		
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		int columnsDataSize = columnsData.size();
		
		// Column hinzufügen
		
		columnController.addColumn("TestSpalte3");
		
		// Überprüfen
		
		assertEquals( columnsDataSize + 1 , columnController.getColumnsData().size()  ,"AddColumn Test1");
		assertEquals( columnsDataSize + 1 , columnController.getColumns().size() , "AddColumn Test1");
		
	}
	
	@Test
	void testAddColumn2() {
		
		//ColumnViews erzeugen
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
		
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		// Column hinzufügen
		String newColumnName = "TestSpalte3";
		Column newColumn = columnController.addColumn(newColumnName);
		//System.out.println(newColumn.getColumnData().getName());
		
		// Überprüfen
		assertEquals(newColumnName, newColumn.getColumnData().getName());
	}
	
	/*
	 * Test für handleRemoveColumn()
	 * Test 1: Column aus columnData und columns entfernt wenn sie keine Tasks besitzt?
	 * Test 2: Werfen einer Column not empty Exception
	 * 
	 * Für Test 2 musste die Exception angepasst werden
	 */
	@Test
	void testHandleRemoveColumn1() {
		
		//ColumnViews erzeugen
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
				
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		List<ColumnData> columnsDataOld = new ArrayList<ColumnData>();
		columnsDataOld = columnController.getColumnsData();
		
		List<Column> columnOld = new ArrayList<Column>();
		columnOld = columnController.getColumns();
		
		columnController.addColumn("TestSpalte3");
		
		// Entferne Column
		try {
			columnController.handleRemoveColumn(columnController.getColumns().get(3));
		} catch (ColumnNotEmptyException e) {
			e.printStackTrace();
		} catch (MinColumnsException e) {
			e.printStackTrace();
		}
		
		// Entfernt aus Columns?
		
		assertEquals(columnOld, columnController.getColumns(), "HandleRemoveColumnTest1-1");
		
		// Entfernt aus ColumnData?
		
		assertEquals(columnsDataOld, columnController.getColumnsData(), "HandleRemoveColumnTest1-2");
		
	}
	
	@Test
	void testHandleRemoveColumn2(){
		//ColumnViews erzeugen
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
						
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		// Tasks zu Column 0 hinzufügen
		columnController.getTaskController().setTasksData(new ArrayList<TaskData>());
		columnController.addTask();
		
		// Column 0 löschen
		
		
		assertThrows(ColumnNotEmptyException.class , ()-> {
			columnController.handleRemoveColumn(columnController.getColumns().get(0));
		});
	
		
		
		
	}
	/*
	 * Test für addTask
	 * Test 1: Wird der Task in erste Spalte hinzugefügt
	 * Test 2: Korrekter Rückgabewert
	 */
	@Test
	void testAddTask1() {
		
		//ColumnView erzeugen
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
						
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		//addTask
		
		columnController.getTaskController().setTasksData(new ArrayList<TaskData>());
		columnController.addTask();
		
		UUID oldID = columnController.getColumns().get(0).getColumnData().getTaskUUIDs().get(
				columnController.getColumns().get(0).getColumnData().getTaskUUIDs().size()-1);
		
		columnController.addTask();
		
		//Task zu Spalte 0 hinzugefügt?
		
		UUID newID = columnController.getColumns().get(0).getColumnData().getTaskUUIDs().get(
				columnController.getColumns().get(0).getColumnData().getTaskUUIDs().size()-2);
		
		assertEquals(oldID, newID, "addTaskTest1");
	}
	
	@Test
	void testAddTask2() {
		
		//ColumnView erzeugen
		List<ColumnData> columnsData = new ArrayList<ColumnData>();
				
		ColumnData cData0 = new ColumnData();
		cData0.setName("TestSpalte0");
		cData0.setMaxTasks(4);
		ColumnData cData1 = new ColumnData();
		cData1.setName("TestSpalte1");
		cData1.setMaxTasks(7);
		ColumnData cData2 = new ColumnData();
		cData2.setName("TestSpalte2");
		cData2.setMaxTasks(9);
				
		columnsData.add(cData0);
		columnsData.add(cData1);
		columnsData.add(cData2);
							
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		//Task hinzufügen und auf korrekten Rückgabewert überprüfen
		
		columnController.getTaskController().setTasksData(new ArrayList<TaskData>());
		boolean value1 = columnController.addTask();
		boolean value2 = columnController.addTask();
		boolean value3 = columnController.addTask();
		boolean value4 = columnController.addTask();
		boolean value5 = columnController.addTask();
		
		assertEquals(false, value1, "addTaskTest2-value1");
		assertEquals(false, value2, "addTaskTest2-value2");
		assertEquals(false, value3, "addTaskTest2-value3");
		assertEquals(true, value4, "addTaskTest2-value4");
		assertEquals(true, value5, "addTaskTest2-value5");
		
	}
	
	
	/*
	 * Test für addEachTaskViewToBoardGridpane
	 * Test 1: Enthält jedes Spaltengridpane die dafür vorgesehenen Tasks
	 *
	 *
	 * TEST AUFGURND SA MOMENTAN NICHT MOEGLICH
	 *
	 *
	@Test
	void testAddTaskViewToGrid() {
		
		//ColumnView erzeugen
		List<ColumnData> columnsData = new ArrayList<ColumnData>();
						
		ColumnData cData0 = new ColumnData();
		cData0.setName("TestSpalte0");
		cData0.setMaxTasks(4);
								
		columnsData.add(cData0);
							
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		//add Task to Column
		
		columnController.getTaskController().setTasksData(new ArrayList<TaskData>());
		columnController.addTask();
		
		columnController.addEachTaskViewToColumnView();
		
		System.out.println(columnController.getColumns().get(0).getColumnTaskGridPane());
	}
	*/
	
	@Test
	void probetest() {
		assertEquals(1, 1, "Das ist der Probetestfall");
	}
}