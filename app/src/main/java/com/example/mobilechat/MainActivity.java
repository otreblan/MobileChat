package com.example.mobilechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void goToContactsActivity(String username, int id) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void onLoginClicked(View view) {
        EditText username = (EditText)findViewById(R.id.username);
        EditText password = (EditText)findViewById(R.id.password);

        String username_str = username.getText().toString();
        String password_str = password.getText().toString();

        Map<String, String> message = new HashMap<String, String>();

        message.put("username", username_str);
        message.put("password", password_str);

        JSONObject jsonMessage = new JSONObject(message);

        //showMessage(jsonMessage.toString());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                // 10.0.2.2
                "https://baroque-bastille-54781.herokuapp.com/authenticate",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //showMessage("Ok");
                        showMessage(response.toString());

                        String username = null;
                        try {
                            username = response.getString("username");
                            int id = response.getInt("id");

                            // Opening ContactsActivity
                            goToContactsActivity(username, id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showMessage("No");
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void onRegisterClicked(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}