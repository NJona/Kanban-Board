package com.dhbwGroup.kanban.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.dhbwGroup.kanban.models.ColumnData;
import com.dhbwGroup.kanban.models.Project;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class KanbanService{

	private List<ColumnData> standardColumns = new ArrayList<ColumnData>();
	
	private String projectPath = "db/project.json";
	
	Gson gson;
	
	public KanbanService() {
		gson = new Gson();
		standardColumns.add(new ColumnData("Todo", 8));
		standardColumns.add(new ColumnData("Doing", 1));
		standardColumns.add(new ColumnData("Done", 10000));
	}
	
	public Project createNewBoard(){
		return new Project(standardColumns);
	}
	
	public Project loadProject() {
		try {
			JsonReader reader = new JsonReader(new FileReader(projectPath));	
			try {
			return gson.fromJson(reader, Project.class);
			}catch(NullPointerException e) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return new Project(standardColumns);
			}
		}catch(FileNotFoundException e) {
            System.err.println("Cant't find File! Load Standard Columns");
            return new Project(standardColumns);
        }		
	}
	
	public void saveProject(Project project) {
		try (Writer writer = new FileWriter(projectPath)){
			gson.toJson(project, writer);
		} catch (IOException e) {			
			e.printStackTrace();
		}	
	}
}
