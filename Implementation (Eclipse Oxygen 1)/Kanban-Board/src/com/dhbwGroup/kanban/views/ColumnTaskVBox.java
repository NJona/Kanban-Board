package com.dhbwGroup.kanban.views;

import com.dhbwGroup.kanban.controllers.TaskController;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ColumnTaskVBox {

	private VBox columnTaskVBox;
	
	public ColumnTaskVBox() {
		initialize();
	}
	
	
	private void initialize() {
		columnTaskVBox = new VBox();

		columnTaskVBox.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
		        /* data is dragged over the target */
		        /* accept it only if it is not dragged from the same node 
		         * and if it has a string data */
		        if (((GridPane)dragEvent.getGestureSource()).getParent() != columnTaskVBox &&
		        		dragEvent.getDragboard().hasContent(TaskController.TASK_DATA_FORMAT)) {
		            /* allow for both copying and moving, whatever user chooses */
		        	dragEvent.acceptTransferModes(TransferMode.MOVE);
		        }
		        
		        dragEvent.consume();
			}
			
		});
		
		columnTaskVBox.setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (((GridPane)event.getGestureSource()).getParent() != columnTaskVBox &&
		                 event.getDragboard().hasContent(TaskController.TASK_DATA_FORMAT)) {
		        	 columnTaskVBox.setStyle("-fx-border-color: green;");
		         }
		                
		         event.consume();
		    }
		});
		
		columnTaskVBox.setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* mouse moved away, remove the graphical cues */
		    	columnTaskVBox.setStyle("-fx-border-color: white");

		        event.consume();
		    }
		});
		
		columnTaskVBox.setMinHeight(900);
		columnTaskVBox.setPrefHeight(900);
		columnTaskVBox.setMinWidth(450);
		columnTaskVBox.setPrefWidth(450);
		columnTaskVBox.setMaxWidth(450);
		columnTaskVBox.getStyleClass().add("columnTaskVBox");
	}


	// Getter and Setter
	public VBox getColumnTaskVBox() {
		return columnTaskVBox;
	}

	public void setColumnTaskVBox(VBox columnTaskVBox) {
		this.columnTaskVBox = columnTaskVBox;
	}
}
