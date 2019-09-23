package com.train;

import android.content.Context;
import android.database.Cursor;
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

import com.train.utils.DatabaseHelper;
import com.train.utils.Utils;

import java.util.ArrayList;

public class RecentSearches extends Fragment implements View.OnClickListener{

    DatabaseHelper trainDB;
    ListView recentSearchList;
    ArrayList<TrainTimeTable> trainTimeTable = new ArrayList<>();
    RecentSearchAdapter adapter;
    Button clearRecentSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_searches, container, false);

        recentSearchList = (ListView) view.findViewById(R.id.recentSearchesView);
        clearRecentSearch = (Button) view.findViewById(R.id.clearRecentBtn);
        clearRecentSearch.setOnClickListener(this);

        trainDB = new DatabaseHelper(getContext());

        Cursor res = trainDB.getAllRecentTables();
        if(res.getCount() == 0){
            Utils.showMessage("", "No Recent Searches", getContext());
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).addToBackStack(null).commit();
        }else {
            if (recentSearchList.getCount() == 0) {
                fillListView();
            }
        }

        adapter = new RecentSearchAdapter(getContext(), R.layout.recent_searches_listview_item, trainTimeTable);
        recentSearchList.setAdapter(adapter);

        return view;
    }


    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Recent Timetables");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.clearRecentBtn:
                clearAll();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecentSearches()).addToBackStack(null).commit();
                break;
        }
    }

    public void fillListView(){

        Cursor res = trainDB.getAllRecentTables();

        if(!res.moveToFirst()){
            res = trainDB.getAllRecentTables();
        }
        do{
            Cursor tables = trainDB.getATimeTable(res.getInt(1));
            while (tables.moveToNext()){
                TrainTimeTable timeTable = new TrainTimeTable(tables.getInt(0), tables.getString(1), tables.getInt(2), tables.getInt(3), tables.getString(4), tables.getString(5), tables.getString(6), tables.getInt(7));
                trainTimeTable.add(timeTable);
            }
        }while (res.moveToNext());

    }

    public void clearAll(){
        boolean res = trainDB.deleteAllRecentTable();
    }

    public class RecentSearchAdapter extends ArrayAdapter<TrainTimeTable>{

        ArrayList<TrainTimeTable> timetables;

        public RecentSearchAdapter(@NonNull Context context, int resource, ArrayList<TrainTimeTable> timeTables) {
            super(context, resource, timeTables);
            this.timetables = timeTables;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.recent_searches_listview_item, parent, false);

            final TextView timeTableName = view.findViewById(R.id.recentTimeTableName);
            TextView startStation = view.findViewById(R.id.recentTimeTableStartStation);
            TextView endStation = view.findViewById(R.id.recentTimeTableEndStation);
            Button delete = view.findViewById(R.id.recentTableDeleteBtn);
            Button viewBtn = view.findViewById(R.id.recentTableViewBtn);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trainDB.deleteRecentTable(""+(timetables.get(position).getTimeTableId()));
                    timetables.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });

            viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTimeTable(timetables.get(position))).addToBackStack(null).commit();
                }
            });

            timeTableName.setText(timetables.get(position).getTimeTableName());
            startStation.setText(trainDB.getStationName(timetables.get(position).getStartStation()));
            endStation.setText(trainDB.getStationName(timetables.get(position).getEndStation()));
            return view;
        }
    }


}