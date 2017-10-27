package com.dhbwGroup.kanban.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.TaskData;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class KanbanService{

	private List<ColumnData> standardColumns = new ArrayList<ColumnData>();
	private String columnPath = "db/columns.json";
	
	private String taskPath = "db/tasks.json";
	
	Gson gson;
	
	public KanbanService() {
		gson = new Gson();
		standardColumns.add(new ColumnData("Column1", 0));
		standardColumns.add(new ColumnData("Column2", 0));
		standardColumns.add(new ColumnData("Column3", 0));
		standardColumns.add(new ColumnData("Column4", 0));
	}

	public List<ColumnData> loadColumnsFromDB() {		
		try {
			JsonReader reader = new JsonReader(new FileReader(columnPath));	
			try {
			return new ArrayList<ColumnData>(Arrays.asList(gson.fromJson(reader, ColumnData[].class)));
			}catch(NullPointerException e) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return standardColumns;
			}
		}catch(FileNotFoundException e) {
            System.err.println("Cant't find File! Load Standard Columns");
            return standardColumns;
        }			
	}
	
	public void saveColumnsToDB(List<ColumnData> columnsToSave) throws IOException{
		try (Writer writer = new FileWriter(columnPath)){
			gson.toJson(columnsToSave, writer);
		} catch (IOException e) {			
			boolean writeNotSuccessful = true;
			int index = 0; //changeName
			System.err.println("Couldn't save Columns in" + columnPath + ". Try with " + columnPath + "_" + index);
			while(writeNotSuccessful || index>10) {
				try {
					String newPath = columnPath + "_" + index;
					File newFile = new File(newPath);
					if(newFile.createNewFile()) {
						Writer writer = new FileWriter(newPath);
						gson.toJson(columnsToSave, writer);
						writeNotSuccessful = false;
						columnPath = newPath;
					}else {
						throw new IOException();
					}
				}catch (IOException e2) {
					System.err.println("Couldn't save Columns in" + columnPath + ". Try with " + columnPath + "_" + index);
					index++;
				}
			}
		}		
	}

	public List<TaskData> loadTasksFromDB() {
		try {
			JsonReader reader = new JsonReader(new FileReader(taskPath));	
			try {
			return new ArrayList<TaskData>(Arrays.asList(gson.fromJson(reader, TaskData[].class)));
			}catch(NullPointerException e) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return null;
			}
		}catch(FileNotFoundException e) {
            System.err.println("Cant't find File!");
            return null;
        }	
	}
	
	public void saveTasksToDB(List<TaskData> tasksToSave) throws IOException{
		try (Writer writer = new FileWriter(taskPath)){
			gson.toJson(tasksToSave, writer);
		} catch (IOException e) {			
			boolean writeNotSuccessful = true;
			int index = 0; //changeName
			System.err.println("Couldn't save Tasks in" + taskPath + ". Try with " + taskPath + "_" + index);
			while(writeNotSuccessful || index>10) {
				try {
					String newPath = taskPath + "_" + index;
					File newFile = new File(newPath);
					if(newFile.createNewFile()) {
						Writer writer = new FileWriter(newPath);
						gson.toJson(tasksToSave, writer);
						writeNotSuccessful = false;
						taskPath = newPath;
					}else {
						throw new IOException();
					}
				}catch (IOException e2) {
					System.err.println("Couldn't save Tasks in" + taskPath + ". Try with " + taskPath + "_" + index);
					index++;
				}
			}
		}		
	}

}
