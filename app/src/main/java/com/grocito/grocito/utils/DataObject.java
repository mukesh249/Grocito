package com.grocito.grocito.utils;

public class DataObject {
    private int imageId;
    private String imageName;
    public DataObject(int imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }
    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}