package com.train;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Alarm extends Fragment implements View.OnClickListener {

    TimePicker timePicker;
    int mHour,mMin;
    Button setAlarms, searchAlarms, viewAlarms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarms, container, false);
        setAlarms = (Button)view.findViewById(R.id.searchTimeTable);
        viewAlarms = (Button)view.findViewById(R.id.createTimeTable);
        setAlarms.setOnClickListener(this);
        viewAlarms.setOnClickListener(this);

/**
 findViewById(R.id.setAlarmButton).setOnClickListener(new View.OnClickListener() {
@Override public void onClick(View view) {
Calendar calendar = Calendar.getInstance();

if(Build.VERSION.SDK_INT >= 23){
calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
timePicker.getHour(), timePicker.getMinute(), 0);
}else{
calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
}
setTimer(calendar.getTimeInMillis());
}
}); **/

        /** public void setTimer(long time){

         AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

         Date date = new Date();

         Calendar cal_alarm = Calendar.getInstance();
         Calendar cal_now = Calendar.getInstance();

         cal_now.setTime(date);
         cal_alarm.setTime(date);

         cal_alarm.set(Calendar.HOUR_OF_DAY,mHour);
         cal_alarm.set(Calendar.MINUTE,mMin);
         cal_alarm.set(Calendar.SECOND,0);

         if(cal_alarm.before(cal_now)){
         cal_alarm.add(Calendar.DATE,1);
         }

         Intent intent = new Intent(MainActivity.this,AppBroadcasrReceiver.class);
         PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,2444,intent,0);
         alarmManager.set(AlarmManager.RTC_WAKEUP,cal_alarm.getTimeInMillis(),pendingIntent);
         }
         **/
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle("Alarms");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.searchTimeTable:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddAlarm()).addToBackStack(null).commit();
                break;
            case R.id.createTimeTable:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SavedAlarms()).addToBackStack(null).commit();
                break;
        }
    }
}
