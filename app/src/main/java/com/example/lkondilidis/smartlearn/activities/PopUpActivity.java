package com.example.lkondilidis.smartlearn.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.LectureAdapter;
import com.example.lkondilidis.smartlearn.helpers.CSVReader;
import com.example.lkondilidis.smartlearn.helpers.DrawerNavigationListener;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PopUpActivity extends Activity implements SearchView.OnSuggestionListener {

    private ListView listView;
    private LectureAdapter itemArrayAdapter;
    private SearchView searchView;
    private User currentuser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        currentuser = (User) getIntent().getSerializableExtra(MainActivity.USER_DETAIL_KEY);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));


        listView = (ListView) findViewById(R.id.listView_popup);
        itemArrayAdapter = new LectureAdapter(getApplicationContext(), R.layout.lecture_item_layout);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);


        SearchManager manager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView_popup);

        assert manager != null;
        searchView.setSearchableInfo(manager.getSearchableInfo(this.getComponentName()));
        searchView.setOnSuggestionListener(this);
        searchView.setSubmitButtonEnabled(true);

        List<Lecture> lectureList = new ArrayList<>(currentuser.getLectures());

        for(Lecture lectureData:lectureList ) {
            itemArrayAdapter.add(lectureData);
        }
        itemArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSuggestionSelect(int i) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int i) {
        return false;
    }
}
