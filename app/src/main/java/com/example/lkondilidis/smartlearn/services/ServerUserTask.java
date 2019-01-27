package com.example.lkondilidis.smartlearn.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.adapters.RatingAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchLectureAdapter;
import com.example.lkondilidis.smartlearn.adapters.UserAdapter;
import com.example.lkondilidis.smartlearn.model.Rating;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerUserTask extends AsyncTask<Void, Void, List<User>>
{
    private ArrayList<User> users;
    private Context context;
    private ApiAuthenticationClient auth;
    private User currentuser;
    private Intent intent;
    private StatusUserFlag flag;
    private SearchAdapter searchAdapter;
    private UserAdapter userAdapter;
    public static final String USER_DETAIL_KEY = "currentuser";
    private SearchLectureAdapter searchLectureAdapter;
    private RatingAdapter ratingadapter;

    public ServerUserTask(ArrayList<User> users, Context context, ApiAuthenticationClient auth, User currentuser, Intent intent, StatusUserFlag flag){
        this.users = users;
        this.context = context;
        this.auth = auth;
        this.currentuser = currentuser;
        this.intent = intent;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        ArrayList<User> users = new ArrayList<>();
        String output = auth.execute();

        switch (flag){
            case SERVER_STATUS_ADD_USER: addUser(output, users);
                break;
            case SERVER_STATUS_GET_USERS: addUsers(output, users);
                break;
            case SERVER_STATUS_GET_NOTIFICATION_USER: updateUser(output);
                break;
            case SERVER_STATUS_UPDATE_USER: updateUser(output);
                break;
            case SERVER_STATUS_REGISTER_USER: updateUser(output);
                break;
            case SERVER_STATUS_LOGIN_USER: saveUser(output);
                break;
                default:
                    break;
        }
        return users;
    }

    @Override
    protected void onPostExecute(List<User> tempUsers) {
        switch (flag){
            case SERVER_STATUS_ADD_USER: updateUserList(tempUsers);
                break;
            case SERVER_STATUS_GET_USERS: updateUserList(tempUsers);
                break;
            case SERVER_STATUS_GET_NOTIFICATION_USER:
                break;
            case SERVER_STATUS_UPDATE_USER:
                break;
            case SERVER_STATUS_REGISTER_USER:
                break;
            case SERVER_STATUS_LOGIN_USER:
                break;
            default:
                break;
        }
        updateAdapters();
    }

    private void saveUser(String output) {
        updateUser(output);
        //TODO: Save User in File
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

    private void addUsers(String output, ArrayList<User> users) {
        try {
            JSONArray response = new JSONArray(output);
            for(int i=0; i<response.length(); i++){
                JSONObject jsonUser = response.getJSONObject(i);
                User tempUser = new User((jsonUser));
                users.add(tempUser);
            }
        } catch (JSONException e) {
            //e.printStackTrace();
            addUser(output, users);
        }
    }

    private void addUser(String output, ArrayList<User> users) {
        try {
            JSONObject jsonUser = new JSONObject(output);
            User tempUser = new User((jsonUser));
            users.add(tempUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUserList(List<User> tempUsers) {
        this.users.retainAll(tempUsers);
        for (User u : tempUsers) {
            if (!this.users.contains(u)) {
                this.users.add(u);
                u.setRatingStars(calculateRatingStars(new ArrayList<>(u.getUserRatings())));
            }
        }
        sortUsers(this.users);
    }

    public void sortUsers(ArrayList<User> zusortieren) {
        User temp;
        for(int i=1; i<zusortieren.size(); i++) {
            for(int j=0; j<zusortieren.size()-i; j++) {
                if(zusortieren.get(j).getRatingStars()>zusortieren.get(j+1).getRatingStars()) {
                    temp=zusortieren.get(j);
                    zusortieren.set(j,zusortieren.get(j+1));
                    zusortieren.set(j+1, temp);
                }

            }
        }
    }

    public void updateAdapters(){
        if( searchAdapter != null) {
            searchAdapter.notifyDataSetChanged();
        }
        if( userAdapter != null) {
            userAdapter.notifyDataSetChanged();
        }
        if( searchLectureAdapter != null) {
            searchLectureAdapter.notifyDataSetChanged();
        }
        if( ratingadapter != null) {
            ratingadapter.setDataSet(currentuser.getUserRatings());
        }
        if(intent != null) {
            if(flag == StatusUserFlag.SERVER_STATUS_GET_NOTIFICATION_USER){
                intent.putExtra(USER_DETAIL_KEY, currentuser);
            }
            if(flag == StatusUserFlag.SERVER_STATUS_UPDATE_USER){
                intent.putExtra(USER_DETAIL_KEY, currentuser);
            }
            context.startActivity(intent);
        }
    }

    public int calculateRatingStars(ArrayList<Rating> ratings) {

        int star = 0;
        int sum = 0;
        double median = 0.0;
        ArrayList<Integer> stars = new ArrayList<>();
        for (Rating rating : ratings) {
            stars.add(rating.getStars());
        }
        for (int i = 0; i < stars.size(); i++) {
            sum += stars.get(i);
        }
        if(stars.size()!= 0) {
            median = sum / stars.size();
            star = (int) median;
        }

        return star;
    }

    public void setAdapter(SearchAdapter searchAdapter) {
        this.searchAdapter = searchAdapter;
    }

    public void setAdapter(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public void setAdapter(SearchLectureAdapter searchLectureAdapter) {
        this.searchLectureAdapter = searchLectureAdapter;
    }

    public void setAdapter(RatingAdapter ratingadapter) {
        this.ratingadapter = ratingadapter;
    }
}
