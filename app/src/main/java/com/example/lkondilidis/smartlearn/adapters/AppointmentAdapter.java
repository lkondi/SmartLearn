package com.example.lkondilidis.smartlearn.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.PopUpAppointmentActivity;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.User;

public class AppointmentAdapter extends ArrayAdapter {
    private List<Appointment> appointmentList = new ArrayList<>();
    private boolean onClick;
    private User currentuser;
    public static final String USER_DETAIL_KEY = "currentuser";

    public void clearLists() {
        appointmentList.clear();
    }

    public void setOnClick() {
        onClick = true;
    }


    static class ItemViewHolder {
        TextView author_name, app_subject, app_date;
        ImageView confirmed, pending;
    }

    public AppointmentAdapter(Context context, int textViewResourceId, User currentuser) {
        super(context, textViewResourceId);
        this.currentuser = currentuser;
    }


    //@Override
    public void add(Appointment appointment) {
        appointmentList.add(appointment);
        super.add(appointment);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.apppointment_list_item, parent, false);

            viewHolder = new ItemViewHolder();
            viewHolder.author_name = (TextView) row.findViewById(R.id.author_name);
            viewHolder.app_subject = (TextView) row.findViewById(R.id.app_subject);
            viewHolder.app_date = (TextView) row.findViewById(R.id.app_date);
            viewHolder.pending = (ImageView) row.findViewById(R.id.pending_image);
            viewHolder.confirmed = (ImageView) row.findViewById(R.id.check_image);

            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        final Appointment appointment = getItem(position);
        viewHolder.author_name.setText(appointment.getAppointmentAuthor().getName());
        viewHolder.author_name.setTextColor(getContext().getResources().getColor(R.color.white));
//        viewHolder.app_subject.setText(appointment.getSubject().getName());
        viewHolder.app_date.setText(appointment.getDate());

        if(appointment.isAccepted()){
            viewHolder.confirmed.setVisibility(View.VISIBLE);
            viewHolder.pending.setVisibility(View.GONE);
        }else {
            viewHolder.confirmed.setVisibility(View.GONE);
            viewHolder.pending.setVisibility(View.VISIBLE);
        }

        if(onClick) {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PopUpAppointmentActivity.class);
                    intent.putExtra(getContext().getResources().getString(R.string.appointment_flag), appointment);
                    intent.putExtra(USER_DETAIL_KEY, currentuser);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getContext().startActivity(intent);
                }
            });
        }

        return row;
    }

}