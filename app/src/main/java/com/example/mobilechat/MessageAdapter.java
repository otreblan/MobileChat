package com.example.mobilechat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public JSONArray messages;
    public Context context;
    public int loggedUserId;

    public MessageAdapter(JSONArray messages, Context context, int loggedUserId) {
        this.messages = messages;
        this.context = context;
        this.loggedUserId = loggedUserId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Log.e("MessageAdapter", ""+position);
            final JSONObject message = messages.getJSONObject(position);
            String content = message.getString("content");
            int whoSent = message.getInt("user_from_id");
            if(whoSent == loggedUserId) {
                holder.myLine.setText(content);
            } else {
                holder.friendLine.setText(content);
            }
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
