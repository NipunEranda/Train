package com.train.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.Button;

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

}
