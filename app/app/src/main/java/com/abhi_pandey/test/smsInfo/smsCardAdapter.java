package com.abhi_pandey.test.smsInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhi_pandey.test.R;
import com.abhi_pandey.test.smsInfo.MessageChatModel;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public  class smsCardAdapter extends RecyclerView.Adapter {

    List<MessageChatModel> messageChatModelList;
    Context context;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_NA = 2;


    public smsCardAdapter(List<MessageChatModel> messageChatModelList, Context context) {
        this.messageChatModelList = messageChatModelList;
        this.context = context;
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        MessageChatModel message = (MessageChatModel) messageChatModelList.get(position);
        if (message.getViewType() == 0) {
            // If the current user is the sender of the message
            Log.e("getItemViewType", "0");
            return VIEW_TYPE_MESSAGE_SENT;
        }else if (message.getViewType() == 1) {
            // If the current user is the sender of the message
            Log.e("getItemViewType", "1");
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else {
            // If some other user sent the message
            Log.e("getItemViewType", "2");
            return VIEW_TYPE_MESSAGE_NA;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_outgoing, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_incoming, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MessageChatModel message = messageChatModelList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageChatModelList.size();
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView time;


        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.tvBody);
            time = (TextView) itemView.findViewById(R.id.tvtime);



        }

        void bind(MessageChatModel messageModel) {
            message.setText(messageModel.getText());
            time.setText(messageModel.getTime());

        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView time;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.tvBody);
            time = (TextView) itemView.findViewById(R.id.tvName);

        }

        void bind(MessageChatModel messageModel) {
            message.setText(messageModel.getText());
            time.setText(messageModel.getTime());
        }
    }
}

