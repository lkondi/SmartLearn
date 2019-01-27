package com.example.lkondilidis.smartlearn.model;

import com.example.lkondilidis.smartlearn.Interfaces.StatusRatingFlag;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Rating implements Serializable {

    private  int id;
    private int stars;
    private String description;
    private User user;
    private User author;

    public Rating(){

    }

    public Rating(int id, int stars, String description, User user, User author){
        this.id = id;
        this.stars = stars;
        this.description = description;
        this.user = user;
        this.author = author;
    }

    public Rating(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.stars = jsonObject.getInt("stars");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.description = jsonObject.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.user = new User(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.author = new User(jsonObject.getJSONObject("author"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject convertToJSON(StatusRatingFlag statusRatingFlag){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("stars", stars);
            jsonObject.put("description", description);
            if(statusRatingFlag!= null) {
                switch (statusRatingFlag) {
                    case STATUS_USER_RATINGS:
                        jsonObject.put("author", author.convertToJASON());
                        break;
                    case STATUS_YOUR_RATINGS:
                        jsonObject.put("user", user.convertToJASON());
                        break;
                }
            }else {
                jsonObject.put("author", author.convertToJASON());
                jsonObject.put("user", user.convertToJASON());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }


    //-------------- Getter and Setter -------------------------------

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
