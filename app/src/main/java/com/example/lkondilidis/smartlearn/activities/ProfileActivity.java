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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.*;
import java.io.*;
import android.net.*;
import android.database.*;
import android.provider.MediaStore;
import android.content.Context;
import android.provider.MediaStore.Images.Media;

import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO: edit profile image

public class ProfileActivity extends AppCompatActivity{
    private final AppCompatActivity activity = ProfileActivity.this;

    TextView textViewName, textViewEmail, textViewNickname, textViewStudies, textViewSubject, textViewPlan, textViewRating, usernameHeader;
    EditText editTextStudies, editTextSubject;

    ImageView imageView;

    private AppCompatButton appCompatButtonSubmit;
    private ImageButton editButton;

    private static String STRING_EMPTY = "";

    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;
    private ImageView profile;
    private static final int GALLERY = 1;

    private CheckBox tutorcheck, mocheck, dicheck, micheck, docheck, frcheck, sacheck, socheck;
    ArrayList<String> plan;

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser;

    private RatingBar rating;

    public static final String USER_DETAIL_KEY = "currentuser";
    private Button btn_pop;

    private Boolean changed;
    private User userBackUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        LinearLayout displayArea = (LinearLayout) findViewById(R.id.displayArea);
        displayArea.setVisibility(LinearLayout.VISIBLE);

        LinearLayout editArea = (LinearLayout) findViewById(R.id.editArea);
        editArea.setVisibility(LinearLayout.GONE);

        profile = (ImageView) findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(null);
                if (Image != null)
                    Image.recycle();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
            }
        });


        textViewName= (TextView) findViewById(R.id.name);
        textViewEmail = (TextView) findViewById(R.id.email);

        //nickname
        textViewNickname = (TextView) findViewById(R.id.nicknametext);
        //studies
        textViewStudies = (TextView) findViewById(R.id.studiestext);
        editTextStudies = (EditText) findViewById(R.id.studiesedit);
        //subject
        textViewSubject = (TextView) findViewById(R.id.subjecttext);
        btn_pop = (Button) findViewById(R.id.btn_pop);
        btn_pop.setText("Add or change your subjects");
        btn_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserLectureActivity.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });





        //editTextSubject = (EditText) findViewById(R.id.subjectedit);
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
        textViewEmail.setText(userBackUp.getEmail());
        textViewName.setText(userBackUp.getName());

        rating.setNumStars(userBackUp.getRatingStars());

        if (!STRING_EMPTY.equals(userBackUp.getNickname())) {
            textViewNickname.setText(userBackUp.getNickname());

            if (userBackUp.getNickname() == "Tutor") {
                tutorcheck.setChecked(true);
            }
            else {
                tutorcheck.setChecked(false);
            }
        }
        if (!STRING_EMPTY.equals(userBackUp.getStudies())) {
            textViewStudies.setText(userBackUp.getStudies());
        }
        if (!STRING_EMPTY.equals(userBackUp.getSubject())) {
            //textViewSubject.setText(userBackUp.getSubject());
        }
        if (!STRING_EMPTY.equals(userBackUp.getPlan())) {
            textViewPlan.setText(userBackUp.getPlan());
        }
        if (!STRING_EMPTY.equals(userBackUp.getRatingDes())) {
            textViewRating.setText(userBackUp.getRatingDes());
        }

        else {
            Toast.makeText(ProfileActivity.this, "Please fill you personal data",
                    Toast.LENGTH_LONG).show();
        }

        if(userBackUp.getNickname() != null && userBackUp.getNickname().equals("Tutor")){
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


    private void isTutor() {
        if (((CheckBox) tutorcheck).isChecked()) {


            userBackUp.setNickname("Tutor");
            userBackUp.updateUser(userBackUp);
            textViewNickname.setText(userBackUp.getNickname());

            Toast.makeText(ProfileActivity.this, "You are a tutor!",
                    Toast.LENGTH_LONG).show();
        }
        else {

            userBackUp.setNickname("Student");
            userBackUp.updateUser(userBackUp);
            textViewNickname.setText(userBackUp.getNickname());


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


                userBackUp.setStudies(editTextStudies.getText().toString());
                userBackUp.setSubject(editTextSubject.getText().toString());
                userBackUp.setPlan(planfinal);
                userBackUp.setChanged(true);

                try {
                    updateUserOnServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                userBackUp.updateUser(userBackUp);
                textViewStudies.setText(userBackUp.getStudies());
                textViewSubject.setText(userBackUp.getSubject());
                textViewPlan.setText(userBackUp.getPlan());


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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode != 0) {
            Uri mImageUri = data.getData();
            currentuser.setImageURL(mImageUri.toString());
            try {
                Image = Media.getBitmap(this.getContentResolver(), mImageUri);
                if (getOrientation(getApplicationContext(), mImageUri) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(getOrientation(getApplicationContext(), mImageUri));
                    if (rotateImage != null)
                        rotateImage.recycle();
                    rotateImage = Bitmap.createBitmap(Image, 0, 0, Image.getWidth(), Image.getHeight(), matrix,true);
                    imageView.setImageBitmap(rotateImage);
                } else
                    imageView.setImageBitmap(Image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
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
        payload.put("imageURL", currentuser.getImageURL());
        auth.setPayload(payload);
        Intent mainActivity = null;
        if(intentAction == "register") {
            mainActivity = new Intent(this, MainActivity.class);
            mainActivity.putExtra(USER_DETAIL_KEY, currentuser);
            //startActivity(mainActivity);
        }
        ServerUserTask serverUserTask = new ServerUserTask(null, this, auth, currentuser, mainActivity, StatusUserFlag.SERVER_STATUS_UPDATE_USER);
        serverUserTask.execute();
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