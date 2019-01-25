package com.example.lkondilidis.smartlearn.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lkondilidis.smartlearn.Interfaces.StatusLectureFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.adapters.LectureAdapter;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerLectureTask;

import java.util.ArrayList;

public class PopUpActivity extends Activity{

    private LectureAdapter itemArrayAdapter;
    private User currentuser;
    private ArrayList<Lecture> lectureList = new ArrayList<>();
    public static final String USER_DETAIL_KEY = "currentuser";

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

        Button btn_add = (Button) findViewById(R.id.button_add);
        btn_add.setText("Add");
        Button btn_cancel = (Button) findViewById(R.id.button_cancel);
        btn_cancel.setText("Cancel");

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<lectureList.size(); i++){
                    if(itemArrayAdapter.getCheckboxes().get(i)){
                        currentuser.addLecture(lectureList.get(i));
                    }
                }
                if(itemArrayAdapter.checkBoxesClicked()){
                    currentuser.setChanged(true);
                    Intent intent = new Intent(PopUpActivity.this, UserLectureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(USER_DETAIL_KEY, currentuser);
                    startActivity(intent);
                    finish();
                } else {
                    //TODO: print: "nothing selected"
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView_popup);
        itemArrayAdapter = new LectureAdapter(getApplicationContext(), R.layout.lecture_item);

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(itemArrayAdapter);
        listView.onRestoreInstanceState(state);


        SearchManager manager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.searchView_popup);

        assert manager != null;
        searchView.setSearchableInfo(manager.getSearchableInfo(this.getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //myList.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("changed");

                //!!!!!!
                //connect to server and fetch Lectures
                ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
                auth.setHttpMethod("GET");
                auth.setUrlPath("lectures/" + s);
                ServerLectureTask serverLectureTask = new ServerLectureTask(lectureList, currentuser, auth, StatusLectureFlag.SERVER_STATUS_GET_LECTURE);
                serverLectureTask.setAdapter(itemArrayAdapter);
                serverLectureTask.execute();
                return true;
            }
        });
    }
}
