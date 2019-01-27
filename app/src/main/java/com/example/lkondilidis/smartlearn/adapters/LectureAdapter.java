package com.example.lkondilidis.smartlearn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Lecture;

import java.util.ArrayList;
import java.util.List;


public class LectureAdapter extends ArrayAdapter {
    private List<Lecture> lectureList = new ArrayList<>();
    private List<Boolean> checkboxes = new ArrayList<>();
    private boolean checkboxesGone;

    public void clearLists() {
        lectureList.clear();
        checkboxes.clear();
    }

    public boolean checkBoxesClicked() {
        for(Boolean b: checkboxes){
            if(b){
                return b;
            }
        }
        return false;
    }

    static class ItemViewHolder {
        TextView id;
        TextView lecture;
        CheckBox checkBox;
    }

    public LectureAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    //@Override
    public void add(Lecture lecture) {
        lectureList.add(lecture);
        checkboxes.add(false);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.lecture_item, parent, false);

            viewHolder = new ItemViewHolder();
            viewHolder.id = (TextView) row.findViewById(R.id.id);
            viewHolder.lecture = (TextView) row.findViewById(R.id.lecture);
            viewHolder.checkBox = (CheckBox) row.findViewById(R.id.checkBox_lecture);

            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder)row.getTag();
        }
        Lecture lecture = getItem(position);
        viewHolder.id.setText(""+lecture.getId());
        viewHolder.lecture.setText(lecture.getName());

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkboxes.set(position, true);
                System.out.println("checked");
            }
        });

        if(checkboxesGone){
            viewHolder.checkBox.setVisibility(View.GONE);
        }
        return row;
    }

    public List<Boolean> getCheckboxes(){
        return this.checkboxes;
    }

    public void setCheckboxGone(){
        this.checkboxesGone = true;
    }
}