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

import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

class SearchLectureViewHolder extends RecyclerView.ViewHolder{

    TextView name, id;
    CardView parentLayout;
    ImageButton imageButtonTrash;

    public SearchLectureViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_lectureName);
        id = itemView.findViewById(R.id.textView_lectureId);
        parentLayout = itemView.findViewById(R.id.parentLayout_lecture);
        imageButtonTrash = itemView.findViewById(R.id.imageButton_trash);

    }
}

public class SearchLectureAdapter extends RecyclerView.Adapter<SearchLectureViewHolder>{

    private Context context;
    private List<Lecture> lectures;
    private User currentuser;
    private SearchLectureAdapter searchLectureAdapter;

    public SearchLectureAdapter(Context context, List<Lecture> lectures, User currentuser){
        this.context = context;
        this.lectures = lectures;
        this.currentuser = currentuser;
        this.searchLectureAdapter = this;
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

        searchViewHolder.imageButtonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lecture lecture = lectures.get(i);
                lectures.remove(i);
                currentuser.removeLecture(lecture.getId());

                ApiAuthenticationClient auth = new ApiAuthenticationClient(context.getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
                auth.setHttpMethod("POST");
                auth.setUrlPath("update/"+currentuser.getId());
                JSONObject payload = new JSONObject();
                try {
                    payload.put("nickname", currentuser.getNickname());
                    JSONArray jsonArray = new JSONArray();
                    for(Lecture l: currentuser.getLectures()){
                        jsonArray.put(l.convertToJSON());
                    }
                    payload.put("lectures", jsonArray);
                    payload.put("studies", currentuser.getStudies());
                    payload.put("plan", currentuser.getPlan());
                } catch (Exception e){

                }
                auth.setPayload(payload);
                ServerUserTask serverUserTask = new ServerUserTask(null, context, auth, currentuser, null, StatusUserFlag.SERVER_STATUS_UPDATE_USER);
                serverUserTask.setAdapter(searchLectureAdapter);
                serverUserTask.execute();
            }
        });

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
