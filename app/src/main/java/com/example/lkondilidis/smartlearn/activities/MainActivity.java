package com.example.lkondilidis.smartlearn.activities;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.ExampleAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.fragments.ChatFragment;
import com.example.lkondilidis.smartlearn.fragments.MainFragment;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.helpers.SQLITEHelper;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerTask;

import java.util.ArrayList;
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
    SQLITEHelper databaseHelper;

    List<String> lectures;

    User currentuser;
    TextView usernameHeader;

    BottomNavigationView bottomNavigationView;
    ChatFragment chatFragment;
    MainFragment mainFragment;

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentuser = (User) getIntent().getSerializableExtra(LoginActivity.USER_DETAIL_KEY);
        //databaseHelper = new SQLITEHelper(activity);
        //userArrayList = new ArrayList<>();

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

        //!!!!!!
        //connect to server and fetch Users
        //ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        //auth.setHttpMethod("GET");
        //auth.setUrlPath("tutors/" + currentuser.getId());
        //ServerTask serverTask = new ServerTask(userArrayList, this, auth, currentuser, null);
        //serverTask.execute();

        //lectures
        //lectures = new ArrayList<>();
        //lectures = databaseHelper.getAllLectures();

        chatFragment = new ChatFragment();
        mainFragment = new MainFragment();

        setFragment(mainFragment);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomDrawer);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.bottom_chat:
                        setFragment(chatFragment);
                        return true;
                    case R.id.bottom_drawer_home:
                        setFragment(mainFragment);
                        return true;
                    case 2:
                        return true;
                    default:
                        return false;
                }
            }
        });


        //initViews();

        //fetch users
        //smartfetchUsers();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }

    private void initViews() {
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

        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(this, userArrayList, currentuser);
        recyclerView.setAdapter(searchAdapter);

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
        for(int j=0;j<lectures.size();j++)
        {
            temp[0]=j;
            temp[1]=lectures.get(j);

            cursor.addRow(temp);
            System.out.println(j+": "+temp[1]+" added to cursor");
        }
        cursor.moveToFirst();
        System.out.println("This one first!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        ExampleAdapter exampleAdapter = new ExampleAdapter(getBaseContext(),cursor,lectures);
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
    }

    private void load(String s) {

        String[] columns=new String[]{"_id","text"};
        Object[] temp=new Object[]{"0","default"};
        final MatrixCursor cursor=new MatrixCursor(columns);
        for(int j=0;j<lectures.size();j++)
        {
            temp[0]=j;
            temp[1]=lectures.get(j);

            cursor.addRow(temp);
            System.out.println(j+": "+temp[1]+" added to cursor");
        }
        cursor.moveToFirst();


        ExampleAdapter exampleAdapter = new ExampleAdapter(getBaseContext(),cursor,lectures);
        searchView.setSuggestionsAdapter(exampleAdapter);


        //myList.setAdapter(exampleAdapter);
    }

    private void fetchUsers() {
        userArrayList.addAll(databaseHelper.getAllTutors());
        searchAdapter.notifyDataSetChanged();
    }

    private void smartfetchUsers() {

        //ArrayList<User> userDetails = new ArrayList<>();
        //User Details
        String studies = currentuser.getStudies();
        String subject = currentuser.getSubject();
        String plan = currentuser.getPlan();

        if (subject == null) { subject = "";}
        if (plan == null) { plan = "";}

        //userDetails.addAll(databaseHelper.getAllTutorsSmart(subject));
        

        userArrayList.addAll(databaseHelper.getAllTutorsSmart(subject));
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

        usernameHeader = (TextView) findViewById(R.id.usernameHeader);
        usernameHeader.setText(currentuser.getName());

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
        String selectedLectureName = lectures.get(i);

        List<User> tutorListSearched = new ArrayList<>();

        for (User user : userArrayList) {
            if(user.getSubject() == selectedLectureName) {
                tutorListSearched.add(user);
            }
        }
        
        searchAdapter = new SearchAdapter(this, tutorListSearched, currentuser);
        recyclerView.setAdapter(searchAdapter);

        //userArrayList.remove(userArrayList.size()-1);
        //searchAdapter.notifyDataSetChanged();
        return true;
    }

    public User getCurrentuser(){
        return currentuser;
    }
}