package com.example.lkondilidis.smartlearn.adapters;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Rating;
import com.example.lkondilidis.smartlearn.model.User;


public class RatingAdapter extends ArrayAdapter {

    private AppCompatActivity activity;
    private List<Rating> arrayList;
    private RatingBar ratingBar;
    private User user;

    public RatingAdapter(AppCompatActivity context, int resource, List<Rating> objects, RatingBar rating) {
        super(context, resource, objects);
        this.activity = context;
        this.arrayList = objects;
        this.ratingBar = rating;
    }

    @Override
    public Rating getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.rating_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            //holder.ratingBar.getTag(position);
        }

        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(getItem(position).getStars());
        holder.description.setText(getItem(position).getDescription());

        return convertView;
    }

    public void setDataSet(Set<Rating> userRatings) {
        for(Rating r: userRatings){
            if(ratingsDoNotContain(r)){
                arrayList.add(r);
            }
        }
        this.ratingBar.setRating(calculateRatingStars(arrayList));
        this.notifyDataSetChanged();
    }

    private boolean ratingsDoNotContain(Rating r) {
        for(Rating rating: this.arrayList){
            if(r.getId() == rating.getId()){
                return false;
            }
        }
        return true;
    }

    public int calculateRatingStars(List<Rating> ratings) {

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


    private static class ViewHolder {
        private RatingBar ratingBar;
        private TextView description;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.rate_img);
            description = (TextView) view.findViewById(R.id.text);
        }
    }
}