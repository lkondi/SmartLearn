package com.example.lkondilidis.smartlearn.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.helpers.DatabaseHelper;

public class PaymentActivity extends AppCompatActivity {

    private final AppCompatActivity activity = PaymentActivity.this;

    DatabaseHelper dataBaseHelper;

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser;

    public static final String USER_DETAIL_KEY = "currentuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        dataBaseHelper = new DatabaseHelper(activity);

        intentAction = intent.getAction();

        //Drawer
        drawerLayout = findViewById(R.id.drawer_profile);
        NavigationView navigationView = findViewById(R.id.navigation_view_profile);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }

}
