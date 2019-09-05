package com.train;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.train.adapters.TimeTableAdapter;
import com.train.utils.DatabaseHelper;

import java.util.ArrayList;

public class CreateTimeTable extends Fragment implements View.OnClickListener {

    DatabaseHelper trainDB;
    Spinner startStation, endStation, trainType;
    Button arrivalBtn, departBtn,dateBtn, saveBtn;
    TextView arrivalTimeTxt, departTimeTxt, dateTxt;
    View view;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_time_table, container, false);

        arrivalBtn = (Button) view.findViewById(R.id.arrivalTimeBtn);
        departBtn = (Button) view.findViewById(R.id.departTimeBtn);
        dateBtn = (Button) view.findViewById(R.id.dateBtn);
        saveBtn = (Button) view.findViewById(R.id.editBtn);
        arrivalTimeTxt = (TextView)view.findViewById(R.id.arrivalTimeTxt);
        departTimeTxt = (TextView)view.findViewById(R.id.departTxt);
        dateTxt = (TextView)view.findViewById(R.id.dateTxt);
        arrivalBtn.setOnClickListener(this);
        departBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        trainDB = new DatabaseHelper(getContext());


        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Create Timetables");

        startStation = view.findViewById(R.id.startStation);
        endStation = view.findViewById(R.id.endStation);
        trainType = view.findViewById(R.id.trainType);

        ArrayAdapter<String> startStationAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.startStations));
        startStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startStation.setAdapter(startStationAdapter);


        ArrayAdapter<String> endStationAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.endStations));
        endStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endStation.setAdapter(endStationAdapter);

        ArrayAdapter<String> trainTypeAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.trainTypes));
        trainTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainType.setAdapter(trainTypeAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrivalTimeBtn:
                getDateTime.getTimeView(getContext(), arrivalTimeTxt);
                break;
            case R.id.departTimeBtn:
                getDateTime.getTimeView(getContext(), departTimeTxt);
                break;
            case R.id.dateBtn:
                getDateTime.getDateView(getContext(), dateTxt);
                break;
            case R.id.editBtn:
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).addToBackStack(null).commit();
                break;

        }
    }

}