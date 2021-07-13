package com.abhi_pandey.test;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SmsLogAdapter extends RecyclerView.Adapter<SmsLogAdapter.ItemViewHolder> {

    ArrayList<smsModel> smsholder;
    private dbHelper dbHandler;
    private Context context;

    public SmsLogAdapter(ArrayList<smsModel> smsholder, Context context) {
        this.smsholder = smsholder;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater from = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SmsLogAdapter.ItemViewHolder holder, int position) {
        holder.senderName.setText(smsholder.get(position).getSmsName());
        holder.senderNumber.setText(smsholder.get(position).getSmsAddress());
        holder.senderDate.setText(smsholder.get(position).getSmsDatee()+"("+smsholder.get(position).getSmsTime()+")");
        holder.senderTime.setText(smsholder.get(position).getSmsTime());
        holder.bodySms.setText(smsholder.get(position).getSmsContent());
        holder.snderType.setText(smsholder.get(position).getSmsType());

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),smsLogDetails.class);
                intent.putExtra("sms_number", smsholder.get(position).getSmsAddress());
                v.getContext().startActivity(intent);


            }
        });
        holder.smsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=smsholder.get(position).getSmsName();
                 dbHandler = new dbHelper(v.getContext());
                Boolean t= dbHandler.onSmsDelete(smsholder.get(position).getSmsAddress());

                if(t==true) {
                        int id=position;
                    if (id > -1 && id < smsholder.size()) {
                        notifyItemRangeChanged(position, getItemCount());
                        smsholder.remove(id);
                        notifyItemRemoved(position);

                        Toast.makeText(v.getContext(), "Successfully Deleted "+name,Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return smsholder.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

            public CardView cvItem;
        public TextView bodySms,senderDate,senderName,senderNumber,senderTime,snderType;
        public ImageView smsDelete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.smsDelete=(ImageView) itemView.findViewById(R.id.smsdelete);
            this.cvItem = (CardView) itemView.findViewById(R.id.cvItem);
            this.senderName = (TextView) itemView.findViewById(R.id.senderName);
            this.senderNumber = (TextView) itemView.findViewById(R.id.senderNumber);
            this.snderType = (TextView) itemView.findViewById(R.id.snderType);
            this.senderDate = (TextView) itemView.findViewById(R.id.smsDate);
            this.senderTime = (TextView) itemView.findViewById(R.id.smsTime);
            this.bodySms = (TextView) itemView.findViewById(R.id.smsContent);

        }
    }



}