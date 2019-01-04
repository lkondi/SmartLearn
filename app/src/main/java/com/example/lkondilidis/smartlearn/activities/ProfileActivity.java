package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.helpers.InputValidation;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO: edit profile image

public class ProfileActivity extends AppCompatActivity {
    private final AppCompatActivity activity = ProfileActivity.this;
    private static final int SELECT_PICTURE = 0;

    TextView textViewName, textViewEmail, textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewRatings;
    EditText editTextStudies, editTextSubject, editTextRatings;
    SQLiteDBHelper dataBaseHelper;

    ImageView imageView;

    private AppCompatButton appCompatButtonSubmit;
    private ImageButton editButton;

    private static String STRING_EMPTY = "";

    private CheckBox tutorcheck, mocheck, dicheck, micheck, docheck, frcheck, sacheck, socheck;
    ArrayList<String> plan;

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_profile);
        NavigationView navigationView = findViewById(R.id.navigation_view_profile);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));


        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        initViews();
        initObjects();

    }

    private void initViews() {

        LinearLayout displayArea = (LinearLayout) findViewById(R.id.displayArea);
        displayArea.setVisibility(LinearLayout.VISIBLE);

        LinearLayout editArea = (LinearLayout) findViewById(R.id.editArea);
        editArea.setVisibility(LinearLayout.GONE);

        textViewName= (TextView) findViewById(R.id.name);
        textViewEmail = (TextView) findViewById(R.id.email);

        //nickname
        textViewNickname = (TextView) findViewById(R.id.nicknametext);
        //studies
        textViewStudies = (TextView) findViewById(R.id.studiestext);
        editTextStudies = (EditText) findViewById(R.id.studiesedit);
        //subject
        textViewSubject = (TextView) findViewById(R.id.subjecttext);
        editTextSubject = (EditText) findViewById(R.id.subjectedit);
        //plan
        textViewPlan = (TextView) findViewById(R.id.plantext);

        //ratings
        textViewRatings = (TextView) findViewById(R.id.ratingstext);

        imageView = (CircleImageView) findViewById(R.id.profile);

        final String emailFromIntent = getIntent().getStringExtra("EMAIL");
        dataBaseHelper = new SQLiteDBHelper(activity);

        //current User
        final User currentuser = dataBaseHelper.getUserEmail(emailFromIntent);

        //checkbox
        tutorcheck = (CheckBox) findViewById(R.id.tutorcheck);
        mocheck = (CheckBox) findViewById(R.id.checkbox_mo);
        dicheck = (CheckBox) findViewById(R.id.checkbox_di);
        micheck = (CheckBox) findViewById(R.id.checkbox_mi);
        docheck = (CheckBox) findViewById(R.id.checkbox_do);
        frcheck = (CheckBox) findViewById(R.id.checkbox_fr);
        sacheck = (CheckBox) findViewById(R.id.checkbox_sa);
        socheck = (CheckBox) findViewById(R.id.checkbox_so);

        //set Values
        textViewEmail.setText(emailFromIntent);
        textViewName.setText(dataBaseHelper.getUserEmail(emailFromIntent).getName());

        if (!STRING_EMPTY.equals(currentuser.getNickname())) {
            textViewNickname.setText(currentuser.getNickname());

            if (currentuser.getNickname() == "Tutor") {
                tutorcheck.setChecked(true);
            }
            else {
                tutorcheck.setChecked(false);
            }
        }
        if (!STRING_EMPTY.equals(currentuser.getStudies())) {
            textViewStudies.setText(currentuser.getStudies());
        }
        if (!STRING_EMPTY.equals(currentuser.getSubject())) {
            textViewSubject.setText(currentuser.getSubject());
        }
        if (!STRING_EMPTY.equals(currentuser.getPlan())) {
            textViewPlan.setText(currentuser.getPlan());
        }
        else {
            Toast.makeText(ProfileActivity.this, "Please fill you personal data",
                    Toast.LENGTH_LONG).show();
        }

        tutorcheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isTutor();
            }
        });

        mocheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });
        dicheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });
        micheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });
        docheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });
        frcheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });
        sacheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });
        socheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked();
            }
        });



    //buttons
        editButton = (ImageButton) findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editClicked();
            }
        });

        appCompatButtonSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
        appCompatButtonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateUser();
            }
        });

    }

    private void initObjects() {
        dataBaseHelper = new SQLiteDBHelper(activity);

    }

    private void isTutor() {
        if (((CheckBox) tutorcheck).isChecked()) {

            String emailFromIntent = getIntent().getStringExtra("EMAIL");
            dataBaseHelper = new SQLiteDBHelper(activity);

            //current User
            User currentuser = dataBaseHelper.getUserEmail(emailFromIntent);
            currentuser.setNickname("Tutor");
            dataBaseHelper.updateUser(currentuser);
            textViewNickname.setText(currentuser.getNickname());

            Toast.makeText(ProfileActivity.this, "You are a tutor!",
                    Toast.LENGTH_LONG).show();
        }
        else {

            String emailFromIntent = getIntent().getStringExtra("EMAIL");
            dataBaseHelper = new SQLiteDBHelper(activity);

            //current User
            User currentuser = dataBaseHelper.getUserEmail(emailFromIntent);
            currentuser.setNickname("Student");
            dataBaseHelper.updateUser(currentuser);
            textViewNickname.setText(currentuser.getNickname());


            Toast.makeText(ProfileActivity.this, "You are a student!",
                    Toast.LENGTH_LONG).show();
        }
    }


    private void updateUser() {

        String emailFromIntent = getIntent().getStringExtra("EMAIL");

        ArrayList<String> plans = onCheckboxClicked();

        String SEPARATOR = ", ";
        StringBuilder planBuilder = new StringBuilder();

        for(String plan : plans){
            planBuilder.append(plan);
            planBuilder.append(SEPARATOR);
        }
        String planfinal = planBuilder.toString();
        System.out.println(planfinal);
        //Remove last comma
        planfinal = planfinal.substring(0, planfinal.length() - SEPARATOR.length());

        System.out.println(planfinal);

            if (!STRING_EMPTY.equals(editTextStudies.getText().toString())
                    && !STRING_EMPTY.equals(editTextSubject.getText().toString())
                    && !STRING_EMPTY.equals(planfinal)) {


                User userDetails = dataBaseHelper.getUserEmail(emailFromIntent);

                userDetails.setStudies(editTextStudies.getText().toString());
                userDetails.setSubject(editTextSubject.getText().toString());
                userDetails.setPlan(planfinal);
                //userDetails.setRatings(Integer.parseInt(editTextRatings.getText().toString()));

                dataBaseHelper.updateUser(userDetails);
                textViewStudies.setText(userDetails.getStudies());
                textViewSubject.setText(userDetails.getSubject());
                textViewPlan.setText(userDetails.getPlan());
                //textViewRatings.setText(userDetails.getRatings());

                submitClicked();
                Toast.makeText(ProfileActivity.this, "EDITED!",
                        Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(ProfileActivity.this, "One or more fields left empty!",
                        Toast.LENGTH_LONG).show();
            }

    }

    /**
     * empty all input edit text
     */
    private void submitClicked() {

        LinearLayout displayArea = (LinearLayout) findViewById(R.id.displayArea);
        displayArea.setVisibility(LinearLayout.VISIBLE);

        LinearLayout editArea = (LinearLayout) findViewById(R.id.editArea);
        editArea.setVisibility(LinearLayout.GONE);


    }

    public void editClicked() {
        LinearLayout displayArea = (LinearLayout) findViewById(R.id.displayArea);
        displayArea.setVisibility(LinearLayout.GONE);

        LinearLayout editArea = (LinearLayout) findViewById(R.id.editArea);
        editArea.setVisibility(LinearLayout.VISIBLE);


    }

    public ArrayList<String> onCheckboxClicked() {
        plan = new ArrayList<>();

        if(mocheck.isChecked()) {
            plan.add("Montag");
        }
        if(dicheck.isChecked()) {
            plan.add("Dienstag");
        }
        if(micheck.isChecked()) {
            plan.add("Mittwoch");
        }
        if(docheck.isChecked()) {
            plan.add("Donnerstag");
        }
        if(frcheck.isChecked()) {
            plan.add("Freitag");
        }
        if(sacheck.isChecked()) {
            plan.add("Samstag");
        }
        if(socheck.isChecked()) {
            plan.add("Sonntag");
        }

        return plan;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}