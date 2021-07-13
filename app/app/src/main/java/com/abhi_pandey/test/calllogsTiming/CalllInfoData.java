package com.abhi_pandey.test.calllogsTiming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.abhi_pandey.test.R;
import com.abhi_pandey.test.dbHelper;
import com.abhi_pandey.test.smsInfo.MessageChatModel;
import com.abhi_pandey.test.smsInfo.smsCardAdapter;
import com.abhi_pandey.test.smsLogDetails;

import java.util.ArrayList;

public class CalllInfoData extends AppCompatActivity {

    ArrayList<callInfoModel> callInfoModels = new ArrayList<>();
    RecyclerView recyclerView;
    dbHelper dbHandler;
    String value;
    callInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calll_info_data);

        recyclerView = findViewById(R.id.rvcallRecyler);
        dbHandler = new dbHelper(this);

        if (getIntent().getExtras() != null) {
            value = getIntent().getStringExtra("call_number");

            LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(manager);

            Cursor cursor = dbHandler.redinfocall(value);
            while (cursor.moveToNext()) {

                callInfoModel call = new callInfoModel(cursor.getString(3),  cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),cursor.getString(2));
                callInfoModels.add(call);


            }
            cursor.close();


            adapter = new callInfoAdapter(callInfoModels,getApplicationContext());
            getSupportActionBar().setTitle(value);
            recyclerView.setAdapter(adapter);


        }
    }
}