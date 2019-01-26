package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO: edit profile image

public class ProfileActivityTest extends AppCompatActivity{
    private final AppCompatActivity activity = ProfileActivityTest.this;

    private TextView textViewName, textViewEmail, textViewNickname, usernameHeader, textViewDescriptionHeader, textViewDescription;
    private AppCompatTextView textViewLinkLectures, textViewLinkAppointments, textViewLinkRatings;
    private ImageView imageView;
    private DrawerLayout drawerLayout;
    private RatingBar rating;
    private AppCompatButton btn_tutor;

    private String intentAction;
    private User currentuser;

    private static String STRING_EMPTY = "";
    public static final String USER_DETAIL_KEY = "currentuser";


    private Boolean changed;
    private User userBackUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_test);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        userBackUp = currentuser;
        changed = false;

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
        textViewName = (TextView) findViewById(R.id.textView_username);
        textViewEmail = (TextView) findViewById(R.id.textView_useremail);

        rating = (RatingBar) findViewById(R.id.ratingProvider);
        btn_tutor = (AppCompatButton) findViewById(R.id.btnTutor);


        //nickname
        textViewNickname = (TextView) findViewById(R.id.textView_userrole);

        textViewDescriptionHeader = (TextView) findViewById(R.id.textView_description_head);
        textViewDescription = (TextView) findViewById(R.id.textView_description);


        textViewLinkLectures = (AppCompatTextView) findViewById(R.id.textViewLinkLectures);
        textViewLinkAppointments = (AppCompatTextView) findViewById(R.id.textViewLinkAppointments);
        textViewLinkRatings = (AppCompatTextView) findViewById(R.id.textViewLinkRatings);


/*        btn_tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivityTest.this, UserLectureActivity.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/

        textViewLinkLectures.setText("Add or change your subjects");
        textViewLinkLectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivityTest.this, UserLectureActivity.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        /*textViewLinkAppointments.setText("Add or change your subjects");
        textViewLinkAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivityTest.this, UserLectureActivity.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        textViewLinkRatings.setText("Add or change your subjects");
        textViewLinkRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivityTest.this, UserLectureActivity.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/


        //set Values
        textViewEmail.setText(userBackUp.getEmail());
        textViewName.setText(userBackUp.getName());

        if(currentuser.getNickname().equals("Tutor")){
            textViewDescriptionHeader.setText("Your Description:");
            textViewDescription.setText(currentuser.getPrivateInfo());
            rating.setNumStars(userBackUp.getRatingStars());
        }else{
            textViewDescriptionHeader.setVisibility(View.GONE);
            textViewDescription.setVisibility(View.GONE);
            rating.setVisibility(View.GONE);
        }

        if (!STRING_EMPTY.equals(userBackUp.getNickname())) {
            textViewNickname.setText(userBackUp.getNickname());
        } else {
            Toast.makeText(ProfileActivityTest.this, "Please fill you personal data",
                    Toast.LENGTH_LONG).show();
        }


        /*appCompatButtonSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
        appCompatButtonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(userBackUp.isChanged()){
                    currentuser = userBackUp;
                    updateUser();
                }else {
                    //TODO: print: "you didnt change anything"
                    System.out.println("nothing changed");
                }

            }
        });*/

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