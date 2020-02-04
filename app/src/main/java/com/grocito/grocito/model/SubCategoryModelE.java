package com.grocito.grocito.model;

import java.io.Serializable;

public class SubCategoryModelE implements Serializable {

    private String menuName, url,subCatSlug,subCatId,image_icon;
    private boolean hasChildren, isGroup;
    private int image;

    public String getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(String image_icon) {
        this.image_icon = image_icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSubCatSlug() {
        return subCatSlug;
    }

    public void setSubCatSlug(String subCatSlug) {
        this.subCatSlug = subCatSlug;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }
}
