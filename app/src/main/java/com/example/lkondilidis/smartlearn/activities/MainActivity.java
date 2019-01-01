package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.ItemAdapter;
import com.example.lkondilidis.smartlearn.adapters.TutorAdapter;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.services.*;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final AppCompatActivity activity = MainActivity.this;
    ActionBarDrawerToggle mToggle;
    DrawerLayout drawerLayout;
    ListView tutorslv;
    TutorAdapter tutorAdapter;
    User currentuser;
    SQLiteDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //Test
        //ArrayList<User> userArrayList = new ArrayList<>();
       // userArrayList.add(new User());
       // userArrayList.add(new User());

       // ListView myListView = findViewById(R.id.myListView);
       // ItemAdapter ia = new ItemAdapter(this, userArrayList);
      //  myListView.setAdapter(ia);

        tutorslv = (ListView) findViewById(R.id.myListView);
        ArrayList<User> atutors = new ArrayList<User>();
        // initialize the adapter
        tutorAdapter = new TutorAdapter(this, atutors);
        // attach the adapter to the ListView
        tutorslv.setAdapter(tutorAdapter);
        tutorSelectedListener();
        fetchTutors();

        ServerTask serverTask = new ServerTask(atutors);
        serverTask.execute();
    }

    private void fetchTutors() {
        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        databaseHelper = new SQLiteDBHelper(activity);
        //current User
        currentuser = databaseHelper.getUserEmail(emailFromIntent);
        tutorAdapter.clear();
        final List<User> users = databaseHelper.getAllUser();
        tutorAdapter.clear();
        // Load model objects into the adapter
        for (User user : users) {
            tutorAdapter.add(user); // add book through the adapter
        }
    }

    public void tutorSelectedListener() {
        tutorslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetail = new Intent(MainActivity.this, DetailActivity.class);
                showDetail.putExtra("EMAIL", tutorAdapter.getItem(position).getEmail());
                startActivity(showDetail);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true; //TODO: create action for the settings menu
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");

        switch (item.getItemId()){
            case R.id.profile:
            Intent profileactivity = new Intent(this, ProfileActivity.class);
            profileactivity.putExtra("EMAIL", email);
            startActivity(profileactivity);
        }
        return false;
    }

}
