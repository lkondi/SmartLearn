package com.example.lkondilidis.smartlearn.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private String nickname;
    private String password;
    private String studies;
    private String subject;
    private String plan;
    private int ratingstars;
    private String ratingdes;
    private String appuser;
    private String appsubject;
    private String appdate;
    private String apptime;
    private String firebaseId;
    private String imageURL;
    private Set<Lecture> lectures;


    public User(){
        imageURL ="default";
    }

    public User(int id, String name, String email, String password, String nickname, String studies, String subject, String plan, int ratingstars, String
            ratingdes, String appuser, String appsubject, String appdate, String apptime, String firebaseId, Set<Lecture> lectures){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.studies = studies;
        this.subject = subject;
        this.plan = plan;
        this.ratingstars = ratingstars;
        this.ratingdes = ratingdes;
        this.appuser = appuser;
        this.appsubject = appsubject;
        this.appdate = appdate;
        this.apptime = apptime;
        this.firebaseId = firebaseId;
        this.lectures = lectures;
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
            this.firebaseId = jsonObject.getString("firebaseId");
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject.getJSONArray("lectures"));
            Set<Lecture> lectures = new HashSet<>();
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    JSONObject jsonLecture = jsonArray.getJSONObject(i);
                    Lecture lecture = new Lecture(jsonLecture);
                    lectures.add(lecture);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.lectures = lectures;
            this.ratingstars = jsonObject.getInt("ratingstars");
            this.ratingdes = jsonObject.getString("ratingdes");
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

    public int getRatingStars() {
        return ratingstars;
    }

    public void setRatingStars(int ratingstars) {
        this.ratingstars = ratingstars;
    }

    public String getRatingDes() {
        return ratingdes;
    }

    public void setRatingDes(String ratingdes) {
        this.ratingdes = ratingdes;
    }

    public String getAppuser() {
        return appuser;
    }

    public void setAppuser(String appuser) {
        this.appuser = appuser;
    }

    public String getAppsubject() {
        return appsubject;
    }

    public void setAppsubject(String appsubject) {
        this.appsubject = appsubject;
    }

    public String getAppdate() {
        return appdate;
    }

    public void setAppdate(String appdate) {
        this.appdate = appdate;
    }

    public String getApptime() {
        return apptime;
    }

    public void setApptime(String apptime) {
        this.apptime = apptime;
    }

    public void setFirebaseId(String firebaseId){
        this.firebaseId = firebaseId;
    }

    public String getFirebaseId(){
        return this.firebaseId;
    }


    public JSONObject convertToJASON(){
        //String toByte = getPassword();
        //String encoding = new String(Base64.encode(toByte.getBytes(), Base64.DEFAULT));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("email", getEmail());
            //FIXME: encode the password
            jsonObject.put("password", getPassword());
            jsonObject.put("nickname", getNickname());
            jsonObject.put("firebaseId", getFirebaseId());
            jsonObject.put("studies", getStudies());
            jsonObject.put("subject", getSubject());
            jsonObject.put("plan", getPlan());
            JSONArray jsonArray = new JSONArray();
            if(lectures != null) {
                for (Lecture l : lectures) {
                    jsonArray.put(l.convertToJSON());
                }
            }
            jsonObject.put("lectures", jsonArray);
            jsonObject.put("ratingstars", getRatingStars());
            jsonObject.put("ratingdes", getRatingDes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    public String getPrivateInfo() {
        return "";
    }

    public void updateUser(User user) {
        this.setId(user.getId());
        this.setNickname(user.getNickname());
        this.setSubject(user.getSubject());
        this.setStudies(user.getStudies());
        this.setRatingDes(user.getRatingDes());
        this.setRatingStars(user.getRatingStars());
        this.setPlan(user.getPlan());
        this.setFirebaseId(user.getFirebaseId());
        this.setLectures(user.getLectures());
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }
}

