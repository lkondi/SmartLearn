package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.R;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivUserImage;
    private TextView userName;
    private TextView userNickname;
    private TextView userStudies;
    private TextView userPlan;
    private TextView userRatings;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Fetch views
        ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        userName = (TextView) findViewById(R.id.userName);
        userNickname = (TextView) findViewById(R.id.userNickname);
        userStudies = (TextView) findViewById(R.id.userStudies);
        userPlan = (TextView) findViewById(R.id.userPlan);
        userRatings = (TextView) findViewById(R.id.userRatings);

    }

    private void loadUser(User user) {
   //TODO
    }
}
