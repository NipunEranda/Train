package com.train;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;


public class AddTrain extends Fragment implements View.OnClickListener {

    String stationsArray[], trainsArray[];
    Spinner spinnerType;
    Button savBtn, canlBtn;
    Spinner startingStationSpinner, endStationSpinner;
    EditText trainName;
    View view;
    DatabaseHelper trainDB;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_train, container, false);

        trainDB = new DatabaseHelper(getContext());
        /*spinnerType = view.findViewById(R.id.spinner1);*/
        savBtn = view.findViewById(R.id.saveBtn);
        canlBtn = view.findViewById(R.id.cancelBtn);
        savBtn.setOnClickListener(this);
        canlBtn.setOnClickListener(this);
        trainName = view.findViewById(R.id.trainNameTxt);
        startingStationSpinner = view.findViewById(R.id.startingStationSpinner);
        endStationSpinner = view.findViewById(R.id.endStationSpinner);

        stationsArray = getResources().getStringArray(R.array.defaultStations);
        trainsArray = getResources().getStringArray(R.array.defaultTrains);

        startingStationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(startingStationSpinner.getSelectedItemPosition() != 0) {
                    if (i == endStationSpinner.getSelectedItemPosition()) {
                        Toast.makeText(getContext(), "Start Station cannot same as End Station", Toast.LENGTH_SHORT).show();
                        startingStationSpinner.setSelection(0);
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
                    if (i == startingStationSpinner.getSelectedItemPosition()) {
                        Toast.makeText(getContext(), "End Station cannot same as Start Station", Toast.LENGTH_SHORT).show();
                        endStationSpinner.setSelection(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        trainDB.loadStations(startingStationSpinner, stationsArray);
        trainDB.loadStations(endStationSpinner, stationsArray);

        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("AddTrains");


        /*ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.defaultTrains));
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(mAdapter);*/

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveBtn:
                if(trainName.getText().toString().isEmpty()) {
                    trainName.setError("Train name can't be Empty");
                }else if(startingStationSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(this.getActivity(), "Starting station cannot be empty", Toast.LENGTH_SHORT).show();
                }else if(endStationSpinner.getSelectedItemPosition() == 0 ){
                    Toast.makeText(this.getActivity(), "Destination cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                    boolean isInserted = trainDB.insertATrain(trainName.getText().toString(),
                            startingStationSpinner.getSelectedItemPosition(),
                            endStationSpinner.getSelectedItemPosition());
                    if (isInserted == true) {
                        Toast.makeText(this.getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).addToBackStack(null).commit();
                    } else
                        Toast.makeText(this.getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cancelBtn:
                AlertDialog.Builder canBuilder = new AlertDialog.Builder(this.getActivity());
                canBuilder.setMessage("Are you Sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).addToBackStack(null).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert1 = canBuilder.create();
                alert1.show();

                break;
        }

    }
}