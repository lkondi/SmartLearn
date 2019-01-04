package com.example.lkondilidis.smartlearn.services;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import static android.content.ContentValues.TAG;

public class ServerTask extends AsyncTask<Void, Void, User>
{
    ArrayList<User> users;
    Context context;

    public ServerTask(ArrayList<User> users, Context context){
        this.users = users;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
    }

    @Override
    protected User doInBackground(Void... voids) {
        return getWebServiceResponseData();

    }

    @Override
    protected void onPostExecute(User user) {
        this.users.add(user);
    }

    public User getWebServiceResponseData() {

        StringBuffer response = null;
        try {
            String path = context.getString(R.string.path);
            URL url = new URL(path);
            Log.d(TAG, "ServerData: " + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d(TAG, "HttpsConnection: " + conn.toString());
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            Log.d(TAG, "Response code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Reading response from input Stream
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String output;
                response = new StringBuffer();

                while ((output = in.readLine()) != null) {
                    response.append(output);
                }
                in.close();
            }}
        catch(Exception e){
            e.printStackTrace();
        }

        String responseText = "";
        if(response != null) {
            responseText = response.toString();
        }
        //Call ServerData() method to call webservice and store result in response
        //  response = service.ServerData(path, postDataParams);
        Log.d(TAG, "data:" + responseText);
        System.out.println("Impotant");
        try {

                JSONObject jsonobject = new JSONObject(responseText);
                int id = jsonobject.getInt("id");
                String content = jsonobject.getString("content");
                Log.d(TAG, "id:" + id);
                Log.d(TAG, "content:" + content);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new User();
    }
}
