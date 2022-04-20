package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EntryApp extends AppCompatActivity {
    Button btnToSignIn, btnToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entryapp);

        btnToSignIn = (Button) findViewById(R.id.btnToSignIn);
        btnToSignUp = (Button) findViewById(R.id.btnToSignUp);

        btnToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(EntryApp.this, Login.class);
                startActivity(intentLogin);
            }
        });

        btnToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(EntryApp.this, Signup.class);
                startActivity(intentSignUp);
            }
        });
    }


}