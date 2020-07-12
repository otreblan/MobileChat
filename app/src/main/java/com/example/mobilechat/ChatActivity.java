package com.example.mobilechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Activity getActivity() {
        return this;
    }

    public void getMessages() {
        String url = "https://otreblan.ddns.net/messages/";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url + userFromId + "/" + userToId,
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mAdapter = new MessageAdapter(response, getActivity(), userFromId);
                        mRecyclerView.setAdapter(mAdapter);
                        Log.e("ChatActivity",response.toString());
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

    public void onClickSend(View view) {
        EditText send = (EditText)findViewById(R.id.send);

        String send_str = send.getText().toString();
        send.getText().clear();

        Map<String, String> message = new HashMap<String, String>();

        message.put("content", send_str);

        JSONObject jsonMessage = new JSONObject(message);
        try {
            jsonMessage.put("user_from_id", userFromId);
            jsonMessage.put("user_to_id", userToId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //showMessage(jsonMessage.toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                // 10.0.2.2
                "https://otreblan.ddns.net/messages",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //showMessage("Ok");
                        getMessages();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //showMessage("No");
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}