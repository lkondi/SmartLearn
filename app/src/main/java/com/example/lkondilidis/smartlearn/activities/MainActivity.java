package com.example.lkondilidis.smartlearn.activities;

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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.Interfaces.StatusLectureFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.fragments.ChatFragment;
import com.example.lkondilidis.smartlearn.fragments.MainFragment;
import com.example.lkondilidis.smartlearn.helpers.CSVReader;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerLectureTask;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";
    DrawerLayout drawerLayout;
    SearchAdapter searchAdapter;


    User currentuser;
    TextView usernameHeader;

    BottomNavigationView bottomNavigationView;
    ChatFragment chatFragment;
    MainFragment mainFragment;

    String action;

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentuser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        action = getIntent().getAction();

        //lectures
     /*   List<Lecture>  lectures = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.stats);
        CSVReader csvFile = new CSVReader(inputStream);
        lectures = csvFile.read();

        List<Lecture>  addedlectures = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        for(Lecture l: lectures){
            if(lectureNotAdded(addedlectures, l)){
                if(l.getId()>999)
                jsonArray.put(l.convertToJSON());
            }
        }

        //!!!!!!
        //connect to server and fetch Lectures
        ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        auth.setHttpMethod("POST");
        auth.setUrlPath("lectures");
        auth.setPayloadArray(jsonArray);
        ServerLectureTask serverLectureTask = new ServerLectureTask(new ArrayList<Lecture>(), currentuser, auth, StatusLectureFlag.SERVER_STATUS_GET_LECTURE);
        serverLectureTask.execute();*/

        //Drawer
        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        chatFragment = new ChatFragment();
        mainFragment = new MainFragment();

        if(action != null && action.equals("ChatFragment")){
            setFragment(chatFragment);
        } else {
            setFragment(mainFragment);
        }

        bottomNavigationView =  findViewById(R.id.bottomDrawer);
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
    }

    private boolean lectureNotAdded(List<Lecture> addedlectures, Lecture l) {
        for(Lecture lecture: addedlectures){
            if(l.getId()==lecture.getId()){
                return false;
            }
        }
        return true;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }

    private void smartfetchUsers() {

        //ArrayList<User> userDetails = new ArrayList<>();
        //User Details
        String subject = currentuser.getSubject();
        String plan = currentuser.getPlan();

        if (subject == null) { subject = "";}
        if (plan == null) { plan = "";}

        //userDetails.addAll(databaseHelper.getAllTutorsSmart(subject));


        //userArrayList.addAll(databaseHelper.getAllTutorsSmart(subject));
        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        usernameHeader = findViewById(R.id.usernameHeader);
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

    public User getCurrentuser(){
        return this.currentuser;
    }
}