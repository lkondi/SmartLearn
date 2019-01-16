package com.example.lkondilidis.smartlearn.activities;

import android.content.Context;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.R;

public class DetailActivity extends AppCompatActivity {
    private final AppCompatActivity activity = DetailActivity.this;
    private ImageView ivUserImage;
    private TextView userName, usernameHeader;
    private TextView userEmail;
    private TextView userNickname;
    private TextView userStudies;
    private TextView userSubject;
    private TextView userPlan;
    private TextView userRating;
    private User user;
    private static String STRING_EMPTY = "";
    int idFromIntent = 0;


    User selecteduser;
    private User currentuser;
    public static final String USER_DETAIL_KEY = "currentuser";

    private DrawerLayout drawerLayout;

    private RatingBar rating;

    private Button sendButton;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.context = this;

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

        // Fetch views
        ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        userName = (TextView) findViewById(R.id.userName);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userNickname = (TextView) findViewById(R.id.userNickname);
        userStudies = (TextView) findViewById(R.id.userStudies);
        userSubject = (TextView) findViewById(R.id.userSubject);
        userPlan = (TextView) findViewById(R.id.userPlan);
        userRating = (TextView) findViewById(R.id.userRating);
        rating = (RatingBar) findViewById(R.id.ratingProvider);
        sendButton = (Button) findViewById(R.id.button_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", selecteduser.getFirebaseId());
                intent.putExtra(USER_DETAIL_KEY, currentuser);
                context.startActivity(intent);
            }
        });


        loadUser(selecteduser);

    }

    private void loadUser(User user) {

        rating.setNumStars(user.getRatingStars());

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
        if (!STRING_EMPTY.equals(user.getRatingDes())) {
            userRating.setText(user.getRatingDes());
        }
        else {
            Toast.makeText(DetailActivity.this, "Please fill you personal data",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        usernameHeader = (TextView) findViewById(R.id.usernameHeader);
        usernameHeader.setText(currentuser.getName());

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_share:
                setShareIntent();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        ImageView ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        final TextView userName = (TextView)findViewById(R.id.userName);
        final TextView userEmail = (TextView)findViewById(R.id.userEmail);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivUserImage);
        // Construct a ShareIntent with link to image
        Intent shareIntent = new Intent();
        // Construct a ShareIntent with link to image
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, (String)userName.getText());
        shareIntent.putExtra(Intent.EXTRA_TEXT, (String)userEmail.getText());
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        // Launch share menu
        startActivity(Intent.createChooser(shareIntent, "Share Image"));

    }

    // Returns the URI path to the Bitmap displayed in cover imageview
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
