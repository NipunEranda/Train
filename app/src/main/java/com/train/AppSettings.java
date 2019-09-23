package com.train;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;


public class AppSettings extends Fragment implements View.OnClickListener {

    DatabaseHelper trainDB;
    Button setDefaultTables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.app_settings, container, false);

        trainDB = new DatabaseHelper(getContext());
        setDefaultTables = view.findViewById(R.id.setDefaultTablesBtn);
        setDefaultTables.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setDefaultTablesBtn:
                trainDB.setDefaultTimeTables();
                Toast.makeText(getContext(), "Default Tables Recovered", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
