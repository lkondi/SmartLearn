package com.example.lkondilidis.smartlearn.activities;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

public class ProfileActivity extends AppCompatActivity {
    private final AppCompatActivity activity = ProfileActivity.this;

    TextView textViewName,textViewEmail,textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewBewertungen;
    SQLiteDBHelper dataBaseHelper;

    private AppCompatButton appCompatButtonSubmit;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private static String STRING_EMPTY = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dataBaseHelper = new SQLiteDBHelper(this);


        textViewName= (TextView) findViewById(R.id.name);
        textViewEmail = (TextView) findViewById(R.id.email);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);


        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        dataBaseHelper = new SQLiteDBHelper(activity);
        textViewEmail.setText(emailFromIntent);
        textViewName.setText(dataBaseHelper.getUserEmail(emailFromIntent).getName());

        appCompatButtonSubmit = (AppCompatButton) findViewById(R.id.appCompatButtonSubmit);
        appCompatButtonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateUser();
            }
        });
    }



    private void updateUser() {

        String emailFromIntent = getIntent().getStringExtra("EMAIL");

            if (!STRING_EMPTY.equals(textInputEditTextEmail.getText().toString()) &&
                    !STRING_EMPTY.equals(textInputEditTextPassword.getText().toString())) {

                User userDetails = dataBaseHelper.getUserEmail(emailFromIntent);

                userDetails.setEmail(textInputEditTextEmail.getText().toString());
                userDetails.setPassword(textInputEditTextPassword.getText().toString());

                dataBaseHelper.updateUser(userDetails);

                emptyInputEditText();

                textViewEmail.setText(userDetails.getEmail());

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
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }


}