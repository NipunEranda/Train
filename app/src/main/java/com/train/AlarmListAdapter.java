package com.train;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Alarms> arrayList;

    public AlarmListAdapter(Context context, ArrayList<Alarms> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.listed_alarms,null);

        TextView alarmName = (TextView) view.findViewById(R.id.alarmName1);
        TextView station = (TextView) view.findViewById(R.id.station1);
        TextView trainTime = (TextView) view.findViewById(R.id.trainTime2);
        TextView alarmTime = (TextView) view.findViewById(R.id.alarmTime2);

        Alarms alarms = arrayList.get(i);

        alarmName.setText(alarms.getAlarmName());
        station.setText(alarms.getStation());
        trainTime.setText(alarms.getTrainTime());
        alarmTime.setText(alarms.getAlarmTime());

        return view;

    }
}
