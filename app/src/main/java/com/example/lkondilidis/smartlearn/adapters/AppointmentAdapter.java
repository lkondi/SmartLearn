package com.example.lkondilidis.smartlearn.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Appointment;

public class AppointmentAdapter extends ArrayAdapter {

    private AppCompatActivity activity;
    private List<Appointment> arrayList;

    public AppointmentAdapter(AppCompatActivity context, int resource, List<Appointment> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.arrayList = objects;
    }

    @Override
    public Appointment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.apppointment_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.author_name.setText(getItem(position).getAppointmentAuthor().getName());
        holder.app_subject.setText(getItem(position).getSubject().getName());
        holder.app_date.setText(getItem(position).getDate());

        if(getItem(position).getAccepted()) {
            holder.confirmed.setVisibility(View.VISIBLE);
            holder.pending.setVisibility(View.GONE);
        } else {
            holder.confirmed.setVisibility(View.GONE);
            holder.pending.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        private ImageView confirmed, pending;
        private TextView author_name, app_subject, app_date;

        public ViewHolder(View view) {
            confirmed = (ImageView) view.findViewById(R.id.image_accepted);
            pending = (ImageView) view.findViewById(R.id.image_pending);
            author_name = (TextView) view.findViewById(R.id.author_name);
            app_date = (TextView) view.findViewById(R.id.app_subject);
            app_subject = (TextView) view.findViewById(R.id.app_date);
        }
    }
}
