package com.example.lkondilidis.smartlearn.adapters;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

public class ExampleAdapter extends CursorAdapter {

    private TextView text;
    private Context context;
    private List<User> users;
    private SearchView searchView;
    private User currentuser;
    private RecyclerView recyclerView;

    public ExampleAdapter(Context context, Cursor cursor, List<User> users, SearchView searchView, User currentuser, RecyclerView recyclerView)
    {
        super(context,cursor,false);
        this.context = context;
        this.users = users;
        this.searchView = searchView;
        this.currentuser = currentuser;
        this.recyclerView = recyclerView;
    }


    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        //String[] item = items.get(cursor.getPosition());
        final String item = cursor.getString(cursor.getColumnIndexOrThrow("text"));
        final int itemId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("number")));
        text=(TextView)view.findViewById(R.id.item);
        text.setText(item);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lectureName = item;
                int lectureNumber = itemId;

                String userName = item;
                int userNumber = itemId;


                List<User> tutorListSearched = new ArrayList<>();

                for (User user : users) {
                    for(Lecture l: user.getLectures()) {
                        if (l.getId() == lectureNumber) {
                            tutorListSearched.add(user);
                        }
                    }
                }

                for (User user : users) {
                    if (user.getId() == itemId && !tutorListSearched.contains(user)) {
                        tutorListSearched.add(user);
                    }
                }

                SearchAdapter searchAdapter = new SearchAdapter(context, tutorListSearched, currentuser);
                recyclerView.setAdapter(searchAdapter);

                searchView.setQuery(lectureName, false);
                searchView.clearFocus();
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.item, parent, false);
        parent.setBackgroundColor(context.getResources().getColor(R.color.primary));

        return view;
    }
}