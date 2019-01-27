package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
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
import com.example.lkondilidis.smartlearn.model.Lecture;
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

    TextView textView_username, textView_useremail, textView_userrole, usernameHeader, textstudies, textlectures, textplan;
    RatingBar ratingProvider;
    Button btnTutor;
    ImageView profile;
    ImageView imageView;
    LinearLayout linearLayoutTutor;

    private static String STRING_EMPTY = "";

    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;
    private static final int GALLERY = 1;

    ArrayList<String> plan;

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser;

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

        linearLayoutTutor = (LinearLayout) findViewById(R.id.linearLayoutTutor);
        textView_username= (TextView) findViewById(R.id.textView_username);
        textView_useremail = (TextView) findViewById(R.id.textView_useremail);
        textView_userrole = (TextView) findViewById(R.id.textView_userrole);
        textstudies = (TextView) findViewById(R.id.textstudies);
        textlectures = (EditText) findViewById(R.id.studiesedit);
        textplan = (TextView) findViewById(R.id.subjecttext);
        ratingProvider = (RatingBar) findViewById(R.id.ratingProvider);
        btnTutor = (Button) findViewById(R.id.btnTutor);
        imageView = (CircleImageView) findViewById(R.id.profile);

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
            textstudies.setText(currentuser.getSubject());
            textplan.setText(currentuser.getPlan());

        }else{
            linearLayoutTutor.setVisibility(View.GONE);
            textView_userrole.setText("Student");
            ratingProvider.setVisibility(View.GONE);
        }


    }





    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode != 0) {
            Uri mImageUri = data.getData();
            currentuser.setImageURL(mImageUri.toString());
            try {
                Image = Media.getBitmap(this.getContentResolver(), mImageUri);
                updateUserPictureOnServer(Image);
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
            } catch (JSONException e) {
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
        payload.put("imageBitmap", image);
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