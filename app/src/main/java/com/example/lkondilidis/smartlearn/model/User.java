package com.example.lkondilidis.smartlearn.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private double rating;
    private String privateInfo;

    public User(){

    }

    public User(int id, String name, String email, String password, double rating, String privateInformation){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rating = rating;
        this.privateInfo = privateInformation;
    }

    public User(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            this.email = jsonObject.getString("email");
            this.rating = jsonObject.getDouble("rating");
            this.privateInfo = jsonObject.getString("privateInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPrivateInfo() {
        return privateInfo;
    }

    public void setPrivateInfo(String privateInfo) {
        this.privateInfo = privateInfo;
    }

    public JSONObject convertToJASON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("email", getEmail());
            jsonObject.put("rating", getRating());
            jsonObject.put("privateInfo", getPrivateInfo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

}
