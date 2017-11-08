package com.dhbwGroup.kanban.models;

import java.util.UUID;

import com.google.gson.annotations.SerializedName;

public class CategoryData {
	
	public CategoryData() {
		this.id = UUID.randomUUID();
	}
	
    @SerializedName("uuid")
    private UUID id;
    
    @SerializedName("title")
    private String title = "Task";
    
    @SerializedName("color")
    private String color;
    
  //---------------------------------------------------------------------------
  //--------------------Getter and Setter--------------------------------------
  //---------------------------------------------------------------------------
    
    public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
