package com.train;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.train.adapters.TimeTableAdapter;

import java.util.ArrayList;

public class UserTimeTables extends Fragment implements AdapterView.OnItemClickListener{

    ListView trainTimeTableListView;
    ArrayList<TrainTimeTable> trainTimeTable = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_time_table, container, false);

        trainTimeTableListView = view.findViewById(R.id.userTimeTableList);

        return view;
    }


    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("User Timetables");
        TrainTimeTable train1 = new TrainTimeTable();
        train1.setTimeTableName("lol");
        train1.setStartStation(0);
        train1.setEndStation(1);

        TrainTimeTable train2 = new TrainTimeTable();
        train2.setTimeTableName("lol");
        train2.setStartStation(0);
        train2.setEndStation(1);

        trainTimeTable.add(train1);
        trainTimeTable.add(train2);
        fillListView();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tv = (TextView)view;
        Toast.makeText(getContext(), tv.getText(), Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTimeTable()).addToBackStack(null).commit();
    }


    public void fillListView(){

        TimeTableAdapter adapter = new TimeTableAdapter(getContext(), trainTimeTable);
        trainTimeTableListView.setAdapter(adapter);

    }
}