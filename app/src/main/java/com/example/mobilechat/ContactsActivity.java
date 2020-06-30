package com.example.mobilechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        int id = getIntent().getExtras().getInt("id");
        String username = getIntent().getExtras().getString("username");

        setTitle(username);
    }
}