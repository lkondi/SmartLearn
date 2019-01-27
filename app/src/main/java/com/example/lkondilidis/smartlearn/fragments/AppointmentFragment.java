package com.example.lkondilidis.smartlearn.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.*;
import java.text.*;

import com.example.lkondilidis.smartlearn.Interfaces.StatusLectureFlag;
import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.Appointment;
import com.example.lkondilidis.smartlearn.model.Lecture;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerLectureTask;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class AppointmentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText startDateText;
    EditText endDateText;

    Calendar cal = Calendar.getInstance();

    TextView resultBox;
    String startDate;
    String endDate;


    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";
    User selecteduser;
    User currentuser;
    ListView newlistview;
    Spinner dropdown;
    ScheduleAdapter scheduleAdapter;
    Lecture selectedlecture;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_appointments, container, false);

        Bundle bundle = this.getArguments();
        currentuser = (User) bundle.getSerializable(USER_DETAIL_KEY);
        selecteduser = (User) bundle.getSerializable(SELECTED_USER_DETAIL_KEY);

        startDateText = (EditText) view.findViewById(R.id.startDateText);
        endDateText = (EditText) view.findViewById(R.id.endDateText);
        newlistview = (ListView) view.findViewById(R.id.newListView);

        //dropdown
        dropdown = (Spinner) view.findViewById(R.id.spinnerlectures);
        dropdown.setOnItemSelectedListener(this);
        List<String> list = new ArrayList<>();

        for(Lecture l: selecteduser.getLectures()){
            list.add(l.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);



        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), startListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), endListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button queryButton = (Button) view.findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startDate = startDateText.getText().toString();
                endDate = endDateText.getText().toString();

                retrieveSchedule(startDate,endDate);


            }
        });

        return view;

    }


    DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStart();
        }
    };


    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, monthOfYear);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEnd();
        }
    };

    private void updateStart() {

        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);
        startDateText.setText(sdf.format(cal.getTime()));

    }
    private void updateEnd() {

        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMANY);
        endDateText.setText(sdf.format(cal.getTime()));

    }

    public void retrieveSchedule(String start, String end) {
        scheduleAdapter = new ScheduleAdapter(getContext(), R.layout.appointment_item);
        newlistview.setAdapter(scheduleAdapter);
        List<String> datesList;

        String plan = selecteduser.getPlan();

        try {


            datesList = getAllDatesBetweenTwoDates(start, end, "yyyy-MM-dd", "dd/MM/yyyy", plan);
            Toast.makeText(getActivity(), plan + " is available",
                    Toast.LENGTH_LONG).show();

            for(String date : datesList ) {
                scheduleAdapter.add(date);
            }
            scheduleAdapter.notifyDataSetChanged();

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // TODO Auto-generated method stub
            //selectedlecture = (parent.getItemAtPosition(position).toString());
            String stringLecture = (parent.getItemAtPosition(position).toString());
            int iend = stringLecture.indexOf(" ");
            String subString = "";
            if (iend != -1)
            {
                subString= stringLecture.substring(0 , iend);
            }
            int lectureId = Integer.parseInt(subString);
            selectedlecture = selecteduser.findLecture(lectureId);
        //TODO find id based on lecture name
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub

    }

    class ScheduleAdapter extends ArrayAdapter {
        private ArrayList<String> scheduleList = new ArrayList();
        private Context context;

        class ItemViewHolder {
            TextView date;
            Button book;
        }

        public ScheduleAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            this.context = context;
        }

        //@Override
        public void add(String object) {
            scheduleList.add(object);
            super.add(object);
        }

        @Override
        public int getCount() {
            return this.scheduleList.size();
        }

        @Override
        public String getItem(int index) {
            return this.scheduleList.get(index);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ItemViewHolder viewHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.appointment_item, parent, false);
                viewHolder = new ItemViewHolder();
                viewHolder.date = (TextView) row.findViewById(R.id.app_day);
                viewHolder.book = (Button) row.findViewById(R.id.btnbook);
                row.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder)row.getTag();
            }

            final String date = getItem(position);
            viewHolder.date.setText(date);
            viewHolder.book.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Appointment appointment = new Appointment();
                    appointment.setSubject(selectedlecture);
                    appointment.setAppointmentAuthor(currentuser);
                    appointment.setAppointmentUser(selecteduser);
                    appointment.setDate(date);
                   //appointment.setTime();

                    //TODO set appointments & update users
                    ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
                    auth.setHttpMethod("POST");
                    auth.setUrlPath("appointment/add/"+selecteduser.getId());
                    auth.setPayload(appointment.convertToJSON(null));
                    ServerUserTask serverUserTask = new ServerUserTask(null, context, auth, currentuser, null, StatusUserFlag.SERVER_STATUS_UPDATE_USER);
                    serverUserTask.execute();





                   // currentuser.setYourAppointments(appointment);
                   // selecteduser.setYourAppointments();

                    Toast.makeText(getActivity(),  "Thank you for schedulling!",
                            Toast.LENGTH_LONG).show();
                }
            });

            return row;

        }
    }

    public static List<String> getAllDatesBetweenTwoDates(String stdate,String enddate,String givenformat
            ,String resultformat,String plan) throws ParseException{

        String[] datesavailable = plan.split(",");

        DateFormat sdf;
        DateFormat sdf1;
        List<Date> dates = new ArrayList<Date>();
        List<String> dateList = new ArrayList<String>();
        SimpleDateFormat checkformat = new SimpleDateFormat(resultformat);
        checkformat.applyPattern("EEE");  // to get Day of week

        try{
            sdf = new SimpleDateFormat(givenformat);
            sdf1 = new SimpleDateFormat(resultformat);
            stdate=sdf1.format(sdf.parse(stdate));
            enddate=sdf1.format(sdf.parse(enddate));

            Date  startDate = (Date)sdf1.parse( stdate);
            Date  endDate = (Date)sdf1.parse( enddate);
            long interval = 24*1000 * 60 * 60; // 1 hour in millis
            long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
            long curTime = startDate.getTime();
            while (curTime <= endTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            for(int i=0;i<dates.size();i++){
                Date lDate =(Date)dates.get(i);
                String ds = sdf1.format(lDate);
                String day= checkformat.format(lDate);
                for (String adate : datesavailable) {
                    switch (adate) {
                        case "Montag":
                            if(day.equalsIgnoreCase("Mon")){
                                dateList.add(ds);}
                            break;
                        case "Dienstag":
                            if(day.equalsIgnoreCase("Tue")){
                                dateList.add(ds);}
                            break;
                        case "Mittwoch":
                            if(day.equalsIgnoreCase("Wed")){
                                dateList.add(ds);}
                            break;
                        case "Donnerstag":
                            if(day.equalsIgnoreCase("Thu")){
                                dateList.add(ds);}
                            break;
                        case "Freitag":
                            if(day.equalsIgnoreCase("Fri")){
                                dateList.add(ds);}
                            break;
                        case "Samstag":
                            if(day.equalsIgnoreCase("Sat")){
                                dateList.add(ds);}
                            break;
                        case "Sonntag":
                            if(day.equalsIgnoreCase("Sun")){
                                dateList.add(ds);}
                            break;
                    }
                }
            }

        }catch(ParseException e){
            e.printStackTrace();
            throw e;
        }finally{
            sdf=null;
            sdf1=null;
        }
        return dateList;
    }
}
