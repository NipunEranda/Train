package com.train;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimeTable extends Fragment implements View.OnClickListener {

    Button searchTimeTable, createTimeTable, editTimeTable, deleteTimeTable;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetables, container, false);

        searchTimeTable = (Button) view.findViewById(R.id.setAlarms);
        searchTimeTable.setOnClickListener(this);
        createTimeTable = (Button) view.findViewById(R.id.viewAlarms);
        createTimeTable.setOnClickListener(this);
        editTimeTable = (Button) view.findViewById(R.id.editTimeTable);
        editTimeTable.setOnClickListener(this);
        deleteTimeTable = (Button) view.findViewById(R.id.deleteTimeTable);
        deleteTimeTable.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.setAlarms:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchTimeTable()).addToBackStack(null).commit();
                break;

            case R.id.viewAlarms:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateTimeTable()).addToBackStack(null).commit();
                break;

            case R.id.editTimeTable:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTimeTable()).addToBackStack(null).commit();
                break;

            case R.id.deleteTimeTable:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecentSearches()).addToBackStack(null).commit();
                break;


        }

    }

    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("TimeTables");
    }
}
