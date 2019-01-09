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

    TextView textViewName, textViewEmail, textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewRatings;
    EditText editTextStudies, editTextSubject;
    SQLiteDBHelper dataBaseHelper;

    ImageView imageView;

    private AppCompatButton appCompatButtonSubmit;
    private ImageButton editButton;

    private static String STRING_EMPTY = "";

    private CheckBox tutorcheck, mocheck, dicheck, micheck, docheck, frcheck, sacheck, socheck;
    ArrayList<String> plan;

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser;

    public static final String USER_DETAIL_KEY = "currentuser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        dataBaseHelper = new SQLiteDBHelper(activity);

        intentAction = intent.getAction();


        if(intentAction != "register"){
            //Drawer
            drawerLayout = findViewById(R.id.drawer_profile);
            NavigationView navigationView = findViewById(R.id.navigation_view_profile);
            navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

            //Toolbar
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

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
        textViewEmail.setText(currentuser.getEmail());
        textViewName.setText(currentuser.getName());

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
        if (!STRING_EMPTY.equals(currentuser.getRatings())) {
            textViewRatings.setText(String.valueOf(currentuser.getRatings()));
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

            dataBaseHelper = new SQLiteDBHelper(activity);

            currentuser.setNickname("Tutor");
            dataBaseHelper.updateUser(currentuser);
            textViewNickname.setText(currentuser.getNickname());

            Toast.makeText(ProfileActivity.this, "You are a tutor!",
                    Toast.LENGTH_LONG).show();
        }
        else {

            dataBaseHelper = new SQLiteDBHelper(activity);

            currentuser.setNickname("Student");
            dataBaseHelper.updateUser(currentuser);
            textViewNickname.setText(currentuser.getNickname());


            Toast.makeText(ProfileActivity.this, "You are a student!",
                    Toast.LENGTH_LONG).show();
        }
    }


    private void updateUser() {

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
        try {
            planfinal = planfinal.substring(0, planfinal.length() - SEPARATOR.length());
        }
        catch (Exception e){
            planfinal = "";
        }

        System.out.println(planfinal);

            if (!STRING_EMPTY.equals(editTextStudies.getText().toString())
                    && !STRING_EMPTY.equals(editTextSubject.getText().toString())
                    && !STRING_EMPTY.equals(planfinal)) {


                currentuser.setStudies(editTextStudies.getText().toString());
                currentuser.setSubject(editTextSubject.getText().toString());
                currentuser.setPlan(planfinal);


                dataBaseHelper.updateUser(currentuser);
                textViewStudies.setText(currentuser.getStudies());
                textViewSubject.setText(currentuser.getSubject());
                textViewPlan.setText(currentuser.getPlan());


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

        if(intentAction == "register") {
            Intent mainActivity = new Intent(this, MainActivity.class);
            mainActivity.putExtra(USER_DETAIL_KEY, currentuser);
            startActivity(mainActivity);
        }


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
        if(intentAction != "register") {
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}