package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.sql.SQLiteDBHelper;

public class DetailActivity extends AppCompatActivity {
    private final AppCompatActivity activity = DetailActivity.this;
    private ImageView ivUserImage;
    private TextView userName;
    private TextView userEmail;
    private TextView userNickname;
    private TextView userStudies;
    private TextView userSubject;
    private TextView userPlan;
    private TextView userRatings;
    private User user;
    private SQLiteDBHelper dataBaseHelper;
    private static String STRING_EMPTY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Fetch views
        ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        userName = (TextView) findViewById(R.id.userName);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userNickname = (TextView) findViewById(R.id.userNickname);
        userStudies = (TextView) findViewById(R.id.userStudies);
        userSubject = (TextView) findViewById(R.id.userSubject);
        userPlan = (TextView) findViewById(R.id.userPlan);
        userRatings = (TextView) findViewById(R.id.userRatings);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        dataBaseHelper = new SQLiteDBHelper(activity);

        //current User
        User currentuser = dataBaseHelper.getUserEmail(emailFromIntent);
        loadUser(currentuser);

    }

    private void loadUser(User user) {

        if (!STRING_EMPTY.equals(user.getName())) {
            userName.setText(user.getName());
        }
        if (!STRING_EMPTY.equals(user.getEmail())) {
            userEmail.setText(user.getEmail());
        }
        if (!STRING_EMPTY.equals(user.getNickname())) {
            userNickname.setText(user.getNickname());
        }
        if (!STRING_EMPTY.equals(user.getStudies())) {
            userStudies.setText(user.getStudies());
        }
        if (!STRING_EMPTY.equals(user.getSubject())) {
            userSubject.setText(user.getSubject());
        }
        if (!STRING_EMPTY.equals(user.getPlan())) {
            userPlan.setText(user.getPlan());
        }
        else {
            Toast.makeText(DetailActivity.this, "Please fill you personal data",
                    Toast.LENGTH_LONG).show();
        }

    }
}
