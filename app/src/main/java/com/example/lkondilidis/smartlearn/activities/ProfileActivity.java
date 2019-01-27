package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;;
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
import android.widget.*;
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
import com.example.lkondilidis.smartlearn.adapters.RatingAdapter;
import com.example.lkondilidis.smartlearn.model.Rating;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Url;

//TODO: edit profile image

public class ProfileActivity extends AppCompatActivity{
    private final AppCompatActivity activity = ProfileActivity.this;

    TextView textView_username, textView_useremail, textView_userrole, usernameHeader, linklectures, linkappointments, textplan;
    RatingBar ratingProvider;
    Button btnTutor;
    ImageView image;
    ImageView imageView;
    LinearLayout linearLayoutTutor;
    ListView linkratings;


    int PICK_IMAGE_REQUEST = 111;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    Button upload;

    private static String STRING_EMPTY = "";

    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;
    private static final int GALLERY = 1;

    ArrayList<String> plan;

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser;
    RatingAdapter ratingadapter;

    public static final String USER_DETAIL_KEY = "currentuser";

    private Boolean changed;
    private User userBackUp;
    ArrayList<Rating> ratingarrayList;

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

        image = (ImageView) findViewById(R.id.profile);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });

        upload = (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //converting image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
                auth.setHttpMethod("POST");
                auth.setUrlPath("update/"+currentuser.getId());
                JSONObject payload = new JSONObject();
                try {
                    payload.put("image", imageString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                auth.setPayload(payload);
                ServerUserTask serverUserTask = new ServerUserTask(null, getApplicationContext(), auth, currentuser, null, StatusUserFlag.SERVER_STATUS_UPDATE_USER);
                serverUserTask.execute();
            }
        });


        linearLayoutTutor = (LinearLayout) findViewById(R.id.linearLayoutTutor);
        textView_username= (TextView) findViewById(R.id.textView_username);
        textView_useremail = (TextView) findViewById(R.id.textView_useremail);
        textView_userrole = (TextView) findViewById(R.id.textView_userrole);
        linklectures = (TextView) findViewById(R.id.textViewLinkLectures);
        linkappointments = (TextView) findViewById(R.id.textViewLinkAppointments);
        textplan = (TextView) findViewById(R.id.subjecttext);
        ratingProvider = (RatingBar) findViewById(R.id.ratingProvider);
        btnTutor = (Button) findViewById(R.id.btnTutor);
        imageView = (CircleImageView) findViewById(R.id.profile);

        //ratings
        ratingarrayList = new ArrayList<>(currentuser.getUserRatings());
        linkratings = (ListView) findViewById(R.id.listViewLinkRatings);
        ratingProvider.setRating(currentuser.getRatingStars());
        ratingadapter = new RatingAdapter(this, R.layout.rating_item_layout, ratingarrayList, ratingProvider);
        linkratings.setAdapter(ratingadapter);


        linklectures.setText("Add or change your subjects");
        linklectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserLectureActivity.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setAction("profile");
                startActivity(intent);
            }
        });


        btnTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileActivity.this, ProfileActivityTutor.class);
                intent.putExtra(USER_DETAIL_KEY, userBackUp);
                startActivity(intent);
            }
        });


        //set Values
        textView_useremail.setText(userBackUp.getEmail());
        textView_username.setText(userBackUp.getName());

        if(currentuser.getNickname().equals("Tutor")){
            linearLayoutTutor.setVisibility(View.VISIBLE);
            textView_userrole.setText("Tutor");
            ratingProvider.setNumStars(userBackUp.getRatingStars());
           // textstudies.setText(currentuser.getSubject());
           // textplan.setText(currentuser.getPlan());

        }else{
            linearLayoutTutor.setVisibility(View.GONE);
            textView_userrole.setText("Student");
            ratingProvider.setVisibility(View.GONE);
        }


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
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



    private void updateUserPictureOnServer(Bitmap image) throws JSONException {
        ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        auth.setHttpMethod("POST");
        auth.setUrlPath("update/"+currentuser.getId());
        JSONObject payload = new JSONObject();
        payload.put("image", image);
        auth.setPayload(payload);
        ServerUserTask serverUserTask = new ServerUserTask(null, this, auth, currentuser, null, StatusUserFlag.SERVER_STATUS_UPDATE_USER);
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