package com.example.lkondilidis.smartlearn.services;


import android.database.MatrixCursor;
import android.os.AsyncTask;

import com.example.lkondilidis.smartlearn.Interfaces.StatusAppointmentFlag;
import com.example.lkondilidis.smartlearn.Interfaces.StatusLectureFlag;
import com.example.lkondilidis.smartlearn.adapters.ExampleAdapter;
import com.example.lkondilidis.smartlearn.adapters.LectureAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.adapters.UserAdapter;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerAppointmentTask extends AsyncTask<Void, Void, List<Appointment>>
{
    private List<Appointment> appointments;
    private ApiAuthenticationClient auth;
    private StatusAppointmentFlag flag;
    private SearchAdapter searchAdapter;
    private UserAdapter userAdapter;
    private LectureAdapter itemArrayAdapter;
    private User currentuser;

    public ServerAppointmentTask(List<Appointment> appointments, User currentuser, ApiAuthenticationClient auth, StatusAppointmentFlag flag){
        this.appointments = appointments;
        this.auth = auth;
        this.flag = flag;
        this.currentuser = currentuser;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
    }

    @Override
    protected List<Appointment> doInBackground(Void... voids) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String output = auth.execute();

        switch (flag) {
            case STATUS_APPOINTMENT_UPDATE_FLAG:
                updateUser(output);
                break;
            default:
                break;
        }
        return appointments;
    }


    private void updateUser(String output) {
        try {
            JSONObject jsonUser = new JSONObject(output);
            User tempUser = new User((jsonUser));
            currentuser.updateUser(tempUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addLectures(String output, ArrayList<Lecture> lectures) {
        try {
            JSONArray response = new JSONArray(output);
            for(int i=0; i<response.length(); i++){
                JSONObject jsonLecture= response.getJSONObject(i);
                Lecture tempLecture = new Lecture((jsonLecture));
                lectures.add(tempLecture);
            }
        } catch (JSONException e) {
            addLecture(output, lectures);
        }
    }

    private void addLecture(String output, ArrayList<Lecture> lectures) {
        try {
            JSONObject jsonLecture = new JSONObject(output);
            Lecture tempLecture = new Lecture((jsonLecture));
            lectures.add(tempLecture);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(List<Appointment> tempLectures) {
        switch (flag){
            case STATUS_APPOINTMENT_UPDATE_FLAG:
                break;
            default:
                break;
        }

        //updateAdapters();
    }

    /*private void updateLectureList(List<Lecture> tempLectures) {
        this.lectures.retainAll(tempLectures);
        for(Lecture tempLecture: tempLectures) {
            if(!containsLecture(this.lectures, tempLecture)){
                lectures.add(tempLecture);
            }
        }

        if(flag == StatusLectureFlag.SERVER_STATUS_FIND_LECTURE) {
            ArrayList<Lecture> removeLectures = new ArrayList<>();
            for (Lecture lecture : this.lectures) {
                if (currentuser.hasLecture(lecture.getId())) {
                    removeLectures.add(lecture);
                }
            }
            this.lectures.removeAll(removeLectures);
        }
    }

    private boolean containsLecture(List<Lecture> copyLectures, Lecture tempLecture) {
        for (Lecture lecture : copyLectures) {
            if(lecture.getId() == tempLecture.getId()){
                return true;
            }
        }
        return false;
    }

    public void updateAdapters(){
        if( searchAdapter != null) {
            searchAdapter.notifyDataSetChanged();
        }
        if( userAdapter != null) {
            userAdapter.notifyDataSetChanged();
        }
        if( itemArrayAdapter != null) {
            itemArrayAdapter.notifyDataSetChanged();
        }
        if( exampleAdapter != null) {
            exampleAdapter.notifyDataSetChanged();
        }
    }

    private void load() {

        String[] columns=new String[]{"_id","text","number"};
        Object[] temp=new Object[]{"0","default","0"};
        MatrixCursor cursor=new MatrixCursor(columns);
        for(int j=0;j<lectures.size();j++)
        {
            Lecture lecture = lectures.get(j);
            String lectureName = lecture.getName();
            int lectureNumber = lecture.getId();
            temp[0] = j;
            temp[1] = lectureName;
            temp[2] = lectureNumber;

            cursor.addRow(temp);
        }
        for(int j=0;j<users.size();j++)
        {
            User user = users.get(j);
            String userName = user.getName();
            int userNumber = user.getId();
            if(userName.contains(getQuerry())) {
                temp[0] = j + lectures.size();
                temp[1] = userName;
                temp[2] = userNumber;

                cursor.addRow(temp);
            }
        }
        cursor.moveToFirst();

        exampleAdapter.changeCursor(cursor);
    }



    public void setAdapter(SearchAdapter searchAdapter) {
        this.searchAdapter = searchAdapter;
    }

    public void setAdapter(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public void setAdapter(LectureAdapter itemArrayAdapter) {
        this.itemArrayAdapter = itemArrayAdapter;
    }

    public void setAdapter(ExampleAdapter exampleAdapter) {
        this.exampleAdapter = exampleAdapter;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setQuerry(String querry) {
        this.querry = querry;
    }

    private CharSequence getQuerry() {
        return this.querry;
    }*/
}

