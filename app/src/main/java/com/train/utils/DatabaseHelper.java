package com.train.utils;

import android.animation.IntArrayEvaluator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.train.R;
import com.train.SearchTimeTable;
import com.train.Train;
import com.train.TrainTimeTable;

import java.sql.SQLTransactionRollbackException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context appContext;
    public static final String DATABASE_NAME = "Train.db";

    /*TRAIN_TIMETABLE DETAILS*/

    public static final String TABLE_TIMETABLE = "TIMETABLE";
    public static final String TIMETABLE_ID = "TIMETABLE_ID";
    public static final String TIMETABLE_NAME = "TIMETABLE_NAME";
    public static final String START_STATION = "START_STATION";
    public static final String END_STATION = "END_STATION";
    public static final String ARRIVAL_TIME = "ARRIVAL_TIME";
    public static final String DEPART_TIME = "DEPART_TIME";
    public static final String TIMETABLE_DATE = "TIMETABLE_DATE";
    public static final String TIMETABLE_TRAIN_ID = "TRAIN_ID";
    public static final String TIMETABLE_IS_DEFAULT = "IS_DEFAULT";

    String startStation;
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

    /*RecentSearches*/
    public static final String TABLE_RECENT_TABLES = "RECENT_TABLES";
    public static final String RECENT_TABLEID = "RECENT_TABLEID";
    public static final String RECENT_RELEVENT_TABLEID = "RECENT_RELEVENT_TABLEID";


    /*AlarmTable*/
    public static final String TABLE_ALARM = "alarm_table1";
    public static final String COL_1 = "id";
    public static final String COL_2 = "ALARM_NAME";
    public static final String COL_3 = "ALARM_TIME";
    public static final String COL_4 = "TRAIN_TIME";
    public static final String COL_5 = "STATION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TRAIN + "(TRAIN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TRAIN_NAME TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_STATION + "(STATION_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "STATION_NAME TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_TIMETABLE + "(TIMETABLE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TIMETABLE_NAME TEXT, START_STATION INTEGER, END_STATION INTEGER, ARRIVAL_TIME TEXT, DEPART_TIME TEXT," +
                " TIMETABLE_DATE TEXT, TRAIN_ID INTEGER, IS_DEFAULT INTEGER, FOREIGN KEY(TRAIN_ID) REFERENCES TRAIN(TRAIN_ID)," +
                " FOREIGN KEY(START_STATION) REFERENCES STATION(STATION_ID), FOREIGN KEY(END_STATION) REFERENCES STATION(STATION_ID))");

        db.execSQL("CREATE TABLE " + TABLE_RECENT_TABLES + "(RECENT_TABLEID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "RECENT_RELEVENT_TABLEID INTEGER, FOREIGN KEY(RECENT_RELEVENT_TABLEID) REFERENCES TIMETABLE(TABLE_ID))");

        String Alarm_table_Q =
                "CREATE TABLE " + TABLE_ALARM + "("
                        + COL_1 + " INTEGER PRIMARY KEY ,"+
                        COL_2 + " TEXT ,"+
                        COL_3 + " TEXT ,"+
                        COL_4 + " TEXT ,"+
                        COL_5 + " TEXT " + ");";

        db.execSQL(Alarm_table_Q);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
        onCreate(db);
    }

    public boolean insertAnAlarm(String aName,String aTime,String tTime,String tStation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,aName);
        contentValues.put(COL_3,aTime);
        contentValues.put(COL_4,tTime);
        contentValues.put(COL_5,tStation);


        long result = db.insert(TABLE_ALARM,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean insertATimeTable(String timeTableName, int startStation, int endStation, String arrivalTime, String departTime, String date, int trainId, int isDefault){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMETABLE_NAME, timeTableName);
        contentValues.put(START_STATION, startStation);
        contentValues.put(END_STATION, endStation);
        contentValues.put(ARRIVAL_TIME, arrivalTime);
        contentValues.put(DEPART_TIME, departTime);
        contentValues.put(TIMETABLE_DATE, date);
        contentValues.put(TIMETABLE_TRAIN_ID, trainId);
        contentValues.put(TIMETABLE_IS_DEFAULT, isDefault);
        long result = db.insert(TABLE_TIMETABLE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean editTimeTable(String id, String name, int startStation, int endStation, String arrivaTime, String departTime, String date, int trainId, int isDefault){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMETABLE_NAME, name);
        contentValues.put(START_STATION, startStation);
        contentValues.put(END_STATION, endStation);
        contentValues.put(ARRIVAL_TIME, arrivaTime);
        contentValues.put(DEPART_TIME, departTime);
        contentValues.put(TIMETABLE_DATE, date);
        contentValues.put(TIMETABLE_TRAIN_ID, trainId);
        contentValues.put(TIMETABLE_IS_DEFAULT, isDefault);
        int res = db.update(TABLE_TIMETABLE, contentValues, "TIMETABLE_ID = ?", new String[] {id});
        if(res < 0){
            return false;
        }else{
            return true;
        }
    }

    public int deleteTimeTable(String tableId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TIMETABLE, "TIMETABLE_ID = ?", new String[] {tableId});
    }

    public boolean insertRecentTable(int tableId){
        boolean check = checkRecentTableExist(tableId);
        if(check == true){
            return false;
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(RECENT_RELEVENT_TABLEID, tableId);
            long result = db.insert(TABLE_RECENT_TABLES, null, contentValues);
            if (result == -1)
                return false;
            else
                return true;
        }
    }

    public boolean checkRecentTableExist(int tableId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_RECENT_TABLES + " where " + RECENT_RELEVENT_TABLEID + " = " + tableId, null);
        if(res.getCount() == 0){
            return false;
        }else{
            return true;
        }

    }

    public int deleteRecentTable(String tableId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_RECENT_TABLES, "RECENT_RELEVENT_TABLEID = ?", new String[] {tableId});
    }

    public boolean deleteAllRecentTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numberOfEntriesDeleted = db.delete(TABLE_RECENT_TABLES, null, null);
        if(numberOfEntriesDeleted > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean insertAStation(String stationName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATION_NAME, stationName);
        long result = db.insert(TABLE_STATION, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertATrain(String trainName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRAIN_NAME, trainName);
        long result = db.insert(TABLE_TRAIN, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public String getStationName(int i){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_STATION + " where " + STATION_ID + " = " + i, null);
        while (res.moveToNext()) {
            if (res.getCount() == 0) {
                startStation = "";
            } else {
                startStation = res.getString(1);
            }
        }
        return startStation;
    }

    public Cursor getAllTimeTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TIMETABLE, null);
        return res;
    }

    public Cursor getATimeTable(int tableId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TIMETABLE + " where " + TIMETABLE_ID + " = " + tableId, null);
        return res;
    }

    public Cursor getAllRecentTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_RECENT_TABLES, null);
        return res;
    }

    public Cursor getAllStations(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_STATION, null);
        return res;
    }

    public Cursor getAllTrains(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TRAIN, null);
        return res;
    }

    public void setDefaultStations(){
        Cursor cursor = getAllStations();
        if(!cursor.moveToFirst()){
            String defaultStations[] = appContext.getResources().getStringArray(R.array.defaultStations);
            for(int i = 0; i < defaultStations.length; ++i){
                insertAStation(defaultStations[i]);
            }
        }
    }

    public void setDefaultTrains(){
        Cursor cursor = getAllTrains();
        if(!cursor.moveToFirst()){
            String defaultTrains[] = appContext.getResources().getStringArray(R.array.defaultTrains);
            for(int i = 0; i < defaultTrains.length; ++i){
                insertATrain(defaultTrains[i]);
            }
        }
    }

    public void setDefaultTimeTables(){
        Cursor cursor = getAllTimeTables();
        if(!cursor.moveToFirst()) {
            insertDefaultTimeTables();
        }else{
            if(deleteAllDefaultTimeTables()){
                insertDefaultTimeTables();
            }
        }
    }

    public void insertDefaultTimeTables(){
        String defaultTimeTables[] = appContext.getResources().getStringArray(R.array.defaultTimeTables);
        for (int i = 0; i < defaultTimeTables.length; ++i) {
            String details[] = defaultTimeTables[i].split(",");
            insertATimeTable(details[0], Integer.parseInt(details[1]), Integer.parseInt(details[2]), details[3], details[4], details[5], Integer.parseInt(details[6]), Integer.parseInt(details[7]));
        }
    }

    public boolean deleteAllDefaultTimeTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numberOfEntriesDeleted = db.delete(TABLE_TIMETABLE, "IS_DEFAULT = ?", new String[] {String.valueOf(1)});
        if(numberOfEntriesDeleted > 0){
            return true;
        }else{
            return false;
        }
    }

    public List<String> getAllStationLabels(){
        List<String> labels = new ArrayList<String>();
        Cursor cursor = getAllStations();

        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(0) == 1){
                    labels.add("Select a station");
                }
                    labels.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }


    public List<String> getAllTrainLabels(){
        List<String> labels = new ArrayList<String>();
        Cursor cursor = getAllTrains();

        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(0) == 1){
                    labels.add("Select a train");
                }
                labels.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return labels;
    }

    public void loadStations(Spinner spinner, String dStations[]){
        List<String> labels = getAllStationLabels();

        if(labels.isEmpty()){
            labels = getAllStationLabels();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(appContext, android.R.layout.simple_spinner_item, labels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void loadTrains(Spinner spinner, String dTrains[]){
        List<String> labels = getAllTrainLabels();

        if(labels.isEmpty()){
            labels = getAllTrainLabels();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(appContext, android.R.layout.simple_spinner_item, labels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    //Setup sql queries for searchtimetableview.java
    public Cursor timeTableSearch(int startStationID, int endStationID, boolean isNextTrainOn, boolean isDailyScheduleOn, String date, boolean isTimeFilterOn, String startTime, String endTime){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;

        if(startTime == "Set Start Time")
            startTime = "";
        if(endTime == "Set End Time")
            endTime = "";

        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals("") && isTimeFilterOn == false) {
            res = db.rawQuery("SELECT * FROM " + TABLE_TIMETABLE + " WHERE " + START_STATION + " = " + startStationID + " AND " + END_STATION + " = " + endStationID, null);
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !(date.equals("")) && isTimeFilterOn == false){
            res = db.rawQuery("SELECT * FROM " + TABLE_TIMETABLE + " WHERE " + START_STATION + " = " + startStationID + " AND " + END_STATION + " = " + endStationID + " AND " + TIMETABLE_DATE + " <= '" + date + "'", null);
            Toast.makeText(appContext, "Normal with date", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == false){
            res = db.rawQuery("SELECT * FROM " + TABLE_TIMETABLE + " WHERE " + START_STATION + " = " + startStationID + " AND " + END_STATION + " = " + endStationID + " AND " + ARRIVAL_TIME + " > " + Utils.getCurrentTime(appContext), null);
            Toast.makeText(appContext, "Finding Next Train, date is known", Toast.LENGTH_SHORT).show();
        }
        /*if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Next Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Next Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Next Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Daily Train, date is known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Daily Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Daily Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Daily Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }

        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")) {
            Toast.makeText(appContext, "Normal, start time and endtime known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true&& !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Normal with date", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, date is known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }


        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")) {
            Toast.makeText(appContext, "Normal", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true&& startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Normal with date", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, date is known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && startTime.equals("Set Start Time") && !endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }


        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")) {
            Toast.makeText(appContext, "Normal", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true&& !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Normal with date", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == true && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Next Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && !date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is known, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, endstation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train, date is unKnown, startStation known", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == true && date.equals(null) && isTimeFilterOn == true && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Daily Train,  date is unKnown", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, date is known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && !startTime.equals("Set Start Time") && endTime.equals("Set End Time")){
            Toast.makeText(appContext, "Finding Known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }



        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID != 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Known, but doesn't know startStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && !date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Known, date is known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID != 0 && endStationID == 0 && isNextTrainOn == false && isDailyScheduleOn == false && date.equals(null) && isTimeFilterOn == false){
            Toast.makeText(appContext, "Finding Known, but doesn't know endStation", Toast.LENGTH_SHORT).show();
        }
        if(startStationID == 0 && endStationID == 0){
            Toast.makeText(appContext, "Specify atleast Start station", Toast.LENGTH_SHORT).show();
        }*/

        return res;
    }
}
