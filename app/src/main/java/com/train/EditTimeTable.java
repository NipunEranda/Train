package com.train;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        endStationSpinner = view.findViewById(R.id.startingStationSpinner);
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Save");
                    builder.setMessage("Are you sure");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editOn = false;
                            ((MainActivity) getActivity())
                                    .setActionBarTitle("View Timetables");
                            saveBtn.setText("Edit");
                            setAlarmBtn.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                            setAlarmBtn.setText("Set Alarm");
                            //set Query
                            boolean isUpdate = trainDB.editTimeTable(String.valueOf(trainTimeTable.getTimeTableId()), tableName.getText().toString(), startStationSpinner.getSelectedItemPosition(), endStationSpinner.getSelectedItemPosition(), arrivalTimeTxt.getText().toString(), departTimeTxt.getText().toString(), dateTxt.getText().toString(), trainIdSpinner.getSelectedItemPosition(), trainTimeTable.getIsDefault());
                            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).commit();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.show();
                }else{
                    editOn = true;
                    setFieldEnable();
                    ((MainActivity) getActivity())
                            .setActionBarTitle("Edit Timetables");
                    saveBtn.setText("Save");
                    setAlarmBtn.setText("Cancel");
                    setAlarmBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                break;

            case R.id.deleteBtn:

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete");
                builder.setMessage("Are you sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        trainDB.deleteTimeTable(String.valueOf(trainTimeTable.getTimeTableId()));
                        Toast.makeText(getContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserTimeTables()).commit();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
                break;

            case R.id.setAlarmBtn:
                if(setAlarmBtn.getText().toString().equalsIgnoreCase("cancel")){
                    setFieldDisable();
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
        Utils.disableBtn(deleteBtn);
        Utils.disableBtn(swapBtn);
        Utils.disableBtn(arrivalBtn);
        Utils.disableBtn(departBtn);
        Utils.disableBtn(dateBtn);
        tableName.setEnabled(false);
        tableName.setTextColor(Color.BLACK);
        deleteBtn.setEnabled(false);

    }

    public void setFieldEnable(){
        Utils.enableBtn(deleteBtn);
        Utils.enableBtn(swapBtn);
        Utils.enableBtn(arrivalBtn);
        Utils.enableBtn(departBtn);
        Utils.enableBtn(dateBtn);
        tableName.setFocusable(true);
        tableName.setEnabled(true);
        deleteBtn.setEnabled(true);
    }
}