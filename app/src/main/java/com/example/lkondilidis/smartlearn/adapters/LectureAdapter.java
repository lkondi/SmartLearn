package com.example.lkondilidis.smartlearn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Lecture;

import org.xml.sax.ext.LexicalHandler;

import java.util.ArrayList;
import java.util.List;


public class LectureAdapter extends ArrayAdapter {
    private List<Lecture> lectureList = new ArrayList();

    static class ItemViewHolder {
        TextView id;
        TextView lecture;
    }

    public LectureAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    //@Override
    public void add(Lecture lecture) {
        lectureList.add(lecture);
        super.add(lecture);
    }

    @Override
    public int getCount() {
        return this.lectureList.size();
    }

    @Override
    public Lecture getItem(int index) {
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
        Lecture lecture = getItem(position);
        viewHolder.id.setText(""+lecture.getId());
        viewHolder.lecture.setText(lecture.getName());
        return row;
    }
}