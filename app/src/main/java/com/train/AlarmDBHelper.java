package com.train;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AlarmDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Train.db";
    public static final String TABLE_NAME = "alarm_table1";
    public static final String COL_1 = "id";
    public static final String COL_2 = "ALARM_NAME";
    public static final String COL_3 = "ALARM_TIME";
    public static final String COL_4 = "TRAIN_TIME";
    public static final String COL_5 = "STATION";

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Alarm_table_Q =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COL_1 + " INTEGER PRIMARY KEY ,"+
                        COL_2 + " TEXT ,"+
                        COL_3 + " TEXT ,"+
                        COL_4 + " TEXT ,"+
                        COL_5 + " TEXT " + ");";

        db.execSQL(Alarm_table_Q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String aName,String aTime,String tTime,String tStation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2,aName);
        contentValues.put(COL_3,aTime);
        contentValues.put(COL_4,tTime);
        contentValues.put(COL_5,tStation);


        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public ArrayList<Alarms> getAllData(){
        ArrayList<Alarms> arrayList= new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM alarm_table1",null);

        while (cursor.moveToNext()){
            String ID = cursor.getString(0);
            String aName = cursor.getString(1);
            String aTime= cursor.getString(2);
            String tTime = cursor.getString(3);
            String tStation  = cursor.getString(4);

            Alarms alarms = new Alarms(ID,aName,aTime,tTime,tStation);

            arrayList.add(alarms);
        }

        return arrayList;
    }


    public Cursor getItemId(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + COL_1 + "from" + TABLE_NAME +
                " where " + COL_2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean updateData(String newName,String alarmTime,String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1,id);
        contentValues.put(COL_2,newName);
        contentValues.put(COL_3,alarmTime);

        db.update(TABLE_NAME,contentValues,"id = ?",new String[] { id });
        /**  String query = "Update " + TABLE_NAME + " set " + COL_2 +
                " = '" + newName + "' AND " + COL_3 +
                " = '" + alarmTime + "' WHERE " + COL_1 + " = '" + id + "'";
        db.execSQL(query);**/
        return true;
    }

    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE From " + TABLE_NAME + " WHERE "
                + COL_1 + " = '" + id + "'";
        db.execSQL(query);
    }
}
