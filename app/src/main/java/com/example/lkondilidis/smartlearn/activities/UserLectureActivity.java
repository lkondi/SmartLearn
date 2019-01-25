package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchLectureAdapter;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserLectureActivity extends AppCompatActivity {

    private User currentuser;
    public static final String USER_DETAIL_KEY = "currentuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lecture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lecture);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLectureActivity.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(USER_DETAIL_KEY, currentuser);
                startActivity(intent);
            }
        });

        currentuser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        List<Lecture> lectures = new ArrayList<>(currentuser.getLectures());

        //recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_lecture);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        SearchLectureAdapter searchLectureAdapter = new SearchLectureAdapter(this, lectures);
        recyclerView.setAdapter(searchLectureAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLectureActivity.this, PopUpActivity.class);
                intent.putExtra(USER_DETAIL_KEY, currentuser);
                startActivity(intent);
            }
        });
    }

}
