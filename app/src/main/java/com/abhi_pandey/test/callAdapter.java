package com.abhi_pandey.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi_pandey.test.calllogsTiming.CalllInfoData;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class callAdapter extends RecyclerView.Adapter<callAdapter.myViewHolder> {

    ArrayList<callLogModel> dataholder;
    private  dbHelper dbHandler;
    private Context mcontext;


    public callAdapter(ArrayList<callLogModel> dataholder, Context mcontext) {
        this.dataholder = dataholder;
        this.mcontext=mcontext;
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
              LayoutInflater from = LayoutInflater.from(parent.getContext());
            return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_cardview_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull callAdapter.myViewHolder holder, int position) {

        holder.callName.setText(dataholder.get(position).getCallName());
        holder.phoneNumber.setText(dataholder.get(position).getCallNumber());
        holder.callType.setText(dataholder.get(position).getCallType());
        holder.callStartTime.setText(dataholder.get(position).getCallStartTime());
        holder.callEndTime.setText(dataholder.get(position).getCallEndTime());
        holder.callDuration.setText(dataholder.get(position).getCallDuration());
        holder.callDate.setText(dataholder.get(position).getCallDate());




        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), CalllInfoData.class);
                intent.putExtra("call_number", dataholder.get(position).getCallNumber());
                v.getContext().startActivity(intent);


            }
        });


        holder.callDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=dataholder.get(position).getCallName();
                dbHandler = new dbHelper(v.getContext());
                Boolean t= dbHandler.onCallDelete(dataholder.get(position).getCallNumber());

                if(t==true) {
                    int id=position;
                    if (id > -1 && id < dataholder.size()) {
                        notifyItemRangeChanged(position, getItemCount());
                        dataholder.remove(id);
                        notifyItemRemoved(position);
                        Toast.makeText(v.getContext(), "Successfully Deleted "+name,Toast.LENGTH_SHORT).show();



                    }

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        public TextView callDate,callDuration,callName,callType, phoneNumber,callStartTime,callEndTime;
        public CardView cvItem;
        public ImageView callDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            this.callDelete = (ImageView) itemView.findViewById(R.id.callDelete);
            this.cvItem = (CardView) itemView.findViewById(R.id.cardViewCall);
            this.callName = (TextView) itemView.findViewById(R.id.callName);
            this.phoneNumber=(TextView)itemView.findViewById(R.id.phoneNumber);
            this.callType=(TextView) itemView.findViewById(R.id.callType);
            this.callStartTime=(TextView) itemView.findViewById(R.id.callStartTime);
            this.callEndTime=(TextView) itemView.findViewById(R.id.callEndTime);
            this.callDuration = (TextView) itemView.findViewById(R.id.callDurationSeconds);
            this.callDate = (TextView) itemView.findViewById(R.id.callDate);

        }
    }

}
