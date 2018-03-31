package com.kellydwalters.beertome;

/**
 * Created by Kelly on 2018-03-25.
 * Models a review
 */

public class BeerReview {

    int id;
    String name, description, abv, image, review, rating;

    // constructors
    public BeerReview() {
    }

    public BeerReview(String name, String description, String abv, String image, String review, String rating) {
        this.name = name;
        this.description = description;
        this.abv = abv;
        this.image=image;
        this.review = review;
        this.rating = rating;
    }

    public BeerReview(String name, String description, String abv, String review, String rating) {
        this.name = name;
        this.description = description;
        this.abv = abv;
        this.review = review;
        this.rating = rating;
    }

    public BeerReview(int id, String name, String description, String abv, String image, String review, String rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.abv = abv;
        this.image=image;
        this.review = review;
        this.rating = rating;
    }

    public BeerReview(int id, String name, String description, String abv, String review, String rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.abv = abv;
        this.review = review;
        this.rating = rating;
    }
    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAbv(String abv){
        this.abv = abv;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setReview(String review){
        this.review = review;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    // getters
    public long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public String getAbv() {
        return this.abv;
    }
    public String getImage() {
        return this.image;
    }
    public String getReview() {
        return this.review;
    }
    public String getRating() {
        return this.rating;
    }
}
