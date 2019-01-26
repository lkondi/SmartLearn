package com.example.lkondilidis.smartlearn.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lkondilidis.smartlearn.Interfaces.StatusLectureFlag;
import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.adapters.ExampleAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerLectureTask;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment{

    private User currentuser;
    private List<Lecture> lectures = new ArrayList<>();
    private ExampleAdapter exampleAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        MainActivity activity = (MainActivity) getActivity();

        assert activity != null;
        currentuser = activity.getCurrentuser();
        ArrayList<User> userArrayList = new ArrayList<>();

        //recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        SearchAdapter searchAdapter = new SearchAdapter(activity, userArrayList, currentuser);
        recyclerView.setAdapter(searchAdapter);

        //!!!!!!
        //connect to server and fetch Users
        ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        auth.setHttpMethod("GET");
        auth.setUrlPath("tutors/" + currentuser.getId());
        ServerUserTask serverUserTask = new ServerUserTask(userArrayList, activity, auth, currentuser, null, StatusUserFlag.SERVER_STATUS_GET_USERS);
        serverUserTask.setAdapter(searchAdapter);
        serverUserTask.execute();

        //searchView
        SearchManager manager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        //myList = findViewById(R.id.listView1);
        SearchView searchView = view.findViewById(R.id.searchView);
        assert manager != null;
        searchView.setSearchableInfo(manager.getSearchableInfo(activity.getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        String[] columns=new String[]{"_id","text","number"};
        MatrixCursor cursor=new MatrixCursor(columns);

        exampleAdapter = new ExampleAdapter(activity,cursor, userArrayList, searchView, currentuser, recyclerView);
        searchView.setSuggestionsAdapter(exampleAdapter);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //!!!!!!
                //connect to server and fetch Lectures
                ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
                auth.setHttpMethod("GET");
                auth.setUrlPath("lectures/" + s+","+30);
                ServerLectureTask serverLectureTask = new ServerLectureTask(lectures, currentuser, auth, StatusLectureFlag.SERVER_STATUS_GET_LECTURE);
                serverLectureTask.setAdapter(exampleAdapter);
                serverLectureTask.execute();
                return true;
            }
        });

        searchView.clearFocus();
        return view;
    }
}
