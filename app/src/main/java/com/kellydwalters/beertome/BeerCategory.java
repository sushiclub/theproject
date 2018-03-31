package com.kellydwalters.beertome;

/**
 * Created by Kelly on 2018-03-25.
 */

public class BeerCategory {

    int id;
    String categoryName;

    // constructors
    public BeerCategory() {

    }

    public BeerCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public BeerCategory(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
}
