package com.example.christinali.treehacks2017;

/**
 * Created by christinali on 2/18/17.
 */

public class Data {
    private String description;

    private String imagePath;

    public Data(String imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

}
