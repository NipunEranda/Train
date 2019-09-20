package com.train;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;
import com.train.utils.getDateTime;

import java.util.ArrayList;

public class CreateTimeTable extends Fragment implements View.OnClickListener {

    int[] date;
    String stationsArray[], trainsArray[];
    DatabaseHelper trainDB;
    Spinner startStationSpinner, endStationSpinner, trainIdSpinner;
    Button arrivalBtn, departBtn,dateBtn, saveBtn, swapBtn;
    TextView arrivalTimeTxt, departTimeTxt, dateTxt, routeName;
    View view;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_time_table, container, false);

        routeName = view.findViewById(R.id.routeName);
        arrivalBtn = (Button) view.findViewById(R.id.arrivalTimeBtn);
        departBtn = (Button) view.findViewById(R.id.departTimeBtn);
        dateBtn = (Button) view.findViewById(R.id.dateBtn);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        arrivalTimeTxt = (TextView)view.findViewById(R.id.arrivalTimeTxt);
        departTimeTxt = (TextView)view.findViewById(R.id.departTxt);
        dateTxt = (TextView)view.findViewById(R.id.dateTxt);
        startStationSpinner = view.findViewById(R.id.startStation);
        endStationSpinner = view.findViewById(R.id.endStation);
        trainIdSpinner = view.findViewById(R.id.trainType);
        swapBtn = view.findViewById(R.id.swapBtn);
        arrivalBtn.setOnClickListener(this);
        departBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        swapBtn.setOnClickListener(this);
        trainDB = new DatabaseHelper(getContext());

        stationsArray = getResources().getStringArray(R.array.defaultStations);
        trainsArray = getResources().getStringArray(R.array.defaultTrains);

        trainDB.loadStations(startStationSpinner, stationsArray);
        trainDB.loadStations(endStationSpinner, stationsArray);
        trainDB.loadTrains(trainIdSpinner, trainsArray);


            startStationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(startStationSpinner.getSelectedItemPosition() != 0) {
                        if (i == endStationSpinner.getSelectedItemPosition()) {
                            Toast.makeText(getContext(), "Start Station cannot same as End Station", Toast.LENGTH_SHORT).show();
                            startStationSpinner.setSelection(0);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            endStationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(endStationSpinner.getSelectedItemPosition() != 0) {
                        if (i == startStationSpinner.getSelectedItemPosition()) {
                            Toast.makeText(getContext(), "End Station cannot same as Start Station", Toast.LENGTH_SHORT).show();
                            endStationSpinner.setSelection(0);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Create Timetables");
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
                date = getDateTime.getDateView(getContext(), dateTxt);
                break;
            case R.id.saveBtn:
                String dateText = String.valueOf(date[0]) + "-" + String.valueOf(date[1]) + "-" + String.valueOf(date[2]);
                boolean isInserted = trainDB.insertATimeTable(routeName.getText().toString(), startStationSpinner.getSelectedItemPosition(), endStationSpinner.getSelectedItemPosition(), arrivalTimeTxt.getText().toString(), departTimeTxt.getText().toString(), dateText, trainIdSpinner.getSelectedItemPosition(), 0);
                if(isInserted){
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).addToBackStack(null).commit();
                }else{
                    Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.swapBtn:
                int temp = startStationSpinner.getSelectedItemPosition();
                startStationSpinner.setSelection(endStationSpinner.getSelectedItemPosition());
                endStationSpinner.setSelection(temp);
                break;

        }
    }


}