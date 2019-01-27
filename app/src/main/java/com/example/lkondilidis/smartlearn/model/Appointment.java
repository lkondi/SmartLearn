package com.example.lkondilidis.smartlearn.model;

import com.example.lkondilidis.smartlearn.Interfaces.StatusAppointmentFlag;
import com.example.lkondilidis.smartlearn.Interfaces.StatusUserJSONFlag;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class Appointment implements Serializable {
    private int id;
    private User appointmentUser;
    private User appointmentAuthor;
    private Lecture subject;
    private String date;
    private String time;
    private boolean accepted;

    public Appointment(){

    }

    public Appointment(int id, User user, User appointmentAuthor, Lecture subject, String date, String time, boolean accepted){
        this.id = id;
        this.appointmentUser = user;
        this.appointmentAuthor = appointmentAuthor;
        this.subject = subject;
        this.date = date;
        this.time = time;
        this.accepted = accepted;
    }

    public Appointment(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.date = jsonObject.getString("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.time = jsonObject.getString("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.accepted = jsonObject.getBoolean("accepted");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.appointmentUser = new User(jsonObject.getJSONObject("appointmentUser"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.appointmentAuthor = new User(jsonObject.getJSONObject("appointmentAuthor"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.subject = new Lecture(jsonObject.getJSONObject("subject"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public JSONObject convertToJSON(StatusAppointmentFlag statusAppointmentflag){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("time", time);
            jsonObject.put("date", date);
            jsonObject.put("accepted", accepted);
            if(statusAppointmentflag != null) {

                switch (statusAppointmentflag) {
                    case STATUS_USER_APPOINTMENT_FLAG:
                        jsonObject.put("appointmentAuthor", appointmentAuthor.convertToJASON(StatusUserJSONFlag.STATUS_USER_USER_FLAG));
                        break;
                    case STATUS_YOUR_APPOINTMENT_FLAG:
                        jsonObject.put("appointmentUser", appointmentUser.convertToJASON(StatusUserJSONFlag.STATUS_YOUR_USER_FLAG));
                        break;
                }

            } else {
                jsonObject.put("subject", subject.convertToJSON());
                jsonObject.put("appointmentAuthor", appointmentAuthor.convertToJASON(null));
                jsonObject.put("appointmentUser", appointmentUser.convertToJASON(null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    //-------------- Getter and Setter -------------------------------

    public User getAppointmentUser() {
        return appointmentUser;
    }

    public void setAppointmentUser(User appointmentUser) {
        this.appointmentUser = appointmentUser;
    }

    public Lecture getSubject() {
        return subject;
    }

    public void setSubject(Lecture subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String plan) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAppointmentAuthor() {
        return appointmentAuthor;
    }

    public void setAppointmentAuthor(User appointmentAuthor) {
        this.appointmentAuthor = appointmentAuthor;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}