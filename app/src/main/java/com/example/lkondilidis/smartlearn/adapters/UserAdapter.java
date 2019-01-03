package com.example.lkondilidis.smartlearn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.model.User;

public class UserAdapter extends ArrayAdapter<User> {

    private static class ViewHolder {
        public ImageView ivUser;
        public TextView userName;
        public TextView userInfo;
    }

    public UserAdapter(Context context, ArrayList<User> tutors) {
        super(context, 0, tutors);
    }

    // Translates a particular `User` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_list_view_detail, parent, false);
            viewHolder.ivUser = (ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.userName = (TextView)convertView.findViewById(R.id.textView_name);
            viewHolder.userInfo = (TextView)convertView.findViewById(R.id.textView_des);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.userName.setText(user.getName());
        viewHolder.userInfo.setText(user.getSubject());

        // Return the completed view to render on screen
        return convertView;
    }
    
}

