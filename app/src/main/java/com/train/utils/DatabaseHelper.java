package com.train.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextThemeWrapper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Train.db";

    /*TRAIN_TIMETABLE DETAILS*/

    public static final String TABLE_TIMETABLE = "TIMETABLE";
    public static final String TIMETABLE_ID = "TABLE_ID";
    public static final String TIMETABLE_NAME = "TIMETABLE_NAME";
    public static final String START_STATION = "START_STATION";
    public static final String END_STATION = "END_STATION";
    public static final String ARRIVAL_TIME = "ARRIVAL_TIME";
    public static final String DEPART_TIME = "DEPART_TIME";
    public static final String TIMETABLE_DATE = "TIMETABLE_DATE";
    public static final String TIMETABLE_TRAIN_ID = "TRAIN_ID";
    /**/

    /*TRAIN DETAILS*/
    public static final String TABLE_TRAIN = "TRAIN";
    public static final String TRAIN_ID = "TRAIN_ID";
    public static final String TRAIN_NAME = "TRAIN_NAME";
    /**/

    /*STATION DETAILS*/
    public static final String TABLE_STATION = "STATION";
    public static final String STATION_ID = "STATION_ID";
    public static final String STATION_NAME = "STATION_NAME";
    /**/

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TRAIN + "(TRAIN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TRAIN_NAME TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_STATION + "(STATION_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "STATION_NAME TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_TIMETABLE + "(TIMETABLE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TIMETABLE_NAME TEXT, START_STATION INTEGER, END_STATION INTEGER, ARRIVAL_TIME TEXT, DEPART_TIME TEXT," +
                " TIMETABLE_DATE TEXT, TRAIN_ID INTEGER, FOREIGN KEY(TRAIN_ID) REFERENCES TRAIN(TRAIN_ID)," +
                " FOREIGN KEY(START_STATION) REFERENCES STATION(STATION_ID), FOREIGN KEY(END_STATION) REFERENCES STATION(STATION_ID))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION);
        onCreate(db);
    }

    public boolean insertATimeTable(String timeTableName, int startStation, int endStation, String arrivalTime, String departTime, String date, int trainId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMETABLE_NAME, timeTableName);
        contentValues.put(START_STATION, startStation);
        contentValues.put(END_STATION, endStation);
        contentValues.put(ARRIVAL_TIME, arrivalTime);
        contentValues.put(DEPART_TIME, departTime);
        contentValues.put(TIMETABLE_DATE, date);
        contentValues.put(TIMETABLE_TRAIN_ID, trainId);
        long result = db.insert(TABLE_TIMETABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllTimeTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TIMETABLE, null);
        return res;
    }
}
