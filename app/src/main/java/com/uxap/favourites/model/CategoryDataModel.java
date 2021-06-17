package com.uxap.favourites.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryDataModel implements Serializable {
    private String categoryTitle, categoryDateAdded;
    private ArrayList<String> categoryItems;

    public CategoryDataModel(String categoryTitle, String categoryDateAdded, ArrayList<String> categoryItems) {
        this.categoryTitle = categoryTitle;
        this.categoryDateAdded = categoryDateAdded;
        this.categoryItems = categoryItems;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getCategoryDateAdded() {
        return categoryDateAdded;
    }

    public ArrayList<String> getCategoryItems() {
        return categoryItems;
    }

}
