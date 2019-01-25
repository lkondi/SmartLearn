package com.example.lkondilidis.smartlearn.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.AppointmentActivity;
import com.example.lkondilidis.smartlearn.activities.DetailActivity;
import com.example.lkondilidis.smartlearn.activities.RatingActivity;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.List;

class SearchLectureViewHolder extends RecyclerView.ViewHolder{

    TextView name, id;
    CardView parentLayout;

    public SearchLectureViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_lectureName);
        id = itemView.findViewById(R.id.textView_lectureId);
        parentLayout = itemView.findViewById(R.id.parentLayout_lecture);
    }
}

public class SearchLectureAdapter extends RecyclerView.Adapter<SearchLectureViewHolder>{

    private Context context;
    private List<Lecture> lectures;

    public SearchLectureAdapter(Context context, List<Lecture> lectures){
        this.context = context;
        this.lectures = lectures;
    }

    @NonNull
    @Override
    public SearchLectureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.lecture_layout_item, viewGroup, false);
        return new SearchLectureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchLectureViewHolder searchViewHolder, final int i) {

        searchViewHolder.name.setText(lectures.get(i).getName());
        searchViewHolder.id.setText(""+lectures.get(i).getId());

/*        searchViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDetail = new Intent(context, DetailActivity.class);
                showDetail.putExtra(SELECTED_USER_DETAIL_KEY, users.get(i));
                showDetail.putExtra(USER_DETAIL_KEY, currentuser);
                context.startActivity(showDetail);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }
}
