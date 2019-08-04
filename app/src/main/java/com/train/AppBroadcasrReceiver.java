package com.train;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
@TargetApi(16)
public class AppBroadcasrReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){

/**

        Notification notification = new Notification.Builder(context)
                .setContentTitle("Alarm is ON")
                .setContentText("You had set up the alarm")
                .setSmallIcon(R.mipmap.ic_launcher).build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags|=Notification.FLAG_AUTO_CANCEL;
        manager.notify(0,notification);


        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        Uri notifi = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Ringtone r = RingtoneManager.getRingtone(context,notifi);
        r.play(); **/



    }
}
