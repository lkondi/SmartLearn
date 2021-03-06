package com.example.lkondilidis.smartlearn.model;

import com.example.lkondilidis.smartlearn.Interfaces.StatusAppointmentFlag;
import com.example.lkondilidis.smartlearn.Interfaces.StatusRatingFlag;
import com.example.lkondilidis.smartlearn.Interfaces.StatusUserJSONFlag;

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
    private Set<Rating> yourRatings;
    private Set<Rating> userRatings;
    private Set<Appointment> yourAppointments;
    private Set<Appointment> userAppointments;
    private boolean changed;


    public User(){
        imageURL ="default";
        changed = false;
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
        changed = false;
    }

    public User(JSONObject jsonObject){
        try {
            this.id = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.email = jsonObject.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.nickname = jsonObject.getString("nickname");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.studies = jsonObject.getString("studies");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.subject = jsonObject.getString("subject");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.imageURL = jsonObject.getString("imageURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.plan = jsonObject.getString("plan");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.firebaseId = jsonObject.getString("firebaseId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("lectures");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("yourRatings");
            Set<Rating> ratings = new HashSet<>();
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    JSONObject jsonRating = jsonArray.getJSONObject(i);
                    Rating rating = new Rating(jsonRating);
                    ratings.add(rating);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.yourRatings = ratings;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("userRatings");
            Set<Rating> ratings = new HashSet<>();
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    JSONObject jsonRating = jsonArray.getJSONObject(i);
                    Rating rating = new Rating(jsonRating);
                    ratings.add(rating);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.userRatings = ratings;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("yourAppointments");
            Set<Appointment> appointments = new HashSet<>();
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    JSONObject jsonAppointment = jsonArray.getJSONObject(i);
                    Appointment appointment = new Appointment(jsonAppointment);
                    appointments.add(appointment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.yourAppointments = appointments;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("userAppointments");
            Set<Appointment> appointments = new HashSet<>();
            for(int i=0; i<jsonArray.length(); i++){
                try {
                    JSONObject jsonAppointment = jsonArray.getJSONObject(i);
                    Appointment appointment = new Appointment(jsonAppointment);
                    appointments.add(appointment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.userAppointments = appointments;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            this.ratingstars = jsonObject.getInt("ratingstars");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.ratingdes = jsonObject.getString("ratingdes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        changed = false;
    }

    public JSONObject convertToJASON(StatusUserJSONFlag statusUserJSONFlag){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("email", getEmail());
            jsonObject.put("nickname", getNickname());
            jsonObject.put("firebaseId", getFirebaseId());
            jsonObject.put("studies", getStudies());
            jsonObject.put("subject", getSubject());
            jsonObject.put("plan", getPlan());
            jsonObject.put("password", getPassword());

            JSONArray jsonArray = new JSONArray();
            if(lectures != null) {
                for (Lecture l : lectures) {
                    jsonArray.put(l.convertToJSON());
                }
            }

            JSONArray jsonArrayYourRatings = new JSONArray();
            if(statusUserJSONFlag == null) {
                if (yourRatings != null) {
                    for (Rating r : yourRatings) {
                        r.setAuthor(this);
                        jsonArrayYourRatings.put(r.convertToJSON(StatusRatingFlag.STATUS_YOUR_RATINGS));
                    }
                }
            }


            JSONArray jsonArrayUserRatings = new JSONArray();
            if(statusUserJSONFlag == null) {
                if (userRatings != null) {
                    for (Rating r : userRatings) {
                        r.setUser(this);
                        jsonArrayUserRatings.put(r.convertToJSON(StatusRatingFlag.STATUS_USER_RATINGS));
                    }
                }
            }

            JSONArray jsonArrayYourAppointments = new JSONArray();
            if(statusUserJSONFlag == null) {
                if (yourAppointments != null) {
                    for (Appointment a : yourAppointments) {
                        a.setAppointmentAuthor(this);
                        jsonArrayYourAppointments.put(a.convertToJSON(StatusAppointmentFlag.STATUS_YOUR_APPOINTMENT_FLAG));
                    }
                }
            }

            JSONArray jsonArrayUserAppointments = new JSONArray();
            if(statusUserJSONFlag == null) {
                if (userAppointments != null) {
                    for (Appointment a : yourAppointments) {
                        a.setAppointmentUser(this);
                        jsonArrayUserAppointments.put(a.convertToJSON(StatusAppointmentFlag.STATUS_YOUR_APPOINTMENT_FLAG));
                    }
                }
            }

            jsonObject.put("lectures", jsonArray);
            jsonObject.put("yourRatings", jsonArray);
            jsonObject.put("userRatings", jsonArray);
            jsonObject.put("yourAppointments", jsonArray);
            jsonObject.put("ratingstars", getRatingStars());
            jsonObject.put("ratingdes", getRatingDes());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    public void updateUser(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setNickname(user.getNickname());
        this.setSubject(user.getSubject());
        this.setStudies(user.getStudies());
        this.setRatingDes(user.getRatingDes());
        this.setRatingStars(user.getRatingStars());
        this.setPlan(user.getPlan());
        this.setFirebaseId(user.getFirebaseId());
        this.setLectures(user.getLectures());
        this.setYourRatings(user.getYourRatings());
        this.setUserRatings(user.getUserRatings());
        this.setYourAppointments(user.getYourAppointments());
        this.setUserAppointments(user.getUserAppointments());
    }

    public void addLecture(Lecture lecture) {
        getLectures().add(lecture);
    }

    public void removeLecture(int id) {
        Lecture removeLecture = null;
        for(Lecture l: getLectures()){
            if(l.getId() == id){
                removeLecture = l;
                break;
            }
        }
        if(removeLecture!=null) {
            getLectures().remove(removeLecture);
        }
    }

    public boolean hasLecture(int id) {
        for (Lecture lecture : getLectures()) {
            if (lecture.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public Lecture findLecture(int id) {
        for (Lecture lecture : getLectures()) {
            if (lecture.getId() == id) {
                return lecture;
            }
        }
        return null;
    }


    //-------------- Getter and Setter -------------------------------


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

    public String getPrivateInfo() {
        return "";
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

    public Set<Rating> getYourRatings() {
        return yourRatings;
    }

    public void setYourRatings(Set<Rating> yourRatings) {
        this.yourRatings = yourRatings;
    }

    public Set<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Set<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public Set<Appointment> getYourAppointments() {
        return yourAppointments;
    }

    public void setYourAppointments(Set<Appointment> yourAppointments) {
        this.yourAppointments = yourAppointments;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public Set<Appointment> getUserAppointments() {
        return userAppointments;
    }

    public void setUserAppointments(Set<Appointment> userAppointments) {
        this.userAppointments = userAppointments;
    }
}

