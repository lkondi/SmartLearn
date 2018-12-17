package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.sql.DatabaseHelper;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewName,textViewEmail,textViewPassword;
   // EditText et_name,et_email,et_userid;
    DatabaseHelper dataBaseHelper;
    ArrayList UserArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dataBaseHelper = new DatabaseHelper(this);

        initView();
    }
    public void initView(){
        textViewName= (TextView) findViewById(R.id.textViewName);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
       // tv_userid= (TextView) findViewById(R.id.tv_userid);

    }
}