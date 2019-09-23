package com.train;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.train.adapters.TimeTableAdapter;

import java.util.ArrayList;

public class SearchTimeTableView extends Fragment implements AdapterView.OnItemClickListener{

    ArrayList<TrainTimeTable> timetableList;
    ListView trainTimeTableListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_time_table_view, container, false);
        trainTimeTableListView = view.findViewById(R.id.searchTimeTableList);
        fillListView();
        return view;
    }

    public SearchTimeTableView(ArrayList<TrainTimeTable> timeTables){
        timetableList = timeTables;
    }

    @Override
    public void onResume() {
        super.onResume();
        trainTimeTableListView.setOnItemClickListener(this);
    }

    public void fillListView(){
            TimeTableAdapter adapter = new TimeTableAdapter(getContext(), timetableList);
            trainTimeTableListView.setAdapter(adapter);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTimeTable(timetableList.get(i))).addToBackStack(null).commit();
    }
}
