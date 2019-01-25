package com.example.lkondilidis.smartlearn.adapters;

import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Lecture;

public class ExampleAdapter extends CursorAdapter {

    private TextView text;
    private Context context;

    public ExampleAdapter(Context context,Cursor cursor)
    {
        super(context,cursor,false);
        this.context = context;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //String[] item = items.get(cursor.getPosition());
        String item = cursor.getString(cursor.getColumnIndexOrThrow("text"));
        text.setText(item);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.item, parent, false);
        text=(TextView)view.findViewById(R.id.item);

        return view;
    }
}