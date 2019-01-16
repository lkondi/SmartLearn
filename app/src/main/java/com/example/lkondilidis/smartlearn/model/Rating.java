package com.example.lkondilidis.smartlearn.model;

public class Rating {

    private  int id;
    private int stars;
    private String description;

    public Rating(){

    }

    public Rating(int id, int stars, String description){
        this.id = id;
        this.stars = stars;
        this.description = description;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return stars;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
