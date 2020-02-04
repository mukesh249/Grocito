package com.grocito.grocito.model;

public class MenuModel {

    public String menuName, url;
    public boolean hasChildren, isGroup;
    public int image;

    public MenuModel(String menuName,int image, boolean isGroup, boolean hasChildren, String url) {
        this.image = image;
        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }

}
