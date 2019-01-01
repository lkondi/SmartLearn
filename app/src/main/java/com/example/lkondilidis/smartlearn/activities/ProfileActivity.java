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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.InputValidation;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO: edit profile image

public class ProfileActivity extends AppCompatActivity {
    private final AppCompatActivity activity = ProfileActivity.this;
    private static final int SELECT_PICTURE = 0;

    TextView textViewName, textViewEmail, textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewRatings;
    EditText editTextNickname, editTextStudies, editTextSubject, editTextPlan, editTextRatings;
    SQLiteDBHelper dataBaseHelper;

    ImageView imageView;

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

        LinearLayout displayArea = (LinearLayout) findViewById(R.id.displayArea);
        displayArea.setVisibility(LinearLayout.VISIBLE);

        LinearLayout displayNicknameArea = (LinearLayout) findViewById(R.id.nicknamelayoutdisplay);
        LinearLayout editNicknameArea = (LinearLayout) findViewById(R.id.nicknamelayoutedit);
        displayNicknameArea.setVisibility(LinearLayout.VISIBLE);
        editNicknameArea.setVisibility(LinearLayout.GONE);

        LinearLayout editArea = (LinearLayout) findViewById(R.id.editArea);
        editArea.setVisibility(LinearLayout.GONE);


        textViewName= (TextView) findViewById(R.id.name);
        textViewEmail = (TextView) findViewById(R.id.email);

        //nickname
        textViewNickname = (TextView) findViewById(R.id.nicknametext);
        editTextNickname = (EditText) findViewById(R.id.nicknameedit);
        //studies
        textViewStudies = (TextView) findViewById(R.id.studiestext);
        editTextStudies = (EditText) findViewById(R.id.studiesedit);
        //subject
        textViewSubject = (TextView) findViewById(R.id.subjecttext);
        editTextSubject = (EditText) findViewById(R.id.subjectedit);
        //plan
        textViewPlan = (TextView) findViewById(R.id.plantext);
        editTextPlan = (EditText) findViewById(R.id.planedit);
        //ratings
        textViewRatings = (TextView) findViewById(R.id.ratingstext);
        editTextRatings = (EditText) findViewById(R.id.ratingsedit);

        imageView = (CircleImageView) findViewById(R.id.profile);

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

        appCompatButtonSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
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

        LinearLayout displayArea = (LinearLayout) findViewById(R.id.displayArea);
        displayArea.setVisibility(LinearLayout.VISIBLE);

        LinearLayout editArea = (LinearLayout) findViewById(R.id.editArea);
        editArea.setVisibility(LinearLayout.GONE);

        LinearLayout displayNicknameArea = (LinearLayout) findViewById(R.id.nicknamelayoutdisplay);
        LinearLayout editNicknameArea = (LinearLayout) findViewById(R.id.nicknamelayoutedit);
        displayNicknameArea.setVisibility(LinearLayout.VISIBLE);
        editNicknameArea.setVisibility(LinearLayout.GONE);

    }

    public void editClicked() {
        LinearLayout displayArea = (LinearLayout) findViewById(R.id.displayArea);
        displayArea.setVisibility(LinearLayout.GONE);

        LinearLayout editArea = (LinearLayout) findViewById(R.id.editArea);
        editArea.setVisibility(LinearLayout.VISIBLE);

        LinearLayout displayNicknameArea = (LinearLayout) findViewById(R.id.nicknamelayoutdisplay);
        LinearLayout editNicknameArea = (LinearLayout) findViewById(R.id.nicknamelayoutedit);
        displayNicknameArea.setVisibility(LinearLayout.GONE);
        editNicknameArea.setVisibility(LinearLayout.VISIBLE);

    }

}