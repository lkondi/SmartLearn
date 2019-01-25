package com.example.lkondilidis.smartlearn.activities;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment{

    public static final String USER_DETAIL_KEY = "currentuser";
    public static final String SELECTED_USER_DETAIL_KEY = "selecteduser";
    User selecteduser;
    User currentuser;

    EditText card_name, card_number, card_date;

    ListView cardlist;
    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_payments, container, false);

        Bundle bundle = this.getArguments();
        currentuser = (User) bundle.getSerializable(USER_DETAIL_KEY);
        selecteduser = (User) bundle.getSerializable(SELECTED_USER_DETAIL_KEY);

        cardlist = (ListView) view.findViewById(R.id.payment_list_view);

        card_name = (EditText) view.findViewById(R.id.name1);
        card_number = (EditText) view.findViewById(R.id.number1);
        card_date = (EditText) view.findViewById(R.id.date1);
        Button btnsave = (Button) view.findViewById(R.id.save);

        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });

        return view;
    }

}
