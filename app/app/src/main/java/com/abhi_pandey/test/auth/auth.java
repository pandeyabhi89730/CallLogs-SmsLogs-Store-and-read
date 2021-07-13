package com.abhi_pandey.test.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abhi_pandey.test.R;
import com.abhi_pandey.test.dbHelper;
import com.bumptech.glide.load.resource.bitmap.CenterInside;

public class auth extends AppCompatActivity {
    EditText password1,password2,oldpassword;
    TextView error1;
    Button btnDelete,btnSet;
    dbHelper  dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_auth);
        btnSet=findViewById(R.id.btn_save);
        password1=findViewById(R.id.et_password_1);
        password2=findViewById(R.id.et_password_2);
        error1=findViewById(R.id.tv_err_password);
        oldpassword=findViewById(R.id.et_old_password);
        btnDelete=findViewById(R.id.delete_password);

        getSupportActionBar().setTitle("Create Password");


        dbHelper=new dbHelper(this);



        SharedPreferences sh = PreferenceManager
                .getDefaultSharedPreferences(this);

        String st= sh.getString("status", "");
        String pass = sh.getString("password", "");

        if(st.equals("1")){
            getSupportActionBar().setTitle("Change Password");
            btnSet.setText("Change Password");
            oldpassword.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }



        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEdit= sharedPreferences.edit();

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String  epassword=password1.getText().toString();
              String  cpassword=password2.getText().toString();
              String opass= oldpassword.getText().toString();

              if(oldpassword==null) {

                  if (epassword != null && cpassword != null) {

                      if (epassword.equals(cpassword)) {

                              error1.setVisibility(View.VISIBLE);
                              error1.setText("Successfully Updated Password ........!");
                              error1.setTextSize(30f);
                              error1.setGravity(50);
                              error1.setTextColor(Color.GREEN);
                              password1.setVisibility(View.GONE);
                              password2.setVisibility(View.GONE);
                              btnSet.setVisibility(View.GONE);
                              btnDelete.setVisibility(View.VISIBLE);


                              myEdit.putString("status", "1");
                              myEdit.putString("password", cpassword);

                              myEdit.apply();


                      } else {
                          error1.setVisibility(View.VISIBLE);
                          error1.setText("Your password did't matched check it .......!");
                      }
                  } else {
                      error1.setVisibility(View.VISIBLE);
                      error1.setText("Password filed  not null check It");

                  }
              }else{

                  if (!epassword.isEmpty() && !cpassword.isEmpty() && oldpassword !=null) {
                      if (pass.equals(opass)) {

                          if (epassword.equals(cpassword)) {


                                  error1.setVisibility(View.VISIBLE);
                                  error1.setText("Successfully Updated Password ........!");
                                  error1.setTextSize(30f);
                                  error1.setGravity(50);
                                  error1.setTextColor(Color.GREEN);
                                  password1.setVisibility(View.GONE);
                                  password2.setVisibility(View.GONE);
                                  btnSet.setVisibility(View.GONE);
                                  btnDelete.setVisibility(View.VISIBLE);
                                  oldpassword.setVisibility(View.GONE);

                                  myEdit.putString("status", "1");
                                  myEdit.putString("password", cpassword);

                                  myEdit.apply();


                          } else {
                              error1.setVisibility(View.VISIBLE);
                              error1.setText("Enter same password check it .......!");
                          }
                      } else {

                          error1.setVisibility(View.VISIBLE);
                          error1.setText("Your old password did't matched check it .......!"+opass);



                      }


                  }else{
                      error1.setText("Password filed not null check It");
                      error1.setVisibility(View.VISIBLE);
                      error1.setTextSize(20f);


                  }
              }


            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myEdit.putString("status","2");
                myEdit.putString("password","");

                myEdit.apply();

                error1.setVisibility(View.VISIBLE);
                error1.setText("Password Deleted Successfully  .......!");
                error1.setTextSize(15f);

            }
        });





    }
}














