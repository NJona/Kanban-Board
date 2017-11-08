package com.dhbwGroup.kanban.views;

import com.dhbwGroup.kanban.models.ColumnData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

public class Column {
	private ColumnData columnData;
	
	private GridPane columnGridPane;
	
	private ColumnHeaderGridPane columnHeaderGridPane;

	private ColumnTaskGridPane columnTaskGridPane;
	
	public Column(ColumnData columnData) {
		this.columnData = columnData;
		
		initialize();
	}
	
	public void initialize() {
		columnGridPane = new GridPane();
		initializeColumnHeaderGridPane();
		columnHeaderGridPane.getColumnGridPaneElementEditButton().getButton().setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	columnData.setName(columnHeaderGridPane.handleEditNameEvent());
    	    }
		});
		initializeColumnTasksGridPane();
		initializeColumnConstraints();
		setRowConstraints();
		this.columnGridPane.getStyleClass().add("columnGridPane");
	}

	private void setRowConstraints() {
		columnGridPane.getRowConstraints().add(columnHeaderGridPane.getRowConstraints());
		columnGridPane.getRowConstraints().add(columnTaskGridPane.getRowConstraints());
	}

	private void initializeColumnHeaderGridPane() {
		columnHeaderGridPane = new ColumnHeaderGridPane(columnData.getName());
		columnGridPane.add(columnHeaderGridPane.getColumnHeaderGridPane(), 0, 0);
	}
	
	private void initializeColumnTasksGridPane() {
		columnTaskGridPane = new ColumnTaskGridPane();
		columnGridPane.add(columnTaskGridPane.getColumnTaskGridPane(), 0, 1);
	}

	public void initializeColumnConstraints() {
		this.columnGridPane.setMinWidth(400);
		this.columnGridPane.setPrefWidth(450);
		this.columnGridPane.setMaxWidth(500);
	}

	
// Getter and Setter
	public ColumnHeaderGridPane getColumnHeaderGridPane() {
		return columnHeaderGridPane;
	}

	public void setColumnHeaderGridPane(ColumnHeaderGridPane columnHeaderGridPane) {
		this.columnHeaderGridPane = columnHeaderGridPane;
	}

	public ColumnTaskGridPane getColumnTaskGridPane() {
		return columnTaskGridPane;
	}

	public void setColumnTaskGridPane(ColumnTaskGridPane columnTaskGridPane) {
		this.columnTaskGridPane = columnTaskGridPane;
	}
	
	public ColumnData getColumnData() {
		return columnData;
	}

	public void setColumnData(ColumnData columnData) {
		this.columnData = columnData;
	}

	public GridPane getColumnGridPane() {
		return columnGridPane;
	}

	public void setColumnGridPane(GridPane columnGridPane) {
		this.columnGridPane = columnGridPane;
	}
	
}
