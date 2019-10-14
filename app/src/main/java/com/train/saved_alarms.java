package com.train;

import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.widget.Toast.*;


public class saved_alarms extends AppCompatActivity {

    ArrayList<Alarms> arrayList=new ArrayList<>();
    AlarmListAdapter alarmListAdapter;
    ListView listView;
    AlarmDBHelper alarmDBHelper;
    Button settingAlarm,deleteAlarm, addAlarm;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_alarms);

        alarmDBHelper = new AlarmDBHelper(this);
        listView = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<>();
        LoadDataInListView();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                  Alarms alarms = arrayList.get(i);

                 String id = alarms.getID();
                 String name = alarms.getAlarmName();
                 String station = alarms.getStation();
                 String trainTime = alarms.getTrainTime();
                 String alarmTime = alarms.getAlarmTime();

              //   Cursor data = alarmDBHelper.getItemId(name);
               //  int itemID = -1;
               //  while (data.moveToNext()){
               //      itemID =  data.getInt(0);
               //  }
               //  if (itemID > -1){
                     Intent editScreenIntent = new Intent(saved_alarms.this,EditAlarm.class);
                     editScreenIntent.putExtra("id",id);
                     editScreenIntent.putExtra("name",name);
                     editScreenIntent.putExtra("station",station);
                     editScreenIntent.putExtra("trainTime",trainTime);
                     editScreenIntent.putExtra("alarmTime",alarmTime);
                     startActivity(editScreenIntent);
              //   }**/
               // else{
                   //Toast.makeText(saved_alarms.this,"There are no Items", LENGTH_LONG).show();
                //}
            }
        });

    }

    private void LoadDataInListView() {

        arrayList = alarmDBHelper.getAllData();
        alarmListAdapter = new AlarmListAdapter(this,arrayList);
        listView.setAdapter(alarmListAdapter);

        alarmListAdapter.notifyDataSetChanged();
    }


}
