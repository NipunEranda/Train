package com.train;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimeTable extends Fragment implements View.OnClickListener {


    Button searchTimeTable, createTimeTable, editTimeTable, deleteTimeTable;
    TextView searchText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timetables, container, false);

        searchTimeTable = (Button) view.findViewById(R.id.searchTimeTable);
        searchTimeTable.setOnClickListener(this);
        createTimeTable = (Button) view.findViewById(R.id.createTimeTable);
        createTimeTable.setOnClickListener(this);
        editTimeTable = (Button) view.findViewById(R.id.userTimeTable);
        editTimeTable.setOnClickListener(this);
        deleteTimeTable = (Button) view.findViewById(R.id.recentSearches);
        deleteTimeTable.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.searchTimeTable:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchTimeTable()).addToBackStack(null).commit();
                break;

            case R.id.createTimeTable:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateTimeTable()).addToBackStack(null).commit();
                break;

            case R.id.userTimeTable:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserTimeTables()).addToBackStack(null).commit();
                break;

            case R.id.recentSearches:
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
