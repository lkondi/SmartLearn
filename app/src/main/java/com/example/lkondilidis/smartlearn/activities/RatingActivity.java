package com.example.lkondilidis.smartlearn.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.RadioButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DatabaseHelper;

import com.example.lkondilidis.smartlearn.R;

public class RatingActivity extends AppCompatActivity {
    private final AppCompatActivity activity = RatingActivity.this;
    private DatabaseHelper dataBaseHelper;
    User selecteduser;
    private User currentuser;
    public static final String USER_DETAIL_KEY = "currentuser";
    private int ratingStars;
    private String ratingDes;

    private DrawerLayout drawerLayout;

    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mFeedback;
    Button mSendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        selecteduser = (User) getIntent().getSerializableExtra(MainActivity.SELECTED_USER_DETAIL_KEY);
        dataBaseHelper = new DatabaseHelper(activity);

        currentuser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_detail);
        NavigationView navigationView = findViewById(R.id.navigation_view_detail);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
        mFeedback = (EditText) findViewById(R.id.etFeedback);
        mSendFeedback = (Button) findViewById(R.id.btnSubmit);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Sehr schlecht");
                        ratingStars = 1;
                        break;
                    case 2:
                        mRatingScale.setText("Braucht etwas Verbesserung");
                        ratingStars = 2;
                        break;
                    case 3:
                        mRatingScale.setText("Gut");
                        ratingStars = 3;
                        break;
                    case 4:
                        mRatingScale.setText("Sehr gut");
                        ratingStars = 4;
                        break;
                    case 5:
                        mRatingScale.setText("Genial. Ich liebe diesen Tutor");
                        ratingStars = 5;
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(RatingActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    selecteduser.setRatingStars(ratingStars);
                    selecteduser.setRatingDes(mFeedback.getText().toString());
                    dataBaseHelper.updateUser(selecteduser);
                    mFeedback.setText("");
                    mRatingBar.setRating(0);
                    Toast.makeText(RatingActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
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