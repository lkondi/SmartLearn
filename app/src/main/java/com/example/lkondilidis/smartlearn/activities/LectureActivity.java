package com.example.lkondilidis.smartlearn.activities;

import android.os.Parcelable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.CSVReader;

import java.io.InputStream;
import java.util.List;

import com.example.lkondilidis.smartlearn.adapters.LectureAdapter;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Lecture;


public class LectureActivity extends AppCompatActivity {
    private ListView listView;
    private LectureAdapter itemArrayAdapter;
    private DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new LectureAdapter(getApplicationContext(), R.layout.lecture_item);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_lecture);
        NavigationView navigationView = findViewById(R.id.navigation_view_lecture);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_lecture);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        InputStream inputStream = getResources().openRawResource(R.raw.stats);
        CSVReader csvFile = new CSVReader(inputStream);
        List<Lecture> lectureList = csvFile.read();

        for(Lecture lectureData:lectureList ) {
            itemArrayAdapter.add(lectureData);
        }
        itemArrayAdapter.notifyDataSetChanged();
    }
}
