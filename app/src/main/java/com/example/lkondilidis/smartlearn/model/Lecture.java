package com.example.lkondilidis.smartlearn.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class Lecture {

    private String name;
    private int id;
    private Set<User> users;

    public Lecture(){

    }

    public Lecture(String name, int id, Set<User> users){
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Lecture(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject convertToJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            //jsonObject.put("")
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
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

    public Set<User> getUsers(){
        return this.users;
    }

    public void setUsers(Set<User> users){
        this.users = users;
    }
}
