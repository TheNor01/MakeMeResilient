package com.DesignPatternDoc.DataContracts.models;

import java.io.Serializable;

public class DesignPatternDoc implements Serializable {
    private static final long serialVersionUID = -6725570673833522155L;
    public String title;
    public String description;
    public String imagePath;

    public DesignPatternDoc(String title, String description, String imagePath){
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }
}
