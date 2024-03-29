package com.train;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.provider.ContactsContract;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.train.utils.DatabaseHelper;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHelper trainDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).commit();
            navigationView.setCheckedItem(R.id.nav_timeTables);
        }

        trainDB = new DatabaseHelper(getApplicationContext());
        Cursor cursor = trainDB.getAllTimeTables();
        if(!cursor.moveToFirst()){
            trainDB.setDefaultTimeTables();
        }
        Cursor cursor1 = trainDB.getAllStations();
        if(!cursor1.moveToFirst()){
            trainDB.setDefaultStations();
        }
        Cursor cursor2 = trainDB.getAllTrains();
        if(!cursor2.moveToFirst()){
            trainDB.setDefaultTrains();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AppSettings()).addToBackStack(null).commit();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_timeTables:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimeTable()).commit();
                break;

            case R.id.nav_stations:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Station()).commit();
                break;

            case R.id.nav_trains:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).commit();
                break;

            case R.id.nav_alarms:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Alarm()).commit();
               // Intent intent = new Intent(MainActivity.this,Alarm.class);
               // startActivity(intent);
                break;

            case R.id.nav_share:

                break;

            case R.id.nav_send:

                break;

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}

