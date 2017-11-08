package com.dhbwGroup.kanban.unitTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.controllers.ColumnController;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.views.Column;

import javafx.embed.swing.JFXPanel;

class ColumnControllerTest {

	// Workaround: Initialisiert internal-graphics
	JFXPanel jfxpanel = new JFXPanel();
	
	/* 
	 * Tests f�r createColumnViewforEachColumnData
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
		cData0.setName("TestSpalte1");
		cData0.setMaxTasks(7);
		ColumnData cData2 = new ColumnData();
		cData0.setName("TestSpalte2");
		cData0.setMaxTasks(9);
		
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
		cData0.setName("TestSpalte1");
		cData0.setMaxTasks(7);
		ColumnData cData2 = new ColumnData();
		cData0.setName("TestSpalte2");
		cData0.setMaxTasks(9);
		
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
	 * Test f�r addColumn
	 * Test 1: nach Aufruf muss neues column sowie neues columnData vorhanden sein
	 * Test 2: R�ckgabewert muss neuem column entsprechen
	 */
	@Test
	void testAddColumn1(){
		
		// ColumnViews erzeugen
		List<ColumnData> columnsData = new ArrayList<ColumnData>();
		
		ColumnData cData0 = new ColumnData();
		cData0.setName("TestSpalte0");
		cData0.setMaxTasks(5);
		ColumnData cData1 = new ColumnData();
		cData0.setName("TestSpalte1");
		cData0.setMaxTasks(7);
		ColumnData cData2 = new ColumnData();
		cData0.setName("TestSpalte2");
		cData0.setMaxTasks(9);
		
		columnsData.add(cData0);
		columnsData.add(cData1);
		columnsData.add(cData2);
		
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		int columnsDataSize = columnsData.size();
		
		// Column hinzuf�gen
		
		columnController.addColumn("TestSpalte3");
		
		// �berpr�fen
		
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
		cData0.setName("TestSpalte1");
		cData0.setMaxTasks(7);
		ColumnData cData2 = new ColumnData();
		cData0.setName("TestSpalte2");
		cData0.setMaxTasks(9);
		
		columnsData.add(cData0);
		columnsData.add(cData1);
		columnsData.add(cData2);
		
		ColumnController columnController = new ColumnController();
		columnController.createColumnViews(columnsData);
		
		// Column hinzuf�gen
		String newColumnName = "TestSpalte3";
		Column newColumn = columnController.addColumn(newColumnName);
		System.out.println(newColumn.getColumnData().getName());
		
		// �berpr�fen
		assertEquals(newColumnName, newColumn.getColumnData().getName());
	}
	
	/*
	 * Test f�r handleRemoveColumn()
	 * Test 1: Column aus columnData und columns entfernt wenn sie keine Tasks besitzt?
	 * Test 2: Werfen einer Column not empty Exception
	 */
	
	/*
	 * Test f�r addEachTaskViewToBoardGridpane
	 * Test 1: Enth�lt jedes Spaltengridpane die daf�r vorgesehenen Tasks
	 */
	
	/*
	 * Test f�r addTask
	 * Test 1: Wird der Task in erste Spalte hinzugef�gt
	 * Test 2: Korrekter R�ckgabewert
	 */
	@Test
	void probetest() {
		assertEquals(1, 1, "Das ist der Probetestfall");
	}
}