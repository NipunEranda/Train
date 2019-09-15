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

import org.w3c.dom.Text;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private TimePicker timePicker;
    private TextView alarmTextView;

    TextView trainTime,trainStation;
    EditText alarmName;
    Button start_alarm;
    Button cancelAlarm;

    private AlarmReceiver alarm;

    AddAlarm inst;
    Context context;

    DatabaseHelper trainDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);

        this.context = this;
        trainDB = new DatabaseHelper(this);

        trainTime = (TextView) findViewById(R.id.trainTime);
        trainStation = (TextView) findViewById(R.id.trainStation);
        alarmName = (EditText) findViewById(R.id.alarmName);
        start_alarm = (Button) findViewById(R.id.setAlarmButton);
        cancelAlarm = (Button) findViewById(R.id.cancelAlarm);
        alarmTextView = (TextView) findViewById(R.id.alarmTextView);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        AddData();
    }

    public void AddData(){


        final Intent myIntent = new Intent(this.context,AlarmReceiver.class);

        //get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //set the alarm to the time that you picked
        final Calendar calendar = Calendar.getInstance();

        final String trainTimeConc = timePicker.getCurrentHour() + ":" +timePicker.getCurrentMinute();

        start_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted =  trainDB.insertAnAlarm(alarmName.getText().toString(),
                        trainTimeConc,trainTime.getText().toString(),trainStation.getText().toString());

                if(isInserted == true){
                    Toast.makeText(AddAlarm.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AddAlarm.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                }

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
        });

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
