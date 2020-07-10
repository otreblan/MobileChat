package com.example.mobilechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public JSONArray messages;
    public Context context;
    public int logedUserId;

    public MessageAdapter(JSONArray messages, Context context, int logedUserId) {
        this.messages = messages;
        this.context = context;
        this.logedUserId = logedUserId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final JSONObject message = messages.getJSONObject(position);
            String content = message.getString("content");

            holder.myLine.setText(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return messages.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView friendLine;
        TextView myLine;
        RelativeLayout container;
        public ViewHolder(View itemView) {
            super(itemView);

            this.friendLine = itemView.findViewById(R.id.message_view_friend_line);
            this.myLine = itemView.findViewById(R.id.message_view_my_line);
            this.container = itemView.findViewById(R.id.message_view_container);
        }
    }
}
