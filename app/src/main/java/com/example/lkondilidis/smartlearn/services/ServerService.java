package com.example.lkondilidis.smartlearn.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.lkondilidis.smartlearn.serverClient.MyWebSocketClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;

public class ServerService extends Service {

    private MyWebSocketClient myWebSocketClient;
    @Override
    public void onCreate(){
        System.out.println("Service working");
        try{
            URI uri = new URI("ws://192.168.100.149:8080/chat");
            myWebSocketClient = new MyWebSocketClient(uri, this);
            myWebSocketClient.connect();
        } catch (Exception e){

        }
    }

    //TODO: create better jsonMessage types and better intentAction types
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.getAction() != null) {
            switch (intent.getAction()) {
                case "send": sendToServer(intent);
                    break;
                case "login": loginOnServer(intent);
                    break;
                default:
            }
        }

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void notifyMain(String message){
        Intent intent = new Intent();
        try {
            JSONObject jsonObject = new JSONObject(message);
            String type = jsonObject.getString("type");
            switch(type){
                case "wrong_password":
                    intent.setAction("wrong_password");
                    System.out.println("wrong password");
                    break;
                case "correct":
                    intent.setAction("correct_password");
                    break;
                case "msg":
                    intent.setAction("msg");
                    intent.putExtra("msg", message);
                    break;
                default:
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("send Broadcast");
        sendBroadcast(intent);
    }

    //TODO: create methods to convert our objects to json strings
    private void loginOnServer(Intent intent) {
        String user = intent.getStringExtra("user");
        String password = intent.getStringExtra("password");

        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(user);
            jsonArray.put(password);
            jsonObject.put("type", "login");
            jsonObject.put("data", jsonArray);
            myWebSocketClient.send(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NotYetConnectedException e2){
            System.out.println("not yet connected");
            e2.printStackTrace();
        }
    }

    public void sendToServer(Intent intent){

        String user = intent.getStringExtra("user");
        String msg = intent.getStringExtra("msg");

        System.out.println("Data will be send to the Server");
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(user);
            jsonArray.put(msg);
            jsonObject.put("type", "msg");
            jsonObject.put("data", jsonArray);
            myWebSocketClient.send(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NotYetConnectedException e2){
            System.out.println("not yet connected");
            e2.printStackTrace();
        }
    }
}
