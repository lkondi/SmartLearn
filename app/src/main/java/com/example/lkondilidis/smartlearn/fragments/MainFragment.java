package com.example.lkondilidis.smartlearn.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lkondilidis.smartlearn.Interfaces.StatusFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.activities.MainActivity;
import com.example.lkondilidis.smartlearn.adapters.ExampleAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerTask;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements SearchView.OnSuggestionListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SearchAdapter searchAdapter;
    private SearchView searchView;
    private User currentuser;
    private ArrayList<User> userArrayList;
    private List lectures;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        activity = (MainActivity) getActivity();

        assert activity != null;
        currentuser = activity.getCurrentuser();
        userArrayList = new ArrayList<>();

        //lectures
        lectures = activity.getLectures();


        //recyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(activity, userArrayList, currentuser);
        recyclerView.setAdapter(searchAdapter);

        //!!!!!!
        //connect to server and fetch Users
        ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        auth.setHttpMethod("GET");
        auth.setUrlPath("tutors/" + currentuser.getId());
        ServerTask serverTask = new ServerTask(userArrayList, activity, auth, currentuser, null, StatusFlag.SERVER_STATUS_GET_USERS);
        serverTask.setAdapter(searchAdapter);
        serverTask.execute();

        //searchView
        SearchManager manager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        //myList = findViewById(R.id.listView1);
        searchView = view.findViewById(R.id.searchView);
        assert manager != null;
        searchView.setSearchableInfo(manager.getSearchableInfo(activity.getComponentName()));
        searchView.setOnSuggestionListener(this);
        searchView.setSubmitButtonEnabled(true);

        String[] columns = new String[]{"_id", "text"};
        Object[] temp = new Object[]{"0", "default"};
        final MatrixCursor cursor = new MatrixCursor(columns);
        for (int j = 0; j < lectures.size(); j++) {
            temp[0] = j;
            temp[1] = lectures.get(j);

            cursor.addRow(temp);
        }
        cursor.moveToFirst();

        ExampleAdapter exampleAdapter = new ExampleAdapter(activity, cursor, lectures);
        searchView.setSuggestionsAdapter(exampleAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //myList.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("changed");
                //if(myList.getVisibility() == View.GONE) {
                //myList.setVisibility(View.VISIBLE);
                //}
                load(s);
                return false;
            }
        });

        return view;
    }



    private void load(String s) {

        String[] columns=new String[]{"_id","text"};
        Object[] temp=new Object[]{"0","default"};
        final MatrixCursor cursor=new MatrixCursor(columns);
        for(int j=0;j<lectures.size();j++)
        {
            temp[0]=j;
            temp[1]=lectures.get(j);

            cursor.addRow(temp);
            //System.out.println(j+": "+temp[1]+" added to cursor");
        }
        //cursor.moveToFirst();


        ExampleAdapter exampleAdapter = new ExampleAdapter(activity,cursor,lectures);
        searchView.setSuggestionsAdapter(exampleAdapter);


        //myList.setAdapter(exampleAdapter);
    }

    @Override
    public boolean onSuggestionSelect(int i) {
        System.out.println("It works selected");
        return true;
    }

    @Override
    public boolean onSuggestionClick(int i) {
        //myList.setVisibility(View.GONE);
        String selectedLectureName = (String) lectures.get(i);

        List<User> tutorListSearched = new ArrayList<>();

        for (User user : userArrayList) {
            if(user.getSubject() == selectedLectureName) {
                tutorListSearched.add(user);
            }
        }

        searchAdapter = new SearchAdapter(activity, tutorListSearched, currentuser);
        recyclerView.setAdapter(searchAdapter);

        //userArrayList.remove(userArrayList.size()-1);
        //searchAdapter.notifyDataSetChanged();
        return true;
    }
}
