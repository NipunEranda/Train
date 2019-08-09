package com.train;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddAlarm extends Fragment implements View.OnClickListener {

    //the first change by sachintha
    //next
    //next 2

    Button setClock, setAlarmBtn;
    TextView setAlarmTime;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_alarm, container, false);
        setClock = (Button)view.findViewById(R.id.setClock);
        setAlarmBtn = (Button)view.findViewById(R.id.setAlarmButton);
        setAlarmTime = (TextView) view.findViewById(R.id.setAlarmTime);
        setClock.setOnClickListener(this);
        setAlarmBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setClock:
                getDateTime.getTimeView(getContext(), setAlarmTime);
                break;

            case R.id.setAlarmButton:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListedAlarms()).addToBackStack(null).commit();
                break;
        }
    }
}
