package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.helpers.SQLITEHelper;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;

public class Appointment_List_Activity extends AppCompatActivity {
    private final AppCompatActivity activity = Appointment_List_Activity.this;

    SQLITEHelper dataBaseHelper;

    private static String STRING_EMPTY = "";

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser;
    private ImageButton imagedelete;
    private TextView appointmentno;

    private TextView appuser, appsubject, appdate, apptime, usernameHeader;

    public static final String USER_DETAIL_KEY = "currentuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        dataBaseHelper = new SQLITEHelper(activity);

        intentAction = intent.getAction();

        //Drawer
        drawerLayout = findViewById(R.id.drawer_profile);
        NavigationView navigationView = findViewById(R.id.navigation_view_profile);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //initiate Views
        appuser = (TextView) findViewById(R.id.app_user);
        appdate = (TextView) findViewById(R.id.app_date);
        appsubject = (TextView) findViewById(R.id.app_subject);

        //appointments
        imagedelete = (ImageButton) findViewById(R.id.imagedelete);
        appointmentno = (TextView) findViewById(R.id.appointmentno);

        imagedelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User selecteduser = dataBaseHelper.getUserName(currentuser.getAppuser());
                selecteduser.setAppuser("");
                selecteduser.setAppsubject("");
                selecteduser.setAppdate("");
                selecteduser.setApptime("");
                dataBaseHelper.updateUser(selecteduser);


                currentuser.setAppuser("");
                currentuser.setAppsubject("");
                currentuser.setAppdate("");
                currentuser.setApptime("");
                dataBaseHelper.updateUser(currentuser);

                appointmentno.setText("Appointment deleted");
            }
        });

        if (!STRING_EMPTY.equals(currentuser.getAppuser())) {
            appuser.setText(currentuser.getAppuser());
        }
        if (!STRING_EMPTY.equals(currentuser.getAppsubject())) {
            appsubject.setText(currentuser.getAppsubject());
        }
        if (!STRING_EMPTY.equals(currentuser.getAppdate())) {
            appdate.setText(currentuser.getAppdate());
        }

        else {
            appuser.setText("");
            appsubject.setText("");
            appdate.setText("");

            Toast.makeText(Appointment_List_Activity.this, "You have no appointments",
                    Toast.LENGTH_LONG).show();
        }

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
