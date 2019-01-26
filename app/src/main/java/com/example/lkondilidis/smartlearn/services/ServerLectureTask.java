package com.example.lkondilidis.smartlearn.services;


import android.os.AsyncTask;
import com.example.lkondilidis.smartlearn.Interfaces.StatusLectureFlag;
import com.example.lkondilidis.smartlearn.adapters.LectureAdapter;
import com.example.lkondilidis.smartlearn.adapters.SearchAdapter;
import com.example.lkondilidis.smartlearn.adapters.UserAdapter;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ServerLectureTask extends AsyncTask<Void, Void, List<Lecture>>
{
    private List<Lecture> lectures;
    private ApiAuthenticationClient auth;
    private StatusLectureFlag flag;
    private SearchAdapter searchAdapter;
    private UserAdapter userAdapter;
    private LectureAdapter itemArrayAdapter;
    private User currentuser;

    public ServerLectureTask(List<Lecture> lectures, User currentuser, ApiAuthenticationClient auth, StatusLectureFlag flag){
        this.lectures = lectures;
        this.auth = auth;
        this.flag = flag;
        this.currentuser = currentuser;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
    }

    @Override
    protected List<Lecture> doInBackground(Void... voids) {
        ArrayList<Lecture> lectures = new ArrayList<>();
        String output = auth.execute();

        switch (flag){
            case SERVER_STATUS_ADD_LECTURE: addLecture(output, lectures);
                break;
            case SERVER_STATUS_GET_LECTURE: addLectures(output, lectures);
                break;
                default:
                    break;
        }
        return lectures;
    }

    private void addLectures(String output, ArrayList<Lecture> lectures) {
        try {
            JSONArray response = new JSONArray(output);
            for(int i=0; i<response.length(); i++){
                JSONObject jsonLecture= response.getJSONObject(i);
                Lecture tempLecture = new Lecture((jsonLecture));
                lectures.add(tempLecture);
            }
        } catch (JSONException e) {
            addLecture(output, lectures);
        }
    }

    private void addLecture(String output, ArrayList<Lecture> lectures) {
        try {
            JSONObject jsonLecture = new JSONObject(output);
            Lecture tempLecture = new Lecture((jsonLecture));
            lectures.add(tempLecture);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(List<Lecture> tempLectures) {
        switch (flag){
            case SERVER_STATUS_ADD_LECTURE: updateLectureList(tempLectures);
                break;
            case SERVER_STATUS_GET_LECTURE: updateLectureList(tempLectures);
                break;
            default:
                break;
        }
        if(itemArrayAdapter != null) {
            itemArrayAdapter.clear();
            itemArrayAdapter.clearLists();
            for(Lecture lectureData:lectures) {
                itemArrayAdapter.add(lectureData);
            }
        }
        updateAdapters();
    }

    private void updateLectureList(List<Lecture> tempLectures) {
        this.lectures.retainAll(tempLectures);
        for(Lecture tempLecture: tempLectures) {
            if(!containsLecture(this.lectures, tempLecture)){
                lectures.add(tempLecture);
            }
        }

        ArrayList<Lecture> removeLectures = new ArrayList<>();
        for(Lecture lecture: this.lectures){
            if(currentuser.hasLecture(lecture.getId())){
                removeLectures.add(lecture);
            }
        }
        this.lectures.removeAll(removeLectures);
    }

    private boolean containsLecture(List<Lecture> copyLectures, Lecture tempLecture) {
        for (Lecture lecture : copyLectures) {
            if(lecture.getId() == tempLecture.getId()){
                return true;
            }
        }
        return false;
    }

    public void updateAdapters(){
        if( searchAdapter != null) {
            searchAdapter.notifyDataSetChanged();
        }
        if( userAdapter != null) {
            userAdapter.notifyDataSetChanged();
        }
        if( itemArrayAdapter != null) {
            itemArrayAdapter.notifyDataSetChanged();
        }
    }

    public void setAdapter(SearchAdapter searchAdapter) {
        this.searchAdapter = searchAdapter;
    }

    public void setAdapter(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    public void setAdapter(LectureAdapter itemArrayAdapter) {
        this.itemArrayAdapter = itemArrayAdapter;
    }
}
