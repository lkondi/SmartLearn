package com.example.lkondilidis.smartlearn.activities;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.InputValidation;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

public class ProfileActivity extends AppCompatActivity {
    private final AppCompatActivity activity = ProfileActivity.this;

    AppCompatTextView textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewRatings;
    AppCompatEditText editTextNickname, editTextStudies, editTextSubject, editTextPlan, editTextRatings;
    SQLiteDBHelper dataBaseHelper;
    TextView textViewName, textViewEmail;

    private AppCompatButton appCompatButtonSubmit;
    private ImageButton editButton;

    private static String STRING_EMPTY = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        initListeners();
        initObjects();

    }

    private void initViews() {

        textViewName= (TextView) findViewById(R.id.name);
        textViewEmail = (TextView) findViewById(R.id.email);

        //nickname
        textViewNickname = (AppCompatTextView) findViewById(R.id.nicknametext);
        editTextNickname = (AppCompatEditText) findViewById(R.id.nicknameedit);
        editTextNickname.setVisibility(View.GONE);
        //studies
        textViewStudies = (AppCompatTextView) findViewById(R.id.studiestext);
        editTextStudies = (AppCompatEditText) findViewById(R.id.studiesedit);
        editTextStudies.setVisibility(View.GONE);
        //subject
        textViewSubject = (AppCompatTextView) findViewById(R.id.subjecttext);
        editTextSubject = (AppCompatEditText) findViewById(R.id.subjectedit);
        editTextSubject.setVisibility(View.GONE);
        //plan
        textViewPlan = (AppCompatTextView) findViewById(R.id.plantext);
        editTextPlan = (AppCompatEditText) findViewById(R.id.planedit);
        editTextPlan.setVisibility(View.GONE);
        //ratings
        textViewRatings = (AppCompatTextView) findViewById(R.id.ratingstext);
        editTextRatings = (AppCompatEditText) findViewById(R.id.ratingsedit);
        editTextRatings.setVisibility(View.GONE);


        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        dataBaseHelper = new SQLiteDBHelper(activity);

        //current User
        User currentuser = dataBaseHelper.getUserEmail(emailFromIntent);

        //set Values
        textViewEmail.setText(emailFromIntent);
        textViewName.setText(dataBaseHelper.getUserEmail(emailFromIntent).getName());

        if (!STRING_EMPTY.equals(currentuser.getNickname())) {
            textViewNickname.setText(currentuser.getNickname());
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


        //buttons
        editButton = (ImageButton) findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editClicked();
            }
        });

        appCompatButtonSubmit = (AppCompatButton) findViewById(R.id.appCompatButtonSubmit);
        appCompatButtonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateUser();
            }
        });

    }

    private void initListeners() {
        appCompatButtonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateUser();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editClicked();
            }
        });
    }

    private void initObjects() {
        dataBaseHelper = new SQLiteDBHelper(activity);

    }

    private void updateUser() {

        String emailFromIntent = getIntent().getStringExtra("EMAIL");

            if (!STRING_EMPTY.equals(editTextNickname.getText().toString()) &&
                    !STRING_EMPTY.equals(editTextStudies.getText().toString())
                    && !STRING_EMPTY.equals(editTextSubject.getText().toString())
                    && !STRING_EMPTY.equals(editTextPlan.getText().toString())) {

                User userDetails = dataBaseHelper.getUserEmail(emailFromIntent);

                userDetails.setNickname(editTextNickname.getText().toString());
                userDetails.setStudies(editTextStudies.getText().toString());
                userDetails.setSubject(editTextSubject.getText().toString());
                userDetails.setPlan(editTextPlan.getText().toString());
                //userDetails.setRatings(Integer.parseInt(editTextRatings.getText().toString()));

                dataBaseHelper.updateUser(userDetails);

                textViewNickname.setText(userDetails.getNickname());
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

        textViewNickname.setVisibility(View.VISIBLE);
        editTextNickname.setVisibility(View.GONE);
        textViewStudies.setVisibility(View.VISIBLE);
        editTextStudies.setVisibility(View.GONE);
        textViewSubject.setVisibility(View.VISIBLE);
        editTextSubject.setVisibility(View.GONE);
        textViewPlan.setVisibility(View.VISIBLE);
        editTextPlan.setVisibility(View.GONE);
        textViewRatings.setVisibility(View.VISIBLE);
        editTextRatings.setVisibility(View.GONE);
    }

    public void editClicked() {
        textViewNickname.setVisibility(View.GONE);
        editTextNickname.setVisibility(View.VISIBLE);
        textViewStudies.setVisibility(View.GONE);
        editTextStudies.setVisibility(View.VISIBLE);
        textViewSubject.setVisibility(View.GONE);
        editTextSubject.setVisibility(View.VISIBLE);
        textViewPlan.setVisibility(View.GONE);
        editTextPlan.setVisibility(View.VISIBLE);
        textViewRatings.setVisibility(View.GONE);
        editTextRatings.setVisibility(View.VISIBLE);
    }


}