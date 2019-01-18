package com.example.lkondilidis.smartlearn.model;

public class Chatlist {
    public String firebaseId;

    public Chatlist(String id) {
        this.firebaseId = id;
    }

    public Chatlist() {
    }

    public String getId() {
        return firebaseId;
    }

    public void setId(String id) {
        this.firebaseId = id;
    }
}