package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
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
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.services.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActionBarDrawerToggle mToggle;
    DrawerLayout drawerLayout;

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
        ArrayList<User> userArrayList = new ArrayList<>();
        userArrayList.add(new User());
        userArrayList.add(new User());
        //TODO: create Database for useres

        ServerTask serverTask = new ServerTask(userArrayList);
        serverTask.execute();



        ListView myListView = findViewById(R.id.myListView);
        ItemAdapter ia = new ItemAdapter(this, userArrayList);
        myListView.setAdapter(ia);




        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //TODO: show Tutoractivity

                Intent showDetail = new Intent(getApplicationContext(), DetailActivity.class);
                showDetail.putExtra("de.lmu.sajko.ITEM.INDEX", i);
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
