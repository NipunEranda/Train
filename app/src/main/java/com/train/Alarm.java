package com.train;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.train.AddAlarm;
import com.train.R;

public class Alarm extends Fragment {

    Button setAlarms, searchAlarms, viewAlarms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarms, container, false);

        setAlarms = (Button) view.findViewById(R.id.setAlarms);
        setAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAlarm.class);
                startActivity(intent);
            }
        });


        return view;
    }

    /** @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms);

        setAlarms = (Button) findViewById(R.id.setAlarms);
        setAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Alarm.this, AddAlarm.class);
                startActivity(intent);
            }
        });
    }

    /**
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarms, container, false);
        setAlarms = (Button)view.findViewById(R.id.setAlarms);
        viewAlarms = (Button)view.findViewById(R.id.viewAlarms);

        //viewAlarms.setOnClickListener(this);

        setAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String savedExtra = getIntent().getStringExtra("value1");
                TextView myText = (TextView) findViewById(R.id.textID);
                myText.setText(savedExtra);
                Intent intent = new Intent(this,AddAlarm.class);
                startActivity(intent);
            }
        });
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
            //case R.id.setAlarms:
                //getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddAlarm()).addToBackStack(null).commit();
                //Intent intent = new Intent(Alarm.this,AddAlarm.class);
                //startActivity(intent);
                //break;
            case R.id.viewAlarms:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListedAlarms()).addToBackStack(null).commit();
                break;
        }
    }**/
}
