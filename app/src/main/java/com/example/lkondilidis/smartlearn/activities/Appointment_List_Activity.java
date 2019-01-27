package com.example.lkondilidis.smartlearn.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;

public class Appointment_List_Activity extends AppCompatActivity {
    private final AppCompatActivity activity = Appointment_List_Activity.this;

    private static String STRING_EMPTY = "";

    private DrawerLayout drawerLayout;

    private String intentAction;
    private User currentuser, selecteduser;
    private ImageView imageconfirmed, imagepending;


    private TextView appuser, appsubject, appdate, usernameHeader;

    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        Intent intent = getIntent();
        currentuser = (User) intent.getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        selecteduser = (User) intent.getSerializableExtra(MainActivity.SELECTED_USER_DETAIL_KEY);


        intentAction = intent.getAction();

        //Drawer
        drawerLayout = findViewById(R.id.drawer_profile);
        NavigationView navigationView = findViewById(R.id.navigation_view_profile);
        navigationView.setNavigationItemSelectedListener(new DrawerNavigationListener(this));

        //Toolbar
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //initiate Views
        appuser = (TextView) findViewById(R.id.author_name);
        appdate = (TextView) findViewById(R.id.app_date);
        appsubject = (TextView) findViewById(R.id.app_subject);
        imageconfirmed = (ImageView) findViewById(R.id.image_accepted);
        imagepending = (ImageView) findViewById(R.id.image_pending);

    }

    class ListAdapter extends ArrayAdapter {
        private ArrayList<Appointment> appointmentList = new ArrayList();
        private Context context;
        Appointment appointment = new Appointment();

        class ItemViewHolder {
            TextView author_name, app_subject, app_date;
            ImageView confirmed, pending;
        }

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.context = context;
        }

        //@Override
        public void add(Appointment object) {
            appointmentList.add(object);
            super.add(object);
        }

        @Override
        public int getCount() {
            return this.appointmentList.size();
        }

        @Override
        public Appointment getItem(int index) {
            return this.appointmentList.get(index);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            Appointment_List_Activity.ListAdapter.ItemViewHolder viewHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.apppointment_list_item, parent, false);
                viewHolder = new Appointment_List_Activity.ListAdapter.ItemViewHolder();
                viewHolder.author_name = (TextView) row.findViewById(R.id.author_name);
                viewHolder.app_subject = (TextView) row.findViewById(R.id.app_subject);
                viewHolder.app_date = (TextView) row.findViewById(R.id.app_date);
                viewHolder.confirmed = (ImageView) row.findViewById(R.id.image_accepted);
                viewHolder.pending = (ImageView) row.findViewById(R.id.image_pending);
                row.setTag(viewHolder);
            } else {
                viewHolder = (Appointment_List_Activity.ListAdapter.ItemViewHolder)row.getTag();
            }

            final Appointment appointment = getItem(position);
            viewHolder.author_name.setText(appointment.getAppointmentAuthor().getName());
            viewHolder.app_subject.setText(appointment.getDate());
            viewHolder.app_date.setText(appointment.getSubject().getName());


            if(appointment.getAccepted()) {
                viewHolder.confirmed.setVisibility(View.VISIBLE);
                viewHolder.confirmed.setVisibility(View.GONE);

            }else  {
                viewHolder.confirmed.setVisibility(View.GONE);
                viewHolder.confirmed.setVisibility(View.VISIBLE);
            }

            return row;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        usernameHeader = (TextView) findViewById(R.id.usernameHeader);
        usernameHeader.setText(currentuser.getName());
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawerLayout.openDrawer(GravityCompat.START);
                    return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
