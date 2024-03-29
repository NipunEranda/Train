package com.train.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    static Button disableBtn;
    Button btn;

    public static void disableBtn(Button btn){

        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#E5E4E2"));
        btn.setTextColor(Color.parseColor("#000000"));

    }

    public static void enableBtn(Button btn){

        btn.setEnabled(true);
        btn.setBackgroundColor(Color.parseColor("#357EC7"));
        btn.setTextColor(Color.parseColor("#FFFFFF"));

    }

    public static void showMessage(String title, String Message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public static String getCurrentTime(Context context) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = "Current Time : " + mdformat.format(calendar.getTime());
        return strDate;
    }

    public void confirmDialogDemo(final Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}
