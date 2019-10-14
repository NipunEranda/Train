package com.train.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.IntegerRes;

import java.util.ArrayList;
import java.util.Calendar;

public class getDateTime {

    static int mYear, mMonth, mDay;
    static TextView output;
    static int mHour, mMinute;
    static String month, hour, Minute;
    static int dateArray[] = new int[3];

    public static int[] getDateView(Context context, final TextView date){

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        output = date;

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        if((monthOfYear + 1) == 1){
                            month = "January";
                        }else if((monthOfYear + 1) == 2) {
                            month = "February";
                        }else if((monthOfYear + 1) == 3){
                            month = "March";
                        }else if((monthOfYear + 1) == 4){
                            month = "April";
                        }else if((monthOfYear + 1) == 5){
                            month = "May";
                        }else if((monthOfYear + 1) == 6){
                            month = "June";
                        }else if((monthOfYear + 1) == 7){
                            month = "July";
                        }else if((monthOfYear + 1) == 8){
                            month = "August";
                        }else if((monthOfYear + 1) == 9){
                            month = "September";
                        }else if((monthOfYear + 1) == 10){
                            month = "October";
                        }else if((monthOfYear + 1) == 11){
                            month = "November";
                        }else if((monthOfYear + 1) == 12){
                            month = "Decembter";
                        }

                        output.setText(dayOfMonth + "-" + (month) + "-" + year);
                        dateArray[0] = dayOfMonth;
                        dateArray[1] = (monthOfYear + 1);
                        dateArray[2] = year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

        return dateArray;
    }


    public static void getTimeView(final Context context, TextView time){
        Calendar endT = Calendar.getInstance();
        mHour = endT.get(Calendar.HOUR_OF_DAY);
        mMinute = endT.get(Calendar.MINUTE);
        output = time;

        // Launch Time Picker Dialog
        TimePickerDialog endTimePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        if(hourOfDay < 10){
                            if(hourOfDay == 0){
                                hour = "00";
                            }else if(hourOfDay == 1){
                                hour = "01";
                            }else if(hourOfDay == 2){
                                hour = "02";
                            }else if(hourOfDay == 3){
                                hour = "03";
                            }else if(hourOfDay == 4){
                                hour = "04";
                            }else if(hourOfDay == 5){
                                hour = "05";
                            }else if(hourOfDay == 6){
                                hour = "06";
                            }else if(hourOfDay == 7){
                                hour = "07";
                            }else if(hourOfDay == 8){
                                hour = "08";
                            }else if(hourOfDay == 9){
                                hour = "09";
                            }
                        }else{
                            hour = String.valueOf(hourOfDay);
                        }

                        if(minute < 10){
                            if(minute == 0)
                                Minute = "00";
                            else if(minute == 1)
                                Minute = "01";
                            else if(minute == 2)
                                Minute = "02";
                            else if(minute == 3)
                                Minute = "03";
                            else if(minute == 4)
                                Minute = "04";
                            else if(minute == 5)
                                Minute = "05";
                            else if(minute == 6)
                                Minute = "06";
                            else if(minute == 7)
                                Minute = "07";
                            else if(minute == 8)
                                Minute = "08";
                            else if(minute == 9)
                                Minute = "09";

                        }else{
                            Minute = String.valueOf(minute);
                        }

                        output.setText(hour + ":" + Minute);
                    }
                }, mHour, mMinute, false);
        endTimePickerDialog.show();
    }

}
