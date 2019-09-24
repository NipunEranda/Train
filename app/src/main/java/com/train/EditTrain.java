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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;


public class EditTrain extends Fragment implements View.OnClickListener{

    String stationsArray[], trainsArray[];
    Button updBtn, canlEBtn, deleBtn;
    EditText edTid, edTrainName;
    Spinner startingStationSpinner, endStationSpinner;
    View view;
    DatabaseHelper trainDB;

    @Nullable
    @Override //start
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_train, container, false);

        trainDB = new DatabaseHelper(getContext());
        edTid = view.findViewById(R.id.editTrainId);
        edTrainName = view.findViewById(R.id.editTrainName);
        startingStationSpinner = view.findViewById(R.id.startingStationSpinner);
        endStationSpinner = view.findViewById(R.id.endStationSpinner);
        updBtn = view.findViewById(R.id.udateTbtn);
        updBtn.setOnClickListener(this);
        canlEBtn = view.findViewById(R.id.cancelTbtn);
        canlEBtn.setOnClickListener(this);
        deleBtn = view.findViewById(R.id.editDeletebtn);
        deleBtn.setOnClickListener(this);

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
                .setActionBarTitle("EditTrains");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.udateTbtn:
                if(edTid.getText().toString().isEmpty()) {
                    Toast.makeText(this.getActivity(), "Id cannot be empty",Toast.LENGTH_SHORT).show();
                    /*edTid.setError("Train id can't be Empty");*/
                }else {
                    boolean isUpdate = trainDB.udateATrain(edTid.getText().toString(),
                            edTrainName.getText().toString(),
                            startingStationSpinner.getSelectedItemPosition(),
                            endStationSpinner.getSelectedItemPosition());
                    if (isUpdate == true) {
                        Toast.makeText(this.getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).addToBackStack(null).commit();
                    } else
                        Toast.makeText(this.getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.cancelTbtn:
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
            case R.id.editDeletebtn:
                Integer deleteRows = trainDB.deleteATrain(edTid.getText().toString());
                if(deleteRows != 0)
                    Toast.makeText(this.getActivity(),"Selected Data hs been Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this.getActivity(),"No data were Deleted, please enter a Train Id", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}

