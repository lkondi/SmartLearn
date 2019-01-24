package com.example.lkondilidis.smartlearn.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.R;

public class RatingActivity extends AppCompatActivity {
    private final AppCompatActivity activity = RatingActivity.this;
    User selecteduser;
    private User currentuser;
    public static final String USER_DETAIL_KEY = "currentuser";
    private int ratingStars;
    private String ratingDes;

    private DrawerLayout drawerLayout;

    RatingBar mRatingBar;
    TextView mRatingScale, usernameHeader;
    EditText mFeedback;
    Button mSendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        selecteduser = (User) getIntent().getSerializableExtra(MainActivity.SELECTED_USER_DETAIL_KEY);

        currentuser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);

        //Drawer
        drawerLayout = findViewById(R.id.drawer_detail);
        NavigationView navigationView = findViewById(R.id.navigation_view_detail);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mRatingBar = (RatingBar) findViewById(R.id.ratingBarup);
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
                    mFeedback.setText("");
                    mRatingBar.setRating(0);
                    Toast.makeText(RatingActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        usernameHeader = (TextView) findViewById(R.id.usernameHeader);
        usernameHeader.setText(currentuser.getName());

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