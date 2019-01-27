package com.example.lkondilidis.smartlearn.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.AppointmentAdapter;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;
import java.util.List;

public class Appointment_List_Activity extends AppCompatActivity {
    private final AppCompatActivity activity = Appointment_List_Activity.this;

    private DrawerLayout drawerLayout;

    private String intentAction;
    User currentuser, selecteduser;
    AppointmentAdapter appAdapter;
    ListView listViewappoint;
    ImageView imageconfirmed, imagepending;
    ArrayList<Appointment> appointmentlist;
    TextView appuser, appsubject, appdate, usernameHeader;

    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        selecteduser = (User) intent.getSerializableExtra(MainActivity.SELECTED_USER_DETAIL_KEY);

        intentAction = intent.getAction();

        //Drawer
        drawerLayout = findViewById(R.id.drawer_appointment);
        NavigationView navigationView = findViewById(R.id.navigation_view_appointment);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //initiate Views
        appuser = (TextView) findViewById(R.id.author_name);
        appdate = (TextView) findViewById(R.id.app_date);
        appsubject = (TextView) findViewById(R.id.app_subject);
        imageconfirmed = (ImageView) findViewById(R.id.image_accepted);
        imagepending = (ImageView) findViewById(R.id.image_pending);

        //listview
        listViewappoint = (ListView) findViewById(R.id.listViewAppointments);
        appointmentlist = new ArrayList<>(currentuser.getYourAppointments());
        appAdapter = new AppointmentAdapter(getApplicationContext(), R.layout.apppointment_list_item);
        listViewappoint.setAdapter(appAdapter);

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
