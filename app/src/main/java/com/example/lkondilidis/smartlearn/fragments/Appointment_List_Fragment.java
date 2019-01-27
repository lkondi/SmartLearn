package com.example.lkondilidis.smartlearn.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ListView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.adapters.AppointmentAdapter;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;

public class Appointment_List_Fragment extends Fragment {

    User currentuser;
    MainActivity activity;

    public static final String USER_DETAIL_KEY = "currentuser";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_list, container, false);
        activity = (MainActivity) getActivity();
        currentuser = activity.getCurrentuser();


        //listview
        ListView myListViewappoint = (ListView) view.findViewById(R.id.listViewMyAppointments);
        for(Appointment a:currentuser.getYourAppointments()){
            a.setAppointmentAuthor(currentuser);
        }
        ArrayList<Appointment> myAppointmentlist = new ArrayList<>(currentuser.getYourAppointments());
        AppointmentAdapter myAppAdapter = new AppointmentAdapter(getContext(), R.layout.activity_appointment_list, currentuser);
        for(Appointment a: myAppointmentlist){
            myAppAdapter.add(a);
        }
        myListViewappoint.setAdapter(myAppAdapter);



        //listview
        ListView userListViewappoint = (ListView) view.findViewById(R.id.listViewUserAppointments);
        for(Appointment a:currentuser.getUserAppointments()){
            a.setAppointmentUser(currentuser);
        }
        ArrayList<Appointment> userAppointmentlist = new ArrayList<>(currentuser.getUserAppointments());
        AppointmentAdapter userAppAdapter = new AppointmentAdapter(getContext(), R.layout.apppointment_list_item, currentuser);
        for(Appointment a: userAppointmentlist){
            userAppAdapter.add(a);
        }
        userAppAdapter.setOnClick();
        userListViewappoint.setAdapter(userAppAdapter);

        return view;
    }

}

