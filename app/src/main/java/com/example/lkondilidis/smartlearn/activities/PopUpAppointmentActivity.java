package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lkondilidis.smartlearn.Interfaces.StatusAppointmentFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.LectureAdapter;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerAppointmentTask;

import java.util.ArrayList;

public class PopUpAppointmentActivity extends AppCompatActivity {

    private User currentuser;
    public static final String USER_DETAIL_KEY = "currentuser";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_appointment);

        currentuser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);
        final Appointment appointment = (Appointment) getIntent().getSerializableExtra(getString(R.string.appointment_flag));

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));*/

        Button btn_add = (Button) findViewById(R.id.button_accept);
        btn_add.setText("Accept");
        btn_add.setTextColor(Color.WHITE);
        Button btn_cancel = (Button) findViewById(R.id.button_decline);
        btn_cancel.setText("Decline");
        btn_cancel.setTextColor(Color.WHITE);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!appointment.isAccepted()) {
                    Intent intent = new Intent(PopUpAppointmentActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setAction(getString(R.string.appointment_fragment));
                    ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
                    auth.setHttpMethod("POST");
                    auth.setUrlPath("appointment/update/" + currentuser.getId());
                    appointment.setAccepted(true);
                    auth.setPayload(appointment.convertToJSON(null));
                    ServerAppointmentTask serverAppointmentTask = new ServerAppointmentTask(null, currentuser, PopUpAppointmentActivity.this, intent, auth, StatusAppointmentFlag.STATUS_APPOINTMENT_UPDATE_FLAG);
                    serverAppointmentTask.execute();
                } else {
                    finish();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUpAppointmentActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setAction(getString(R.string.appointment_fragment));
                ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
                auth.setHttpMethod("POST");
                auth.setUrlPath("appointment/remove/"+currentuser.getId());
                auth.setPayload(appointment.convertToJSON(null));
                ServerAppointmentTask serverAppointmentTask = new ServerAppointmentTask(null, currentuser, PopUpAppointmentActivity.this, intent, auth, StatusAppointmentFlag.STATUS_APPOINTMENT_UPDATE_FLAG);
                serverAppointmentTask.execute();
                finish();
            }
        });
    }
}
