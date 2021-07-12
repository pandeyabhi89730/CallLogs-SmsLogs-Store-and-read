package com.abhi_pandey.test.calllogsTiming;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi_pandey.test.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class callInfoAdapter extends RecyclerView.Adapter<callInfoAdapter.Viewholder> {

    ArrayList<callInfoModel> callInfoModel=new ArrayList<>();
    Context context;

    public callInfoAdapter(ArrayList<com.abhi_pandey.test.calllogsTiming.callInfoModel> callInfoModel,Context context) {
        this.callInfoModel = callInfoModel;
        this.context=context;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());

        return new callInfoAdapter.Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.call_log_details_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull callInfoAdapter.Viewholder holder, int position) {

        holder.callerDate.setText(callInfoModel.get(position).getDate()+" "+callInfoModel.get(position).getStartTime());
        holder.callerType.setText(callInfoModel.get(position).getType()+"("+callInfoModel.get(position).getDuration()+")");
        holder.endCallerTime.setText(callInfoModel.get(position).getEndTime());
        holder.callerId.setText(callInfoModel.get(position).getNumber());



    }

    @Override
    public int getItemCount() {
        return callInfoModel.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        TextView callerType,endCallerTime,callerDate,callerId;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            callerDate=itemView.findViewById(R.id.callerDate);
            callerType=itemView.findViewById(R.id.callerType);
            endCallerTime=itemView.findViewById(R.id.end_time);
            callerId=itemView.findViewById(R.id.call_number);
        }
    }

}
