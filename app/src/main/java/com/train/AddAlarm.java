package com.train;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.train.utils.DatabaseHelper;
import com.train.utils.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    AlarmManager alarmManager;
    DatabaseHelper trainDB;
    private PendingIntent pendingIntent;
    static TrainTimeTable trainTimeTable;

    private TimePicker timePicker;
    private TextView alarmTextView, time;

    TextView trainTime,trainStation;
    EditText alarmName;
    Button start_alarm;
    Button cancelAlarm;

    private AlarmReceiver alarm;

    AddAlarm inst;
    Context context;

    AlarmDBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);

        this.context = this;
        mydb = new AlarmDBHelper(this);

        final Intent myIntent = new Intent(this.context,AlarmReceiver.class);
        trainDB = new DatabaseHelper(getApplicationContext());

        trainTime = (TextView) findViewById(R.id.trainTime);
        trainStation = (TextView) findViewById(R.id.trainStation);
        alarmName = (EditText) findViewById(R.id.alarmName);
        start_alarm = (Button) findViewById(R.id.setAlarmButton);
        cancelAlarm = (Button) findViewById(R.id.cancelAlarm);
        alarmTextView = (TextView) findViewById(R.id.alarmTextView);

        trainTime.setText(trainTimeTable.getArrivalTime() + " - " + trainTimeTable.getDepartTime());
        trainStation.setText(trainDB.getStationName(trainTimeTable.getStartStation()) + " - " + trainDB.getStationName(trainTimeTable.getEndStation()));


        AddData();

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myIntent.putExtra("extra","no");
                sendBroadcast(myIntent);

                alarmManager.cancel(pendingIntent);
                setAlarmText("Alarm canceled");
            }
        });
    }

    public static void setTimeTable(TrainTimeTable timeTable){

        trainTimeTable = timeTable;

    }



    public void AddData(){

        timePicker = (TimePicker) findViewById(R.id.timePicker);

        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!alarmName.getText().toString().equalsIgnoreCase("")) {

                    boolean isInserted = mydb.insertData(alarmName.getText().toString(),
                            timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute(), trainTime.getText().toString(), trainStation.getText().toString());

                    if (isInserted == true) {
                        Toast.makeText(AddAlarm.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddAlarm.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }

                    SetAlarm();

                    Intent intent = new Intent(AddAlarm.this, saved_alarms.class);
                    startActivity(intent);
                }else{
                    Utils.showMessage("Error", "Alarm name should set", AddAlarm.this);
                }
            }
        });


    }

    public void SetAlarm(){
        final Intent myIntent = new Intent(this.context,AlarmReceiver.class);

        //get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //set the alarm to the time that you picked
        final Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.SECOND,3);

        final int hour = timePicker.getCurrentHour();
        final int minute = timePicker.getCurrentMinute();

        Log.e("MyActivity", "In the receiver with " + hour + " and " + minute);
        setAlarmText("You clicked a " + hour + " and " + minute);


        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

        myIntent.putExtra("extra", "yes");
        pendingIntent = PendingIntent.getBroadcast(AddAlarm.this,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        setAlarmText("Alarm set to " + hour + ":" + minute);


    }
    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }


    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("MyActivity", "on Destroy");
    }


}
