package com.example.lkondilidis.smartlearn.services;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.strictmode.NonSdkApiUsedViolation;
import android.util.Log;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import static android.content.ContentValues.TAG;

public class ServerTask extends AsyncTask<Void, Void, List<User>>
{
    List<User> users;
    Context context;
    ApiAuthenticationClient auth;
    User currentuser;
    Intent intent;
    int flag;
    SearchAdapter searchAdapter;

    public ServerTask(List<User> users, Context context, ApiAuthenticationClient auth, User currentuser, Intent intent, int flag){
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
        //return getWebServiceResponseData();
        ArrayList<User> users = new ArrayList<>();
        String output = auth.execute();
        try {
            JSONArray response = new JSONArray(output);
            for(int i=0; i<response.length(); i++){
                JSONObject jsonUser = response.getJSONObject(i);
                User tempUser = new User((jsonUser));
                users.add(tempUser);
            }
        } catch (JSONException e) {
            try {
                JSONObject jsonUser = new JSONObject(output);
                User tempUser = new User((jsonUser));
                switch (flag){
                    case 0: currentuser.updateUser(tempUser);
                    break;
                    case 1: users.add(tempUser);
                    break;
                    default:
                        break;
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    protected void onPostExecute(List<User> tempUsers) {
        if (users != null) {
            this.users.retainAll(tempUsers);
            for (User u : tempUsers) {
                if (!this.users.contains(u)) {
                    this.users.add(u);
                }
            }
            if(searchAdapter != null) {
                searchAdapter.notifyDataSetChanged();
            }
        }
        if(intent != null) {
            context.startActivity(intent);
        }
    }

    public void setAdapter(SearchAdapter searchAdapter) {
        this.searchAdapter = searchAdapter;
    }
}
