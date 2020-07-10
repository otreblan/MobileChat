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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        this.id = getIntent().getExtras().getInt("id");
        String username = getIntent().getExtras().getString("username");

        setTitle(username);

        recyclerView = findViewById(R.id.main_recycler_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUsers();
    }

    public Activity getActivity() {
        return this;
    }

    public void getUsers() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "https://otreblan.ddns.net/users",
                new JSONArray(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        adapter = new ContactsAdapter(response, getActivity(), id);
                        recyclerView.setAdapter(adapter);

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