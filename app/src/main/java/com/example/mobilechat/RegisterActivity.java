package com.example.mobilechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public RegisterActivity getThis() {
        return this;
    }

    public void onRegisterClicked(View view) {
        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);

        EditText username = (EditText)findViewById(R.id.r_username);
        EditText name = (EditText)findViewById(R.id.r_name);
        EditText fullname = (EditText)findViewById(R.id.r_fullname);
        EditText password = (EditText)findViewById(R.id.r_password);

        String username_str = username.getText().toString();
        String name_str = name.getText().toString();
        String fullname_str = fullname.getText().toString();
        String password_str = password.getText().toString();

        Map<String, String> message = new HashMap<String, String>();

        message.put("username", username_str);
        message.put("name", name_str);
        message.put("fullname", fullname_str);
        message.put("password", password_str);

        JSONObject jsonMessage = new JSONObject(message);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                // 10.0.2.2
                "https://otreblan.ddns.net/users",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //showMessage("Ok");
                        getThis().finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RegisterActivity","No");
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}