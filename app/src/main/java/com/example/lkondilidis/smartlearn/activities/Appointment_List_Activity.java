package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ListView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.AppointmentAdapter;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;

public class Appointment_List_Activity extends AppCompatActivity {
    private final AppCompatActivity activity = Appointment_List_Activity.this;

    private DrawerLayout drawerLayout;

    User currentuser;

    TextView usernameHeader;

    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);


        //Drawer
        drawerLayout = findViewById(R.id.drawer_appointment);
        NavigationView navigationView = findViewById(R.id.navigation_view_appointment);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //listview
        ListView myListViewappoint = (ListView) findViewById(R.id.listViewMyAppointments);
        for(Appointment a:currentuser.getYourAppointments()){
            a.setAppointmentAuthor(currentuser);
        }
        ArrayList<Appointment> myAppointmentlist = new ArrayList<>(currentuser.getYourAppointments());
        AppointmentAdapter myAppAdapter = new AppointmentAdapter(getApplicationContext(), R.layout.apppointment_list_item, currentuser);
        for(Appointment a: myAppointmentlist){
            myAppAdapter.add(a);
        }
        myListViewappoint.setAdapter(myAppAdapter);



        //listview
        ListView userListViewappoint = (ListView) findViewById(R.id.listViewUserAppointments);
        for(Appointment a:currentuser.getUserAppointments()){
            a.setAppointmentUser(currentuser);
        }
        ArrayList<Appointment> userAppointmentlist = new ArrayList<>(currentuser.getUserAppointments());
        AppointmentAdapter userAppAdapter = new AppointmentAdapter(getApplicationContext(), R.layout.apppointment_list_item, currentuser);
        for(Appointment a: userAppointmentlist){
            userAppAdapter.add(a);
        }
        userAppAdapter.setOnClick();
        userListViewappoint.setAdapter(userAppAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        usernameHeader = (TextView) findViewById(R.id.usernameHeader);
        usernameHeader.setText(currentuser.getName());
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
