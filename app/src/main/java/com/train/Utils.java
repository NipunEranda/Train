package com.train;

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

}
