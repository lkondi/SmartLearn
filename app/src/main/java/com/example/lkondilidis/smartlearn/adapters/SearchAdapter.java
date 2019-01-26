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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.HomeFragmentActivity;
import com.example.lkondilidis.smartlearn.activities.DetailActivity;
import com.example.lkondilidis.smartlearn.activities.RatingActivity;
import com.example.lkondilidis.smartlearn.model.Rating;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.ArrayList;
import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{

    TextView name, subject, plan;
    CardView parentLayout;
    RatingBar ratingBar;
    LinearLayout click;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_name);
        subject = itemView.findViewById(R.id.textView_subject);
        plan = itemView.findViewById(R.id.textView_plan);
        click = itemView.findViewById(R.id.clickable_layout);
        parentLayout = itemView.findViewById(R.id.parentLayout);
        ratingBar = itemView.findViewById(R.id.ratingProvider);
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
        ArrayList ratingarrayList = new ArrayList<>(users.get(i).getUserRatings());
        searchViewHolder.ratingBar.setNumStars(calculateRatingStars(ratingarrayList));

        searchViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showDetail = new Intent(context, DetailActivity.class);
                showDetail.putExtra(SELECTED_USER_DETAIL_KEY, users.get(i));
                showDetail.putExtra(USER_DETAIL_KEY, currentuser);
                context.startActivity(showDetail);
            }
        });

        searchViewHolder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookApp = new Intent(context, HomeFragmentActivity.class);
                bookApp.putExtra(SELECTED_USER_DETAIL_KEY, users.get(i));
                bookApp.putExtra(USER_DETAIL_KEY, currentuser);
                context.startActivity(bookApp);
            }
        });

    }

    public int calculateRatingStars(ArrayList<Rating> ratings) {

        int star = 0;
        int sum = 0;
        double median = 0.0;
        ArrayList<Integer> stars = new ArrayList<>();
        for (Rating rating : ratings) {
            stars.add(rating.getStars());
        }
        for (int i = 0; i < stars.size(); i++) {
            sum += stars.get(i);
        }
        if(stars.size()!= 0) {
            median = sum / stars.size();
            star = (int) median;
        }

        return star;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
