package com.dhbwGroup.kanban.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.services.KanbanService;
import com.dhbwGroup.kanban.views.Column;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ColumnController {

	private List<ColumnData> columnsData;
	private KanbanService kanbanService;
	
	private List<Column> columns = new ArrayList<Column>();

	public ColumnController() {
		kanbanService = new KanbanService();
		columnsData = kanbanService.loadColumnsFromDB();
		createColumnViewForEeachColumnData();
	}
	
	private void createColumnViewForEeachColumnData() {
		int numberOfColumns = columnsData.size();
		if(!columnsData.isEmpty()) {
			columnsData.forEach((activeColumn) -> {
				activeColumn.setNumberOfTasks(0);
				columns.add(new Column(numberOfColumns, getColumnsData().indexOf(activeColumn), activeColumn));
			});;			
		}
		createEventHandlerForEachEditColumnNameButton();
	}
	
	private void createEventHandlerForEachEditColumnNameButton() {
		if(!columns.isEmpty()) {
	    	columns.forEach((activeColumn) -> {
	    		activeColumn.getColumnGridPaneElementButton().getToggleChangeColumnName().setOnAction(new EventHandler<ActionEvent>() {
	        	    @Override public void handle(ActionEvent e) {
	        	    	handleEditNameEvent(activeColumn);
	        	    }
	    		});
	    	});			
		}
	}
	
	private void handleEditNameEvent(Column activeColumn) {
        if(activeColumn.getColumnGridPaneElementText().getColumnTextLabel().isVisible()) {
			activeColumn.getColumnGridPaneElementButton().setButtonText("Save");
			activeColumn.getColumnGridPaneElementText().getColumnTextLabel().setVisible(false);
			activeColumn.getColumnGridPaneElementText().getColumnTextField().setVisible(true);
		}else {
			activeColumn.getColumnGridPaneElementButton().setButtonText("Edit");
			activeColumn.getColumnGridPaneElementText().getColumnTextLabel().setVisible(true);
			activeColumn.getColumnGridPaneElementText().getColumnTextField().setVisible(false);
			activeColumn.getColumnData().setName(activeColumn.getColumnGridPaneElementText().getColumnTextField().getText());
			activeColumn.getColumnGridPaneElementText().getColumnTextLabel().setText(activeColumn.getColumnGridPaneElementText().getColumnTextField().getText());
			getColumnsData().set(activeColumn.getColumnIndex(), activeColumn.getColumnData());
			updateDataBase();
		}
	}
	
	public Column addColumn(String columnName){
		ColumnData newColumnData = new ColumnData(columnName);
		getColumnsData().add(newColumnData);
		Column newColumn = new Column(getColumnsData().size(), getColumnsData().indexOf(newColumnData), newColumnData);
		columns.add(newColumn);
		newColumn.getColumnGridPaneElementButton().getToggleChangeColumnName().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	handleEditNameEvent(newColumn);
    	    }
		});
		newColumn.getColumnGridPane().getStyleClass().add("lastColumn");
		updateColumnSize();
		updateDataBase();
		return newColumn;
	}
	
	public Column removeColumn() {
			Column columnToRemove = columns.get(getColumnsData().size()-1);
			columnsData.remove(columnToRemove.getColumnIndex());
			columns.remove(columnToRemove.getColumnIndex());
			updateColumnSize();
			updateDataBase();
			return columnToRemove;		
	}
	
	private void updateColumnSize() {
		columns.forEach((activeColumn) -> {
			activeColumn.setNumberOfColumns(columns.size());
		});
	}
	
	private void updateDataBase() {
		try {
			kanbanService.saveColumnsToDB(columnsData);
		} catch (IOException e) {
			System.err.println("ColumnController.updateDataBase()");
			System.err.println("Can't update Database");
		}
	}
	
	
	//Getter and Setter Methods
	
	public List<ColumnData> getColumnsData() {
		return columnsData;
	}

	public void setColumnsData(List<ColumnData> columnsData) {
		this.columnsData = columnsData;
	}
	
	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
}
