package com.train;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;
import com.train.utils.getDateTime;

public class EditTimeTable extends Fragment implements View.OnClickListener {

    TrainTimeTable trainTimeTable;
    String stationsArray[], trainsArray[];
    Spinner startStationSpinner, endStationSpinner, trainIdSpinner;
    Button arrivalBtn, departBtn,dateBtn, saveBtn, swapBtn;
    TextView arrivalTimeTxt, departTimeTxt, dateTxt;
    View view;
    EditText tableName;
    DatabaseHelper trainDB;

    String routeName;
    int startStation;
    int endStation;
    String arrivalTime;
    String departTime;
    String date;
    int trainId;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_time_table, container, false);

        tableName = view.findViewById(R.id.routeName);
        arrivalBtn = (Button) view.findViewById(R.id.arrivalTimeBtn);
        departBtn = (Button) view.findViewById(R.id.departTimeBtn);
        dateBtn = (Button) view.findViewById(R.id.dateBtn);
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        swapBtn = (Button)view.findViewById(R.id.swapBtn);
        arrivalTimeTxt = (TextView)view.findViewById(R.id.arrivalTimeTxt);
        departTimeTxt = (TextView)view.findViewById(R.id.departTxt);
        dateTxt = (TextView)view.findViewById(R.id.dateTxt);
        arrivalBtn.setOnClickListener(this);
        departBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        swapBtn.setOnClickListener(this);
        trainDB = new DatabaseHelper(getContext());


        return view;
    }

    public EditTimeTable(TrainTimeTable trainTimeTable) {
        this.trainTimeTable = trainTimeTable;
    }

    public EditTimeTable() {
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("View Timetables");

        startStationSpinner = view.findViewById(R.id.startStation);
        endStationSpinner = view.findViewById(R.id.endStation);
        trainIdSpinner = view.findViewById(R.id.trainId);

        stationsArray = getResources().getStringArray(R.array.defaultStations);
        trainsArray = getResources().getStringArray(R.array.defaultTrains);

        trainDB.loadStations(startStationSpinner, stationsArray);
        trainDB.loadStations(endStationSpinner, stationsArray);
        trainDB.loadTrains(trainIdSpinner, trainsArray);

        tableName.setText(trainTimeTable.getTimeTableName());
        startStationSpinner.setSelection(trainTimeTable.getStartStation());
        endStationSpinner.setSelection(trainTimeTable.getEndStation());
        arrivalTimeTxt.setText(trainTimeTable.getArrivalTime());
        departTimeTxt.setText(trainTimeTable.getDepartTime());
        dateTxt.setText(trainTimeTable.getTrainDate());
        trainIdSpinner.setSelection(trainTimeTable.getTrainId());


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
            case R.id.swapBtn:
                int temp = startStationSpinner.getSelectedItemPosition();
                startStationSpinner.setSelection(endStationSpinner.getSelectedItemPosition());
                endStationSpinner.setSelection(temp);
                break;
            case R.id.saveBtn:
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).addToBackStack(null).commit();
                break;
        }
    }
}