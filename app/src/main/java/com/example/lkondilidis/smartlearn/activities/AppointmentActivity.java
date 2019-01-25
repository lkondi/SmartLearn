package com.example.lkondilidis.smartlearn.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;

public class AppointmentActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressDialog progressDialog;
    private BroadcastReceiver receiver = null;

    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";
    User selecteduser;
    User currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showSpinner();

        selecteduser = (User) getIntent().getSerializableExtra(DetailActivity.SELECTED_USER_DETAIL_KEY);
        currentuser = (User) getIntent().getSerializableExtra(DetailActivity.USER_DETAIL_KEY);

        // Fragments
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


    }

    // Fragments
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppointmentFragment(), "Appointments");
        adapter.addFragment(new PaymentFragment(), "Home");
        viewPager.setAdapter(adapter);
    }

    // Fragments
    class ViewPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                AppointmentFragment tab1 = new AppointmentFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER_DETAIL_KEY, currentuser);
                bundle.putSerializable(SELECTED_USER_DETAIL_KEY, selecteduser);
                tab1.setArguments(bundle);
                return tab1;
            }else if(position == 1){
                PaymentFragment tab2 = new PaymentFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(USER_DETAIL_KEY, currentuser);
                bundle.putSerializable(SELECTED_USER_DETAIL_KEY, selecteduser);
                tab2.setArguments(bundle);
                return tab2;
            }
            return null;
        }


        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onDestroy() {
        //Service is stopped and Sinch stops running.

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    //show a loading spinner while the sinch client starts
    private void showSpinner() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean success = intent.getBooleanExtra("success", false);
                progressDialog.dismiss();
                if (!success) {
                    Toast.makeText(getApplicationContext(), "service failed to start", Toast.LENGTH_LONG).show();
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("com.example.lkondilidis.smartlearn.AppointmentActivity"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent mainactivity = new Intent(this, MainActivity.class);
                mainactivity.putExtra(USER_DETAIL_KEY, currentuser);
                mainactivity.putExtra(SELECTED_USER_DETAIL_KEY, selecteduser);
                startActivity(mainactivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}