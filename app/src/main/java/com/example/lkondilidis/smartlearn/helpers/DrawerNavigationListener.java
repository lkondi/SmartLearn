package com.example.lkondilidis.smartlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.LoginActivity;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.activities.ProfileActivity;

public class DrawerNavigationListener implements NavigationView.OnNavigationItemSelectedListener{

    private Context context;

    public DrawerNavigationListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = ((Activity)context).getIntent();
        String email = intent.getStringExtra("EMAIL");

        switch (item.getItemId()){
            case R.id.drawer_home:
                Intent mainactivity = new Intent(context, MainActivity.class);
                mainactivity.putExtra("EMAIL", email);
                context.startActivity(mainactivity);
                break;
            case R.id.profile:
                Intent profileactivity = new Intent(context, ProfileActivity.class);
                profileactivity.putExtra("EMAIL", email);
                context.startActivity(profileactivity);
                break;
            case R.id.logout:
                Toast.makeText(context, "You are logged out!",
                        Toast.LENGTH_LONG).show();
                Intent loginactivity = new Intent(context, LoginActivity.class);
                context.startActivity(loginactivity);
                break;
        }
        return false;
    }
}
