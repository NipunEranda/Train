package com.train.adapters;

import android.content.Context;
import android.os.PersistableBundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.train.R;
import com.train.TrainTimeTable;
import com.train.utils.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TimeTableAdapter extends BaseAdapter {

    DatabaseHelper trainDB;
    Context context;
    ArrayList<TrainTimeTable> trainTimeTable = new ArrayList<>();
    String startStation;

    public TimeTableAdapter(Context context, ArrayList<TrainTimeTable> trainTimeTable){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.train_timetable_listview_item, viewGroup, false);
        }

        TrainTimeTable timeTable = (TrainTimeTable)getItem(i);

        TextView timeTableName = view.findViewById(R.id.timeTableName);
        TextView startStation = view.findViewById(R.id.timeTableStartStation);
        TextView endStation = view.findViewById(R.id.timeTableEndStation);

        timeTableName.setText(timeTable.getTimeTableName());
        startStation.setText(trainDB.getStationName(timeTable.getStartStation()));
        endStation.setText(trainDB.getStationName(timeTable.getStartStation()));

        return view;
    }
}
