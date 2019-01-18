package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO: edit profile image

public class ProfileActivity extends AppCompatActivity {
    private final AppCompatActivity activity = ProfileActivity.this;

    TextView textViewName, textViewEmail, textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewRating, usernameHeader;
    EditText editTextStudies, editTextSubject;

    ImageView imageView;

    private AppCompatButton appCompatButtonSubmit;
    private ImageButton editButton;

    private static String STRING_EMPTY = "";

    private CheckBox tutorcheck, mocheck, dicheck, micheck, docheck, frcheck, sacheck, socheck;
    ArrayList<String> plan;

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser;

    private RatingBar rating;

    public static final String USER_DETAIL_KEY = "currentuser";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);

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
        rating = (RatingBar) findViewById(R.id.ratingProvider);
        textViewRating = (TextView) findViewById(R.id.ratingText);

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

        rating.setNumStars(currentuser.getRatingStars());

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
        if (!STRING_EMPTY.equals(currentuser.getRatingDes())) {
            textViewRating.setText(currentuser.getRatingDes());
        }

        else {
            Toast.makeText(ProfileActivity.this, "Please fill you personal data",
                    Toast.LENGTH_LONG).show();
        }

        if(currentuser.getNickname() != null && currentuser.getNickname().equals("Tutor")){
            tutorcheck.setChecked(true);
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


    private void isTutor() {
        if (((CheckBox) tutorcheck).isChecked()) {


            currentuser.setNickname("Tutor");
            currentuser.updateUser(currentuser);
            textViewNickname.setText(currentuser.getNickname());

            Toast.makeText(ProfileActivity.this, "You are a tutor!",
                    Toast.LENGTH_LONG).show();
        }
        else {

            currentuser.setNickname("Student");
            currentuser.updateUser(currentuser);
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

                try {
                    updateUserOnServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                currentuser.updateUser(currentuser);
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

    private void updateUserOnServer() throws JSONException {
        ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        auth.setHttpMethod("POST");
        auth.setUrlPath("update/"+currentuser.getId());
        JSONObject payload = new JSONObject();
        payload.put("nickname", currentuser.getNickname());
        payload.put("subject", currentuser.getSubject());
        payload.put("studies", currentuser.getStudies());
        payload.put("plan", currentuser.getPlan());
        auth.setPayload(payload);
        Intent mainActivity = null;
        if(intentAction == "register") {
            mainActivity = new Intent(this, MainActivity.class);
            mainActivity.putExtra(USER_DETAIL_KEY, currentuser);
            //startActivity(mainActivity);
        }
        ServerTask serverTask = new ServerTask(null, this, auth, currentuser, mainActivity, 0);
        serverTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        usernameHeader = (TextView) findViewById(R.id.usernameHeader);
        usernameHeader.setText(currentuser.getName());

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