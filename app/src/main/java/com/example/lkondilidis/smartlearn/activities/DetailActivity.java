package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
    int idFromIntent = 0;

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

        User selecteduser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        //idFromIntent = getIntent().getIntExtra("SELECTEDUSER", idFromIntent);
        dataBaseHelper = new SQLiteDBHelper(activity);

        //selected User
        //User selecteduser = dataBaseHelper.getUser(idFromIntent);
        loadUser(selecteduser);

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
        int id = item.getItemId();

        if (id == R.id.action_share) {
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
