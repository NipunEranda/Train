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

    private int tableNumber;
    private String tableName;
    private String arrival;
    private String depart;

    public TimeTable(){

    }

    public TimeTable(String tableName, String arrival, String depart){
        this.tableName = tableName;
        this.arrival = arrival;
        this.depart = depart;
    }

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public void setArrival(String arrival){
        this.arrival = arrival;
    }
    public void setDepart(String depart){
        this.depart = depart;
    }

    public String getTableName(){
        return tableName;
    }

    public String getArrival(){
        return arrival;
    }

    public String getDepart(){
        return depart;
    }

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
