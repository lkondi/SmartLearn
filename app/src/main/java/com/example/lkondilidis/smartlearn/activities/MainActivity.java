package com.example.lkondilidis.smartlearn.activities;

import android.app.SearchManager;
import android.app.UiAutomation;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.ExampleAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.services.ServerTask;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnSuggestionListener {

    private final AppCompatActivity activity = MainActivity.this;
    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";
    DrawerLayout drawerLayout;
    SearchView searchView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter searchAdapter;
    private static String STRING_EMPTY = "";

    ArrayList<User> userArrayList;
    SQLiteDBHelper databaseHelper;

    String[] lectures = {"Analysis", "MSP", "Datenbanksysteme"};
    ArrayList<String> lectures2 = new ArrayList<String>(Arrays.asList(lectures));

    User currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentuser = (User) getIntent().getSerializableExtra(LoginActivity.USER_DETAIL_KEY);
        databaseHelper = new SQLiteDBHelper(activity);

        //Drawer
        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));


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



        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(this, userArrayList, currentuser.getEmail());
        recyclerView.setAdapter(searchAdapter);

        //fetch users
        smartfetchUsers();



        //searchView
        SearchManager manager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        //myList = findViewById(R.id.listView1);
        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnSuggestionListener(this);
        searchView.setSubmitButtonEnabled(true);

        String[] columns=new String[]{"_id","text"};
        Object[] temp=new Object[]{"0","default"};
        final MatrixCursor cursor=new MatrixCursor(columns);
        for(int j=0;j<lectures2.size();j++)
        {
            temp[0]=j;
            temp[1]=lectures2.get(j);

            cursor.addRow(temp);
            System.out.println(j+": "+temp[1]+" added to cursor");
        }
        cursor.moveToFirst();
        System.out.println("This one first!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        ExampleAdapter exampleAdapter = new ExampleAdapter(getBaseContext(),cursor,lectures2);
        searchView.setSuggestionsAdapter(exampleAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //myList.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("changed");
                //if(myList.getVisibility() == View.GONE) {
                //myList.setVisibility(View.VISIBLE);
                //}
                load(s);
                return false;
            }
        });

        //!!!!!!
        //connect to server
        ServerTask serverTask = new ServerTask(userArrayList, this);
        serverTask.execute();

    }

    private void fetchUsers() {
        userArrayList.addAll(databaseHelper.getAllTutors());
        searchAdapter.notifyDataSetChanged();
    }

    private void smartfetchUsers() {
        //User Details
        String studies = currentuser.getStudies();
        String subject = currentuser.getSubject();
        String plan = currentuser.getPlan();

        if (subject == null) { subject = "";}
        if (plan == null) { plan = "";}

        userArrayList.addAll(databaseHelper.getAllTutorsSmart(subject, plan));
        searchAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void load(String s) {

        String[] columns=new String[]{"_id","text"};
        Object[] temp=new Object[]{"0","default"};
        final MatrixCursor cursor=new MatrixCursor(columns);
        for(int j=0;j<lectures2.size();j++)
        {
            temp[0]=j;
            temp[1]=lectures2.get(j);

            cursor.addRow(temp);
            System.out.println(j+": "+temp[1]+" added to cursor");
        }
        cursor.moveToFirst();


        ExampleAdapter exampleAdapter = new ExampleAdapter(getBaseContext(),cursor,lectures2);
        searchView.setSuggestionsAdapter(exampleAdapter);


        //myList.setAdapter(exampleAdapter);
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
    public boolean onSuggestionSelect(int i) {
        System.out.println("It works selected");
        return true;
    }

    @Override
    public boolean onSuggestionClick(int i) {
        //myList.setVisibility(View.GONE);
        String selectedLectureName = lectures2.get(i);

        List<User> tutorList = new ArrayList<>();
        
        searchAdapter = new SearchAdapter(this, tutorList, currentuser.getEmail());
        recyclerView.setAdapter(searchAdapter);

        //userArrayList.remove(userArrayList.size()-1);
        //searchAdapter.notifyDataSetChanged();
        return true;
    }
}