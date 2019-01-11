package com.example.lkondilidis.smartlearn.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.DetailActivity;
import com.example.lkondilidis.smartlearn.activities.LoginActivity;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.activities.RatingActivity;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{

    TextView name, subject, plan;
    CardView parentLayout;
    ImageButton imagerating;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_name);
        subject = itemView.findViewById(R.id.textView_subject);
        plan = itemView.findViewById(R.id.textView_plan);
        imagerating = itemView.findViewById(R.id.imagerating);
        parentLayout = itemView.findViewById(R.id.parentLayout);
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";
    public static final String USER_DETAIL_KEY = "currentuser";
    private Context context;
    private List<User> users;
    private User currentuser;

    public SearchAdapter(Context context, List<User> users, User currentuser){
        this.context = context;
        this.users = users;
        this.currentuser = currentuser;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.main_layout_item, viewGroup, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, final int i) {

        searchViewHolder.name.setText(users.get(i).getName());
        searchViewHolder.subject.setText(users.get(i).getSubject());
        searchViewHolder.plan.setText(users.get(i).getPlan());
//        searchViewHolder.descrition.setText(users.get(i).getPrivateInfo());

        searchViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDetail = new Intent(context, DetailActivity.class);
                showDetail.putExtra(SELECTED_USER_DETAIL_KEY, users.get(i));
                showDetail.putExtra(USER_DETAIL_KEY, currentuser);
                context.startActivity(showDetail);
            }
        });

        searchViewHolder.imagerating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showRating = new Intent(context, RatingActivity.class);
                showRating.putExtra(SELECTED_USER_DETAIL_KEY, users.get(i));
                showRating.putExtra(USER_DETAIL_KEY, currentuser);
                context.startActivity(showRating);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
