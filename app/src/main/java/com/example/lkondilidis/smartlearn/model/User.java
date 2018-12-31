package com.example.lkondilidis.smartlearn.model;

import org.json.JSONObject;
import org.json.JSONException;

public class User {

    private int id;
    private String name;
    private String email;
    private String nickname;
    private String password;
    private String studies;
    private String subject;
    private String plan;
    private int ratings;

    public User(){

    }

    public User(int id, String name, String email, String password, String nickname, String studies, String subject, String plan, int ratings){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.studies = studies;
        this.subject = subject;
        this.plan = plan;
        this.ratings = ratings;
    }

    public User(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            this.email = jsonObject.getString("email");
            this.nickname = jsonObject.getString("nickname");
            this.studies = jsonObject.getString("studies");
            this.subject = jsonObject.getString("subject");
            this.plan = jsonObject.getString("plan");
            this.ratings = jsonObject.getInt("ratings");
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudies() {
        return studies;
    }

    public void setStudies(String studies) {
        this.studies = studies;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public JSONObject convertToJASON(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("email", getEmail());
            jsonObject.put("nickname", getNickname());
            jsonObject.put("studies", getStudies());
            jsonObject.put("subject", getSubject());
            jsonObject.put("plan", getPlan());
            jsonObject.put("ratings", getRatings());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

}

