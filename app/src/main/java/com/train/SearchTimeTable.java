package com.train;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ServiceWorkerClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SearchTimeTable extends Fragment implements View.OnClickListener{
    Spinner startStation, endStation;
    Switch nextTrain, dailyTrain, timeFilterCheck;
    View view;
    Button btnDatePicker, startTime, endTime, timeTableSaveBtn, swap;
    EditText txtDate;
    TextView startTimeTxt, endTimeTxt;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_time_table, container, false);
        startStation = (Spinner) view.findViewById(R.id.startStation);
        endStation = (Spinner) view.findViewById(R.id.endStation);
        nextTrain = (Switch) view.findViewById(R.id.nextTrainSwitch);
        dailyTrain = (Switch) view.findViewById(R.id.dailyTrainSwitch);
        timeFilterCheck = (Switch)view.findViewById(R.id.timeFilterCheck);
        btnDatePicker = view.findViewById(R.id.btn_date);
        startTime=(Button)view.findViewById(R.id.startTime);
        endTime=(Button)view.findViewById(R.id.endTime);
        startTime.setEnabled(false);
        endTime.setEnabled(false);
        startTime.setBackgroundColor(Color.parseColor("#E5E4E2"));
        startTime.setTextColor(Color.parseColor("#000000"));
        endTime.setBackgroundColor(Color.parseColor("#E5E4E2"));
        endTime.setTextColor(Color.parseColor("#000000"));
        swap = (Button)view.findViewById(R.id.swap);
        txtDate=(EditText)view.findViewById(R.id.in_date);
        startTimeTxt=(TextView)view.findViewById(R.id.startTimeTxt);
        endTimeTxt=(TextView) view.findViewById(R.id.endTimeTxt);
        timeTableSaveBtn = (Button)view.findViewById(R.id.timeTableSearchBtn);
        btnDatePicker.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        timeTableSaveBtn.setOnClickListener(this);
        return view;

    }


    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Search Timetables");

        ArrayAdapter<String> startStationAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.startStations));
        startStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startStation.setAdapter(startStationAdapter);


        ArrayAdapter<String> endStationAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.endStations));
        endStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endStation.setAdapter(endStationAdapter);


        //set Radio button
        nextTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextTrain.isSelected()) {
                    dailyTrain.setSelected(false);
                }
            }
        });

        dailyTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dailyTrain.isSelected()) {
                    nextTrain.setSelected(false);
                }
            }
        });

        nextTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nextTrain.isChecked())
                    dailyTrain.setChecked(false);

            }
        });

        dailyTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dailyTrain.isChecked()){
                    nextTrain.setChecked(false);
                }
            }
        });

        timeFilterCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timeFilterCheck.isChecked()){
                    Utils.enableBtn(startTime);
                    Utils.enableBtn(endTime);
                }else{
                    Utils.disableBtn(startTime);
                    Utils.disableBtn(endTime);
                }
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_date:
                getDateTime.getDateView(getContext(), txtDate);
                break;

            case R.id.endTime:
                getDateTime.getTimeView(getContext(), endTimeTxt);
                break;

            case R.id.startTime:
                getDateTime.getTimeView(getContext(), startTimeTxt);
                break;

            case R.id.timeTableSearchBtn:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchTimeTableView()).addToBackStack(null).commit();
                break;
        }
    }

}