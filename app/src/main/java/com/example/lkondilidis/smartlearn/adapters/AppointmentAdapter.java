package com.example.lkondilidis.smartlearn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Appointment;

public class AppointmentAdapter extends ArrayAdapter {
    private List<Appointment> appointmentList = new ArrayList<>();

    public void clearLists() {
        appointmentList.clear();
    }


    static class ItemViewHolder {
        TextView author_name, app_subject, app_date;
        ImageView confirmed, pending;
    }

    public AppointmentAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
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
            viewHolder.pending = (ImageView) row.findViewById(R.id.image_pending);
            viewHolder.confirmed = (ImageView) row.findViewById(R.id.image_accepted);

            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        Appointment appointment = getItem(position);
        viewHolder.author_name.setText(appointment.getAppointmentAuthor().getName());
        viewHolder.app_subject.setText(appointment.getSubject().getName());
        viewHolder.app_date.setText(appointment.getDate());

        if(appointment.getAccepted()){
            viewHolder.confirmed.setVisibility(View.VISIBLE);
            viewHolder.pending.setVisibility(View.GONE);
        }else {
            viewHolder.confirmed.setVisibility(View.GONE);
            viewHolder.pending.setVisibility(View.VISIBLE);
        }
        return row;
    }

}