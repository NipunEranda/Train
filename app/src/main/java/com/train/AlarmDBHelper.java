package com.train;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
}
