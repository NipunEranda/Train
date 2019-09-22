package com.train;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditAlarm extends AppCompatActivity {

    private TextView trainTime,trainStation;
    private EditText alarmName;
    private Button updateAlarm;
    private Button deleteAlarm;
    private TimePicker timePicker;

    AlarmDBHelper alarmDBHelper;

    private String selectedName,selectedTrainTime,selectedTrainStation,selectedAlarmTime;
    private String selectedId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);

        updateAlarm = (Button) findViewById(R.id.setAlarmButton);
        deleteAlarm = (Button) findViewById(R.id.cancelAlarm);

        alarmName = (EditText) findViewById(R.id.alarmName);
        trainTime = (TextView) findViewById(R.id.trainTime);
        trainStation = (TextView) findViewById(R.id.trainStation);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        alarmDBHelper = new AlarmDBHelper(this);

       Intent receiveIntent = getIntent();

        selectedId = receiveIntent.getStringExtra("id");
        final int intSelectedId = Integer.parseInt(selectedId);

        selectedName = receiveIntent.getStringExtra("name");
        selectedTrainStation = receiveIntent.getStringExtra("station");
        selectedTrainTime = receiveIntent.getStringExtra("trainTime");
        selectedAlarmTime = receiveIntent.getStringExtra("alarmTime");

        alarmName.setText(selectedName);
        trainTime.setText(selectedTrainTime);
        trainStation.setText(selectedTrainStation);

        updateAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = alarmName.getText().toString();
                boolean up = alarmDBHelper.updateData(name,timePicker.getCurrentHour() + ":" +timePicker.getCurrentMinute(),selectedId);

                if(up == true){
                    Toast.makeText(EditAlarm.this, "Alarm Edited", Toast.LENGTH_SHORT).show();
                    Intent intentedit = new Intent(EditAlarm.this, saved_alarms.class);
                    startActivity(intentedit);
                }
                else{
                    Toast.makeText(EditAlarm.this, "Alarm not Edited", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmDBHelper.deleteData(intSelectedId);
                Toast.makeText(EditAlarm.this, "Alarm Deleted", Toast.LENGTH_LONG).show();
                Intent intent= new Intent(EditAlarm.this,saved_alarms.class);
                startActivity(intent);
            }
        });

    }

    
}
