package com.dhbwGroup.kanban.controllers;

import java.util.Iterator;

import com.dhbwGroup.kanban.exceptions.MaxTasksAlreadyReachedException;
import com.dhbwGroup.kanban.exceptions.TaskNotReusableException;
import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.dhbwGroup.kanban.models.TaskChangeLog;
import com.dhbwGroup.kanban.models.TaskData;
import com.dhbwGroup.kanban.views.ColumnTaskListView;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class ColumnTaskListController extends Controller{

	ColumnTaskListView columnTaskListView;
	
	ColumnData columnData;
	
	TaskListController taskListController;
	
	public ColumnTaskListController(Project projectData, ColumnData columnData, TaskListController taskListController) {
		super();
		this.setProjectData(projectData);
		this.columnData = columnData;
		this.taskListController = taskListController;
		initialize();
	}

	private void initialize() {
		columnTaskListView = new ColumnTaskListView();
		columnTaskListView.getColumnTaskVBox().setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
		        /* data is dragged over the target */
		        /* accept it only if it is not dragged from the same node 
		         * and if it has a string data */
		        if (((GridPane)dragEvent.getGestureSource()).getParent() != columnTaskListView.getColumnTaskVBox() &&
		        		dragEvent.getDragboard().hasContent(ProjectController.TASK_DATA_FORMAT)) {
		            /* allow for both copying and moving, whatever user chooses */
		        	dragEvent.acceptTransferModes(TransferMode.MOVE);
		        }
		        
		        dragEvent.consume();
			}
			
		});
		
		columnTaskListView.getColumnTaskVBox().setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (((GridPane)event.getGestureSource()).getParent() != columnTaskListView.getColumnTaskVBox() &&
		                 event.getDragboard().hasContent(ProjectController.TASK_DATA_FORMAT)) {
		        	 columnTaskListView.getColumnTaskVBox().setStyle("-fx-border-color: green;");
		         }
		                
		         event.consume();
		    }
		});
		
		columnTaskListView.getColumnTaskVBox().setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* mouse moved away, remove the graphical cues */
		    	columnTaskListView.getColumnTaskVBox().setStyle("-fx-border-color: white");

		        event.consume();
		    }
		});
		createDragAndDropHandlerForColumnTaskVBox();
	}
	
	//---------------------------------------------------------------------------
	//--------------------Drag and Drop Handler----------------------------------
	//---------------------------------------------------------------------------
		
		private void createDragAndDropHandlerForColumnTaskVBox() {
			columnTaskListView.getColumnTaskVBox().setOnDragDropped(new EventHandler<DragEvent>() {

				@Override
				public void handle(DragEvent event) {
					Dragboard dragBoard = event.getDragboard();
					boolean success = false;
					if(dragBoard.hasContent(ProjectController.TASK_DATA_FORMAT)) {
						if(hasColumnSpaceForAnotherTask()) {
							TaskData taskDataToMove = (TaskData) dragBoard.getContent(ProjectController.TASK_DATA_FORMAT);
							TaskController taskController = getTaskViewFromTaskData(taskDataToMove);
							if(taskController != null) {
								if(taskController.getColumnData() != null) {
									ColumnData oldColumn = taskController.getColumnData();
									if(isNotLastColumn(oldColumn)) {
										columnData.getTaskUUIDs().add(taskDataToMove.getId());
										oldColumn.getTaskUUIDs().remove(taskDataToMove.getId());
										taskController.setColumnData(columnData);
										taskController.getTaskData().getChangeLog().add(new TaskChangeLog(columnData.getName()));
										columnTaskListView.getColumnTaskVBox().getChildren().add(taskController.getTaskView().getTaskGridPane());
										saveProject();
										success = true;
									}else {
										success = false;
										try {
											throw new TaskNotReusableException();
										} catch (TaskNotReusableException e) {
											e.printStackTrace();
										}
									}
								}

							}
						}else {
							success = false;
							try {
								throw new MaxTasksAlreadyReachedException();
							} catch (MaxTasksAlreadyReachedException e) {
								e.printStackTrace();
							}
						}
					}
					
			        event.setDropCompleted(success);
			        
			        event.consume();
				}	
			});
		}
		
		private boolean hasColumnSpaceForAnotherTask() {
			return columnData.getMaxTasks() > columnTaskListView.getColumnTaskVBox().getChildren().size();
		}
		
		private boolean isNotLastColumn(ColumnData oldColumn) {
			return this.getProjectData().getColumnsData().indexOf(oldColumn) != this.getProjectData().getColumnsData().size()-1;
		}
		
		private TaskController getTaskViewFromTaskData(TaskData taskDataToMove) {
			Iterator<TaskController> taskControllerIterator = taskListController.getTaskControllers().iterator();
			while(taskControllerIterator.hasNext()) {
				TaskController activeTaskController = taskControllerIterator.next();
				if(activeTaskController.getTaskData().getId().equals(taskDataToMove.getId()))
					return activeTaskController;
			}
			return null;
		}

//---------------------------------------------------------------------------
//--------------------Getter and Setter--------------------------------------
//---------------------------------------------------------------------------
	
	
	public ColumnTaskListView getColumnTaskListView() {
		return columnTaskListView;
	}

	public void setColumnTaskListView(ColumnTaskListView columnTaskListView) {
		this.columnTaskListView = columnTaskListView;
	}
}
