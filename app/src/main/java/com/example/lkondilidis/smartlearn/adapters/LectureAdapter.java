package com.example.lkondilidis.smartlearn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class LectureAdapter extends ArrayAdapter {
    private List lectureList = new ArrayList();

    static class ItemViewHolder {
        TextView id;
        TextView lecture;
    }

    public LectureAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(String[] object) {
        lectureList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.lectureList.size();
    }

    @Override
    public String[] getItem(int index) {
        return this.lectureList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.lecture_item_layout, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.id = (TextView) row.findViewById(R.id.id);
            viewHolder.lecture = (TextView) row.findViewById(R.id.lecture);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        String[] stat = getItem(position);
        viewHolder.id.setText(stat[0]);
        viewHolder.lecture.setText(stat[1]);
        return row;
    }
}