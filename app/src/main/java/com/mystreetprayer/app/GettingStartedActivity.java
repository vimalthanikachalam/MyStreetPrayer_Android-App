package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class GettingStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        Button registerView = (Button) findViewById(R.id.gs_New_User);
        Button loginView = (Button) findViewById(R.id.gs_Login);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(GettingStartedActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(GettingStartedActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}
