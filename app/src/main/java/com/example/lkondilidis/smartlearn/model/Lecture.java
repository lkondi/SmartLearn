package com.example.lkondilidis.smartlearn.model;

public class Lecture {

    private String name;
    private int id;

    public Lecture(){

    }

    public Lecture(String name, int id){
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
