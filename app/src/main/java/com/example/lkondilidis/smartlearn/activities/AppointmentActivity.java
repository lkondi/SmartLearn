package com.example.lkondilidis.smartlearn.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.R;

public class AppointmentActivity extends AppCompatActivity {
    private final AppCompatActivity activity = AppointmentActivity.this;
    User selecteduser;
    private User currentuser;
    public static final String USER_DETAIL_KEY = "currentuser";
    private int ratingStars;
    private String ratingDes;

    private DrawerLayout drawerLayout;

    TextView name;
    EditText subject, time, date;
    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        selecteduser = (User) getIntent().getSerializableExtra(MainActivity.SELECTED_USER_DETAIL_KEY);

        currentuser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);

        subject = (EditText) findViewById(R.id.subject1);
        date = (EditText) findViewById(R.id.date1);
        time = (EditText) findViewById(R.id.time1);
        confirm = (Button) findViewById(R.id.confirm);
        name = (TextView) findViewById(R.id.name1);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_detail);
        NavigationView navigationView = findViewById(R.id.navigation_view_detail);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        name.setText(selecteduser.getName());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subject.getText().toString().isEmpty() && date.getText().toString().isEmpty() &&
                        time.getText().toString().isEmpty()) {
                    Toast.makeText(AppointmentActivity.this, "Please fill in text box", Toast.LENGTH_LONG).show();
                } else {
                    //set values
                    currentuser.setAppuser(selecteduser.getName());
                    currentuser.setAppsubject(subject.getText().toString());
                    currentuser.setAppdate(date.getText().toString());
                    currentuser.setApptime(time.getText().toString());
                    currentuser.updateUser(currentuser);

                    selecteduser.setAppuser(currentuser.getName());
                    selecteduser.setAppsubject(subject.getText().toString());
                    selecteduser.setAppdate(date.getText().toString());
                    selecteduser.setApptime(time.getText().toString());
                    selecteduser.updateUser(selecteduser);

                    subject.setText("");
                    time.setText("");
                    date.setText("");

                    Toast.makeText(AppointmentActivity.this, "Thank you for scheduling", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
