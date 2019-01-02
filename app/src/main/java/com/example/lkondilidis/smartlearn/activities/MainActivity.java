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
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.UserAdapter;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.services.*;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final AppCompatActivity activity = MainActivity.this;
    public static final String USER_DETAIL_KEY = "selecteduser";
    ActionBarDrawerToggle mToggle;
    DrawerLayout drawerLayout;
    ListView userslv;
    UserAdapter userAdapter;
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

        userslv = (ListView) findViewById(R.id.lvusers);
        ArrayList<User> ausers = new ArrayList<User>();
        // initialize the adapter
        userAdapter = new UserAdapter(this, ausers);
        // attach the adapter to the ListView
        userslv.setAdapter(userAdapter);
        fetchUsers();
        userSelectedListener();

        ServerTask serverTask = new ServerTask(ausers);
        serverTask.execute();
    }

    private void fetchUsers() {
        databaseHelper = new SQLiteDBHelper(activity);
        final List<User> users = databaseHelper.getAllTutors();
        userAdapter.clear();
        // Load model objects into the adapter
        for (User user : users) {
                userAdapter.add(user);
        }
    }

    public void userSelectedListener() {
        userslv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showDetail = new Intent(MainActivity.this, DetailActivity.class);
                showDetail.putExtra(USER_DETAIL_KEY, userAdapter.getItem(position));
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
