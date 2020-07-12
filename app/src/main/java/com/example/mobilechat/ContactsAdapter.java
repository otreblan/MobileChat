package com.example.mobilechat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
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

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    public JSONArray users;
    private Context context;
    private int userFromId;

    public ContactsAdapter(JSONArray users, Context context, int userFromId) {
        this.users = users;
        this.context = context;
        this.userFromId = userFromId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Log.e("ContactsAdapter", ""+position);
            final JSONObject user = users.getJSONObject(position);
            String name = user.getString("name") + " " + user.getString("fullname");
            final String username = user.getString("username");

            holder.first_line.setText(name);
            holder.second_line.setText(username);
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        goToChatActivity(userFromId, user.getInt("id"), username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return users.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView first_line;
        TextView second_line;
        RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            first_line = itemView.findViewById(R.id.element_view_first_line);
            second_line = itemView.findViewById(R.id.element_view_second_line);

            container = itemView.findViewById(R.id.element_container);
        }
    }

    public void goToChatActivity(int userFromId, int userToId, String username) {
        Intent intent = new Intent(this.context, ChatActivity.class);

        // Int
        intent.putExtra("userFromId", userFromId);
        intent.putExtra("userToId", userToId);

        // String
        intent.putExtra("username", username);

        this.context.startActivity(intent);
    }
}
