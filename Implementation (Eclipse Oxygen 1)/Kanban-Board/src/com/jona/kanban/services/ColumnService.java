package com.jona.kanban.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jona.kanban.models.Column;

public class ColumnService {
	private Column[] columns;
	
	private Gson gson;
	
	public ColumnService() {
		gson = new Gson();
	}
	
	public void loadColumns() {		
		try {
		JsonReader reader = new JsonReader(new FileReader("db/columns.json"));	
		columns = gson.fromJson(reader, Column[].class);
		}catch(FileNotFoundException e) {
            e.printStackTrace();            
        }			
	}
	
	public String getColumnName(int columnIndex) {
		if(isColumnInArray(columnIndex)) {
			return columns[columnIndex].getName();
		}else {
			System.err.println("Column not available");
			return "Column";
		}
	}
	public boolean setColumnName(int columnIndex, String name) throws IOException {
		if(isColumnInArray(columnIndex)) {
			columns[columnIndex].setName(name);
			try (Writer writer = new FileWriter("db/columns.json")) {
			    gson.toJson(columns, writer);
			}
			return true;
		}else {
			System.err.println("Column not available");
			return false;
		}
	}
	
	private boolean isColumnInArray(int columnIndex) {
		return columnIndex < columns.length;
	}
	
	public boolean getColumnIsActive(int columnIndex) {
		if(isColumnInArray(columnIndex)) {
			return columns[columnIndex].getIsActive();
		}else {
			System.err.println("Column not available");
			return true;
		}
	}
	public boolean setColumnIsActive(int columnIndex, boolean isActive) throws IOException {
		if(isColumnInArray(columnIndex)) {
			columns[columnIndex].setIsActive(isActive);
			try (Writer writer = new FileWriter("db/columns.json")) {
			    gson.toJson(columns, writer);
			}
			return true;
		}else {
			System.err.println("Column not available");
			return false;
		}
	}
	
	public int getColumnNamesLength() {
		return columns.length;
	}	
}
