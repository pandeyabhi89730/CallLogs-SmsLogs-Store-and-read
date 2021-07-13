package com.abhi_pandey.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.abhi_pandey.test.smsInfo.MessageChatModel;
import com.abhi_pandey.test.smsInfo.smsCardAdapter;

import java.util.ArrayList;
import java.util.List;

public class smsLogDetails extends AppCompatActivity {

    private dbHelper dbHandler;
    private List<smsModel> dataholder= new ArrayList<>();
    private RecyclerView recyclerView;
    List<MessageChatModel> messageChatModelList =  new ArrayList<>();
     smsCardAdapter adapter ;
     private  String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_log_details);
        recyclerView=findViewById(R.id.rvChat);
        dbHandler=new dbHelper(this);

        if (getIntent().getExtras() != null) {
            value = getIntent().getStringExtra("sms_number");
            LinearLayoutManager manager = new LinearLayoutManager(smsLogDetails.this, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(manager);


            Cursor cursor = dbHandler.redinfosms(value);
            while (cursor.moveToNext()) {

                if(cursor.getString(3).equals("inbox")){

                    String dateTime= cursor.getString(6)+"("+ cursor.getString(5)+")";

                    MessageChatModel data = new MessageChatModel(
                            cursor.getString(4),
                            dateTime,
                            1
                    );
                    messageChatModelList.add(data);

                }else if(cursor.getString(3).equals("sent")){

                    String dateTime= cursor.getString(6)+"("+ cursor.getString(5)+")";


                    MessageChatModel data = new MessageChatModel(
                            cursor.getString(4),
                           dateTime,
                            0
                    );
                    messageChatModelList.add(data);

                }else{

                    String dateTime= cursor.getString(6)+"("+ cursor.getString(5)+")";


                    MessageChatModel data = new MessageChatModel(
                            cursor.getString(4),
                           dateTime,
                            2
                    );
                    messageChatModelList.add(data);


                }




            }
            cursor.close();



            recyclerView.smoothScrollToPosition(messageChatModelList.size());
            adapter = new smsCardAdapter(messageChatModelList, smsLogDetails.this);
            getSupportActionBar().setTitle(value);
            recyclerView.setAdapter(adapter);


        }

    }
}