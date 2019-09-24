package com.train;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;
import com.train.utils.Utils;
import com.train.utils.getDateTime;

import java.util.ArrayList;

public class SearchTimeTable extends Fragment implements View.OnClickListener{

    String stationsArray[];
    DatabaseHelper trainDB;
    Spinner startStation, endStation;
    Switch nextTrain, dailyTrain, timeFilterCheck;
    View view;
    Button btnDatePicker, startTime, endTime, timeTableSaveBtn, swap;
    TextView startTimeTxt, endTimeTxt, dateTxt;
    ListView searchTimeTables;
    ArrayList<TrainTimeTable> timeTableArrayList;
    int[] pickedDate = new int[3];
    private int mYear, mMonth, mDay, mHour, mMinute;
    static boolean status = true;
    String date, startTimeText, endTimeText;
    int startStationID, endStationID;
    boolean isNextTrainOn, isDailyScheduleOn, isTimeFilterOn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_time_table, container, false);
        startStation = (Spinner) view.findViewById(R.id.startStation);
        endStation = (Spinner) view.findViewById(R.id.startingStationSpinner);
        nextTrain = (Switch) view.findViewById(R.id.nextTrainSwitch);
        dailyTrain = (Switch) view.findViewById(R.id.dailyTrainSwitch);
        timeFilterCheck = (Switch)view.findViewById(R.id.timeFilterCheck);
        btnDatePicker = view.findViewById(R.id.btn_date);
        startTime=(Button)view.findViewById(R.id.startTime);
        endTime=(Button)view.findViewById(R.id.endTime);
        timeFilterCheck.setChecked(false);
        startTime.setBackgroundColor(Color.parseColor("#E5E4E2"));
        startTime.setTextColor(Color.parseColor("#000000"));
        endTime.setBackgroundColor(Color.parseColor("#E5E4E2"));
        endTime.setTextColor(Color.parseColor("#000000"));
        swap = (Button)view.findViewById(R.id.swapBtn);
        dateTxt = view.findViewById(R.id.in_date);
        startTimeTxt=(TextView)view.findViewById(R.id.startTimeTxt);
        endTimeTxt=(TextView) view.findViewById(R.id.endTimeTxt);
        timeTableSaveBtn = (Button)view.findViewById(R.id.timeTableSearchBtn);
        searchTimeTables = view.findViewById(R.id.searchTimeTableList);
        btnDatePicker.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        swap.setOnClickListener(this);
        timeTableSaveBtn.setOnClickListener(this);
        timeTableArrayList = new ArrayList<>();
        trainDB = new DatabaseHelper(getContext());
        return view;

    }


    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Search Timetables");
        stationsArray = getResources().getStringArray(R.array.defaultStations);
        trainDB.loadStations(startStation, stationsArray);
        trainDB.loadStations(endStation, stationsArray);


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
                    startTimeTxt.setText("Set Start Time");
                    endTimeTxt.setText("Set End Time");
                }
            }
        });

        if(timeFilterCheck.isEnabled()){
            startTime.setEnabled(true);
            endTime.setEnabled(true);
        }else{
            startTime.setEnabled(false);
            endTime.setEnabled(false);
        }

        timeFilterCheck.setChecked(false);
        Utils.disableBtn(startTime);
        Utils.disableBtn(endTime);
        dateTxt.setText(null);

        startStation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(startStation.getSelectedItemPosition() != 0) {
                    if (i == endStation.getSelectedItemPosition()) {
                        Toast.makeText(getContext(), "Start Station cannot same as End Station", Toast.LENGTH_SHORT).show();
                        startStation.setSelection(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        endStation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(endStation.getSelectedItemPosition() != 0) {
                    if (i == startStation.getSelectedItemPosition()) {
                        Toast.makeText(getContext(), "End Station cannot same as Start Station", Toast.LENGTH_SHORT).show();
                        endStation.setSelection(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_date:
                pickedDate = getDateTime.getDateView(getContext(), dateTxt);
                break;

            case R.id.endTime:
                getDateTime.getTimeView(getContext(), endTimeTxt);
                break;

            case R.id.startTime:
                getDateTime.getTimeView(getContext(), startTimeTxt);
                break;

            case R.id.swapBtn:
                int temp = startStation.getSelectedItemPosition();
                startStation.setSelection(endStation.getSelectedItemPosition());
                endStation.setSelection(temp);
                break;

            case R.id.timeTableSearchBtn:
                date = null;
                startStationID = startStation.getSelectedItemPosition();
                endStationID = endStation.getSelectedItemPosition();
                isNextTrainOn = nextTrain.isChecked();
                isDailyScheduleOn = dailyTrain.isChecked();
                if(pickedDate[0] == 0) {
                    date = "";
                }else{
                    date = String.valueOf(pickedDate[0]) + "-" + String.valueOf(pickedDate[1] + "-" + String.valueOf(pickedDate[2]));
                }
                isTimeFilterOn = timeFilterCheck.isChecked();
                startTimeText = startTimeTxt.getText().toString();
                endTimeText = endTimeTxt.getText().toString();

                if(startStationID == -1 || startStationID == 0){
                    Utils.showMessage("Error", "Start Station Required", getContext());
                }else if(endStationID == -1 || endStationID == 0){
                    Utils.showMessage("Error", "End Station Required", getContext());
                }else{
                    if(isTimeFilterOn) {
                        if (startTimeTxt.getText().toString().equalsIgnoreCase("Set Start Time")) {
                            Utils.showMessage("Error", "Start Time Required", getContext());
                        } else if (endTimeTxt.getText().toString().equalsIgnoreCase("Set End Time")) {
                            Utils.showMessage("Error", "End Time Required", getContext());
                        }else{
                            search();
                        }
                    }else{
                        search();
                    }
                }


                break;

        }
    }
    public void search(){
        Cursor res = trainDB.timeTableSearch(startStationID, endStationID, isNextTrainOn, isDailyScheduleOn, date, isTimeFilterOn, startTimeText, endTimeText);
        if (res != null) {
            do {
                if (!res.moveToFirst()) {
                    Utils.showMessage("Error", "No Results", getContext());
                    break;
                } else {
                    TrainTimeTable trainTimeTable = new TrainTimeTable(res.getInt(0), res.getString(1), res.getInt(2), res.getInt(3), res.getString(4), res.getString(5), res.getString(6), res.getInt(7), res.getInt(8));
                    timeTableArrayList.add(trainTimeTable);
                }
            } while (res.moveToNext());
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchTimeTableView(timeTableArrayList)).addToBackStack(null).commit();
        }
    }
}