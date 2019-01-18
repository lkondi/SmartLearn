package com.example.lkondilidis.smartlearn.activities;

import android.app.Activity;
import android.os.Parcelable;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.CSVReader;

import java.io.InputStream;
import java.util.List;

import com.example.lkondilidis.smartlearn.adapters.LectureAdapter;


public class LectureActivity extends Activity {
    private ListView listView;
    private LectureAdapter itemArrayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        listView = (ListView) findViewById(R.id.listView);
        itemArrayAdapter = new LectureAdapter(getApplicationContext(), R.layout.lecture_item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);

        InputStream inputStream = getResources().openRawResource(R.raw.stats);
        CSVReader csvFile = new CSVReader(inputStream);
        List<String[]> lectureList = csvFile.read();

        for(String[] lectureData:lectureList ) {
            itemArrayAdapter.add(lectureData);
        }
    }
}
