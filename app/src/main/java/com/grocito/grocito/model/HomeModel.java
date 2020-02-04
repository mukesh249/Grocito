package com.grocito.grocito.model;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeModel implements Serializable {

    private String id = "";
    private String cat_name = "";
    private String cat_slug = "";
    private ArrayList<HomeCatProductModel> bavArrayList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_slug() {
        return cat_slug;
    }

    public void setCat_slug(String cat_slug) {
        this.cat_slug = cat_slug;
    }

    public ArrayList<HomeCatProductModel> getBavArrayList() {
        return bavArrayList;
    }

    public void setBavArrayList(ArrayList<HomeCatProductModel> bavArrayList) {
        this.bavArrayList = bavArrayList;
    }
}
