package com.train;

import android.content.Context;
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

    }
}
