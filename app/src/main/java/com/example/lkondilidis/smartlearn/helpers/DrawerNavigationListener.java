package com.example.lkondilidis.smartlearn.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.Appointment_List_Activity;
import com.example.lkondilidis.smartlearn.activities.LectureActivity;
import com.example.lkondilidis.smartlearn.activities.LoginActivity;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.activities.ProfileActivity;
import com.example.lkondilidis.smartlearn.model.User;

public class DrawerNavigationListener implements NavigationView.OnNavigationItemSelectedListener{

    private Context context;
    public static final String USER_DETAIL_KEY = "currentuser";


    public DrawerNavigationListener(Context context){
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent = ((Activity)context).getIntent();
        User currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);

        switch (item.getItemId()){
            case R.id.drawer_home:
                if ( context instanceof MainActivity ) {

                } else {
                    Intent mainactivity = new Intent(context, MainActivity.class);
                    mainactivity.putExtra(USER_DETAIL_KEY, currentuser);
                    context.startActivity(mainactivity);
                }
                break;
            case R.id.profile:
                if ( context instanceof ProfileActivity ) {

                } else {
                    Intent profileactivity = new Intent(context, ProfileActivity.class);
                    profileactivity.putExtra(USER_DETAIL_KEY, currentuser);
                    context.startActivity(profileactivity);
                }
                break;
            case R.id.appointments:
                if ( context instanceof Appointment_List_Activity ) {

                } else {
                    Intent appactivity = new Intent(context, Appointment_List_Activity.class);
                    appactivity.putExtra(USER_DETAIL_KEY, currentuser);
                    context.startActivity(appactivity);
                }
                break;
            case R.id.lecture:
                if ( context instanceof LectureActivity ) {

                } else {
                    Intent lectureactivity = new Intent(context, LectureActivity.class);
                    lectureactivity.putExtra(USER_DETAIL_KEY, currentuser);
                    context.startActivity(lectureactivity);
                }
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
