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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class AddTrain extends Fragment implements View.OnClickListener {

    Spinner spinnerType;
    Button savBtn, canlBtn;
    View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_train, container, false);

        spinnerType = view.findViewById(R.id.spinner1);
        savBtn = view.findViewById(R.id.saveBtn);
        canlBtn = view.findViewById(R.id.cancelBtn);
        savBtn.setOnClickListener(this);
        canlBtn.setOnClickListener(this);

        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("AddTrains");


        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.trainTypes));
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(mAdapter);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveBtn:
                AlertDialog.Builder savBuilder = new AlertDialog.Builder(this.getActivity());
                savBuilder.setMessage("Are you sure, you want to save?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewTrain()).addToBackStack(null).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = savBuilder.create();
                alert.show();
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
        }

    }
}