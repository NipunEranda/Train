package com.train.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.train.R;
import com.train.TrainTimeTable;
import com.train.utils.DatabaseHelper;

import java.util.ArrayList;

public class RecentSearchesAdapter extends BaseAdapter{

    DatabaseHelper trainDB;
    Context context;
    ArrayList<TrainTimeTable> trainTimeTable = new ArrayList<>();
    String startStation;

    public RecentSearchesAdapter(Context context, ArrayList<TrainTimeTable> trainTimeTable){
        this.context = context;
        this.trainTimeTable = trainTimeTable;
        setDB(context);
    }

    public void setDB(Context context){
        trainDB = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return trainTimeTable.size();
    }

    @Override
    public Object getItem(int i) {
        return trainTimeTable.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.recent_searches_listview_item, viewGroup, false);
        }

        final TrainTimeTable timeTable = (TrainTimeTable)getItem(i);

        TextView timeTableName = view.findViewById(R.id.recentTimeTableName);
        TextView startStation = view.findViewById(R.id.recentTimeTableStartStation);
        TextView endStation = view.findViewById(R.id.recentTimeTableEndStation);
        final Button delete = view.findViewById(R.id.recentTableViewBtn);

        timeTableName.setText(timeTable.getTimeTableName());
        startStation.setText(trainDB.getStationName(timeTable.getStartStation()));
        endStation.setText(trainDB.getStationName(timeTable.getEndStation()));
        delete.setId(timeTable.getTimeTableId());
        delete.setText(""+delete.getId());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trainTimeTable.remove(i);
                Toast.makeText(context, String.valueOf(delete.getId()), Toast.LENGTH_SHORT).show();
                //trainDB.deleteRecentTable(String.valueOf(delete.getId()));
                //notifyDataSetChanged();
            }
        });

        return view;
    }
}
