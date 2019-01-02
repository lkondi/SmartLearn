package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.support.v7.widget.SearchView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.services.*;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnSuggestionListener {

    private final AppCompatActivity activity = MainActivity.this;
    public static final String USER_DETAIL_KEY = "selecteduser";
    ActionBarDrawerToggle mToggle;
    DrawerLayout drawerLayout;
    SearchView searchView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter searchAdapter;

    ArrayList<User> userArrayList;
    SQLiteDBHelper databaseHelper;

    ArrayAdapter<String> arrayAdapter;
    String[] lectures = {"Analysis", "MSP", "Datenbanksysteme"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Drawer
        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        //Test
        userArrayList = new ArrayList<>();
        //userArrayList.add(new User(1, "Wanja", "example@lmu.de", null, 0, "sadsasfasgasasas"));
        //userArrayList.add(new User(2, "Lydia", "example2@lmu.de", null, 0, "!!!!!!!!!!!!!"));
        //TODO: create Database for useres

        //!!!!!!
        //connect to server
        ServerTask serverTask = new ServerTask(userArrayList);
        serverTask.execute();



        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lectures);


        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(this, userArrayList);
        recyclerView.setAdapter(searchAdapter);
        fetchUsers();




        searchView = findViewById(R.id.searchView);
        searchView.setOnSuggestionListener(this);
        searchView.setSuggestionsAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                lectures,
                new int[]{android.R.layout.simple_list_item_1},
                0));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void fetchUsers() {
        databaseHelper = new SQLiteDBHelper(activity);
        userArrayList.addAll(databaseHelper.getAllTutors());
        searchAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onSuggestionSelect(int i) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int i) {
        return false;
    }
}
