package com.train;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Station extends Fragment implements View.OnClickListener {

    Button addStation, editStation, searchStations, deleteStations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stations, container, false);
        addStation = (Button) view.findViewById(R.id.addStation);
        addStation.setOnClickListener(this);
        editStation = (Button) view.findViewById(R.id.editStation);
        editStation.setOnClickListener(this);
        searchStations = (Button) view.findViewById(R.id.searchStations);
        searchStations.setOnClickListener(this);
        deleteStations = (Button) view.findViewById(R.id.deleteStations);
        deleteStations.setOnClickListener(this);
        return view;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Stations");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addStation:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddStation()).addToBackStack(null).commit();
                break;

            case R.id.editStation:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditStation()).addToBackStack(null).commit();
                break;

            case R.id.searchStations:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchStation()).addToBackStack(null).commit();
                break;

            case R.id.deleteStations:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeleteStation()).addToBackStack(null).commit();
                break;

        }
    }
}
