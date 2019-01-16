package com.example.lkondilidis.smartlearn.model;

public class Appointment {
    private String user;
    private String subject;
    private String date;
    private String time;


    public Appointment(String user, String subject, String date, String time){
        this.user = user;
        this.subject = subject;
        this.date = date;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
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
    
}