package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import ca.antonious.materialdaypicker.MaterialDayPicker;


public class ProfileActivityTutor extends AppCompatActivity{
    private final AppCompatActivity activity = ProfileActivityTutor.this;

    private EditText studiesedit;
    private AppCompatTextView textViewLinkLectures, textViewLinkAppointments, textViewLinkRatings;
    private Button btnSubmit, btn_pop;

    private String intentAction;
    private User currentuser;

    private static String STRING_EMPTY = "";
    public static final String USER_DETAIL_KEY = "currentuser";

    private Boolean changed;
    private User userBackUp;
    MaterialDayPicker materialDayPicker;
    ArrayList<String> plan;
    String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tutor);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        userBackUp = currentuser;
        changed = false;
        action = intent.getAction();
        intentAction = intent.getAction();

        Toolbar toolbar = findViewById(R.id.toolbarprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivityTutor.this, ProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(USER_DETAIL_KEY, currentuser);
                startActivity(intent);
            }
        });


        initViews();

    }

    private void initViews() {
        studiesedit = (EditText) findViewById(R.id.studiesedit);

        textViewLinkLectures = (AppCompatTextView) findViewById(R.id.textViewLinkLectures);
        textViewLinkAppointments = (AppCompatTextView) findViewById(R.id.textViewLinkAppointments);
        textViewLinkRatings = (AppCompatTextView) findViewById(R.id.textViewLinkRatings);

        btn_pop = (Button) findViewById(R.id.btn_pop);
        btn_pop.setText("Add or change your subjects");
        btn_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivityTutor.this, UserLectureActivity.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        materialDayPicker = (MaterialDayPicker) findViewById(R.id.day_picker);

        materialDayPicker.setDaySelectionChangedListener(new MaterialDayPicker.DaySelectionChangedListener() {
            @Override
            public void onDaySelectionChanged(List<MaterialDayPicker.Weekday> selectedDays) {
                List<MaterialDayPicker.Weekday> daysSelected = materialDayPicker.getSelectedDays();
                plan = new ArrayList<>();
                for (MaterialDayPicker.Weekday day : daysSelected){
                    switch (day) {
                        case MONDAY:
                            plan.add("Montag");
                            break;
                        case TUESDAY:
                            plan.add("Dienstag");
                            break;
                        case WEDNESDAY:
                            plan.add("Mittwoch");
                            break;
                        case THURSDAY:
                            plan.add("Donnerstag");
                            break;
                        case FRIDAY:
                            plan.add("Friday");
                            break;
                        case SATURDAY:
                            plan.add("Samstag");
                            break;
                        case SUNDAY:
                            plan.add("Sonntag");
                            break;
                    }
                }
            }
        });

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(userBackUp.isChanged()){
                    currentuser = userBackUp;
                    updateUser();
                }else {
                    //TODO: print: "you didnt change anything"
                    System.out.println("nothing changed");
                }

            }
        });

    }

    private void updateUser() {

        String SEPARATOR = ", ";
        StringBuilder planBuilder = new StringBuilder();

        for(String plan : plan){
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


        if (!STRING_EMPTY.equals(studiesedit.getText().toString())) {
            userBackUp.setStudies(studiesedit.getText().toString());
            userBackUp.setPlan(planfinal);
            userBackUp.setNickname("Tutor");
            userBackUp.setChanged(true);

            try {
                updateUserOnServer();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            userBackUp.updateUser(userBackUp);

            Toast.makeText(ProfileActivityTutor.this, "EDITED!",
                    Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(ProfileActivityTutor.this, "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();
        }

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
        }
        ServerUserTask serverUserTask = new ServerUserTask(null, this, auth, currentuser, mainActivity, StatusUserFlag.SERVER_STATUS_UPDATE_USER);
        serverUserTask.execute();
    }
}