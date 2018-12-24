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

    TextView textViewName,textViewEmail,textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewBewertungen;
    DatabaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dataBaseHelper = new DatabaseHelper(this);

        initView();
    }
    public void initView(){

        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");

        textViewName= (TextView) findViewById(R.id.name);
        textViewEmail = (TextView) findViewById(R.id.email);
        textViewNickname = (TextView) findViewById(R.id.nickname);
        textViewStudies = (TextView) findViewById(R.id.studies1);
        textViewSubject = (TextView) findViewById(R.id.subject1);
        textViewPlan = (TextView) findViewById(R.id.plan1);
        textViewBewertungen = (TextView) findViewById(R.id.bewertungen1);

        textViewEmail.setText(email);




    }
}