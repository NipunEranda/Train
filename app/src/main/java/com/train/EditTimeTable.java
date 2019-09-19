package com.train;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import com.train.utils.Utils;
import com.train.utils.getDateTime;

public class EditTimeTable extends Fragment implements View.OnClickListener {

    private TrainTimeTable trainTimeTable;
    private String stationsArray[], trainsArray[];
    private Spinner startStationSpinner, endStationSpinner, trainIdSpinner;
    private Button arrivalBtn, departBtn,dateBtn, saveBtn, swapBtn, deleteBtn, setAlarmBtn;
    private TextView arrivalTimeTxt, departTimeTxt, dateTxt;
    private View view;
    private EditText tableName;
    private DatabaseHelper trainDB;

    private int timeTableId;
    private int isDefault;
    private boolean editOn = false;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_time_table, container, false);

        tableName = view.findViewById(R.id.routeName);
        arrivalBtn = view.findViewById(R.id.arrivalTimeBtn);
        departBtn = view.findViewById(R.id.departTimeBtn);
        dateBtn = view.findViewById(R.id.dateBtn);
        saveBtn = view.findViewById(R.id.saveBtn);
        swapBtn = view.findViewById(R.id.swapBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);
        setAlarmBtn = view.findViewById(R.id.setAlarmBtn);
        arrivalTimeTxt = view.findViewById(R.id.arrivalTimeTxt);
        departTimeTxt = view.findViewById(R.id.departTxt);
        dateTxt = view.findViewById(R.id.dateTxt);
        arrivalBtn.setOnClickListener(this);
        departBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        swapBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        setAlarmBtn.setOnClickListener(this);
        trainDB = new DatabaseHelper(getContext());

        Utils.disableBtn(deleteBtn);
        setFieldDisable();

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

        timeTableId = trainTimeTable.getTimeTableId();
        isDefault = trainTimeTable.getIsDefault();
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

                if(saveBtn.getText().toString().equalsIgnoreCase("save")){
                    Utils.disableBtn(deleteBtn);
                    ((MainActivity) getActivity())
                            .setActionBarTitle("View Timetables");
                    saveBtn.setText("Edit");
                    setAlarmBtn.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    setAlarmBtn.setText("Set Alarm");
                    //set Query
                    boolean isUpdate = trainDB.editTimeTable(String.valueOf(trainTimeTable.getTimeTableId()), tableName.getText().toString(), startStationSpinner.getSelectedItemPosition(), endStationSpinner.getSelectedItemPosition(), arrivalTimeTxt.getText().toString(), departTimeTxt.getText().toString(), dateTxt.getText().toString(), trainIdSpinner.getSelectedItemPosition(), trainTimeTable.getIsDefault());
                    if(isUpdate) {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).commit();
                    }else{
                        Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Utils.enableBtn(deleteBtn);
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Edit Timetables");
                    saveBtn.setText("Save");
                    setAlarmBtn.setText("Cancel");
                    setAlarmBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                break;

            case R.id.deleteBtn:
                trainDB.deleteTimeTable(String.valueOf(trainTimeTable.getTimeTableId()));
                Toast.makeText(getContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserTimeTables()).commit();
                break;

            case R.id.setAlarmBtn:
                if(setAlarmBtn.getText().toString().equalsIgnoreCase("cancel")){
                    Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    setAlarmBtn.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    Utils.disableBtn(deleteBtn);
                    ((MainActivity) getActivity())
                            .setActionBarTitle("View Timetables");
                    saveBtn.setText("Edit");
                    setAlarmBtn.setText("Set Alarm");
                }else {
                    Intent i = new Intent(getContext(), AddAlarm.class);
                    startActivity(i);
                }
                break;
        }
    }

    public void setFieldDisable(){
        tableName.setEnabled(false);
    }
}