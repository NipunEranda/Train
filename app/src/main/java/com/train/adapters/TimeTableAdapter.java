package com.train.adapters;

import android.content.Context;
import android.os.PersistableBundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.train.R;
import com.train.TrainTimeTable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TimeTableAdapter extends BaseAdapter {

    Context context;
    ArrayList<TrainTimeTable> trainTimeTable = new ArrayList<>();

    public TimeTableAdapter(Context context, ArrayList<TrainTimeTable> trainTimeTable){
        this.context = context;
        this.trainTimeTable = trainTimeTable;
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
        startStation.setText("" + timeTable.getStartStation());
        endStation.setText("" +timeTable.getEndStation());

        return view;
    }
}
