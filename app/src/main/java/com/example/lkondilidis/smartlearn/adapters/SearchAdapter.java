package com.example.lkondilidis.smartlearn.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.List;

class SearchViewHolder extends RecyclerView.ViewHolder{

    TextView name, email, descrition;

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView_username);
        email = itemView.findViewById(R.id.textView_email);
        descrition = itemView.findViewById(R.id.textView_description);
    }
}

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>{

    private Context context;
    private List<User> users;

    public SearchAdapter(Context context, List<User> users){
        this.context = context;
        this. users = users;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.layout_item, viewGroup, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.name.setText(users.get(i).getName());
        searchViewHolder.email.setText(users.get(i).getEmail());
        searchViewHolder.descrition.setText(users.get(i).getPrivateInfo());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
