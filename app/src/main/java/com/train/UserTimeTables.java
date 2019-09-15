package com.train;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.train.adapters.TimeTableAdapter;
import com.train.utils.DatabaseHelper;

import java.util.ArrayList;

public class UserTimeTables extends Fragment implements AdapterView.OnItemClickListener{

    DatabaseHelper trainDB;
    ListView trainTimeTableListView;
    ListView searchTimeTables;
    ArrayList<TrainTimeTable> trainTimeTable = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_time_table, container, false);
        trainTimeTableListView = view.findViewById(R.id.searchTimeTableList);
        trainTimeTableListView.setOnItemClickListener(this);

        trainDB = new DatabaseHelper(getContext());

        if(trainTimeTable.size() == 0) {
            Cursor res = trainDB.getAllTimeTables();
            if (!res.moveToFirst()) {
                res = trainDB.getAllTimeTables();
            }
            do {
                TrainTimeTable timetable = new TrainTimeTable(res.getInt(0), res.getString(1), res.getInt(2), res.getInt(3), res.getString(4), res.getString(5), res.getString(6), res.getInt(7));
                trainTimeTable.add(timetable);
            } while (res.moveToNext());
        }
        fillListView();

        return view;
    }


    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("User Timetables");

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        trainDB.insertRecentTable(trainTimeTable.get(i).getTimeTableId());
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTimeTable(trainTimeTable.get(i))).addToBackStack(null).commit();
    }


    public void fillListView(){
        TimeTableAdapter adapter = new TimeTableAdapter(getContext(), trainTimeTable);
        trainTimeTableListView.setAdapter(adapter);
    }
}