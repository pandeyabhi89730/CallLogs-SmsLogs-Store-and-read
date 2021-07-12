package com.abhi_pandey.test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StartActivityOnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent activityIntent = new Intent(context, MainActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);

//            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            PendingIntent pi = PendingIntent.getService(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//            long interval=1000;
//            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, interval, pi);
//
//
//            //Toast.makeText(context, "Start app Data", Toast.LENGTH_LONG).show();

        }
    }
}

