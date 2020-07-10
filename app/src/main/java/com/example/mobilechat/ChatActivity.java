package com.example.mobilechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ChatActivity extends AppCompatActivity {

    private int userFromId;
    private int userToId;

    private String username;

    // Reference to the chat container
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.userFromId = getIntent().getExtras().getInt("userFromId");
        this.userToId = getIntent().getExtras().getInt("userToId");

        this.username = getIntent().getExtras().getString("username");
        this.mRecyclerView = findViewById(R.id.messages_recycler_view);

        setTitle(this.username);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMessages();
    }

    public Activity getActivity() {
        return this;
    }

    public void getMessages() {
        String url = "https://otreblan.ddns.net/messages/";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                //url + userFromId + "/" + userToId,
                url + userToId,
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mAdapter = new MessageAdapter(response, getActivity(), userFromId);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}