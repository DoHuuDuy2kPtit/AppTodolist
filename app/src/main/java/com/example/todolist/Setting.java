package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {
    private ImageButton imageBtnChangePasswd, imageBtnEditInfo, imageBtnAbout, imageBtnSec, imageBtnLogout;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
        Intent i = getIntent();
        token = i.getStringExtra("accessToken");
        Log.e("","token:"+ token);

        imageBtnChangePasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, ChangePassword.class);
                intent.putExtra("accessToken", token);
                startActivity(intent);
            }
        });

        imageBtnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, UserInfo.class);
                intent.putExtra("accessToken", token);
                startActivity(intent);
            }
        });

//        imageBtnAbout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Setting.this, AboutUs.class);
//                startActivity(intent);
//            }
//        });

        imageBtnSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shared = getSharedPreferences("cookie", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.remove("accessToken");
                editor.apply();
                Intent intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
            }
        });

    }
    private void init(){
        imageBtnChangePasswd = findViewById(R.id.imageBtnChangePasswd);
        imageBtnEditInfo     = findViewById(R.id.imageBtnEditInfo);
        imageBtnAbout             = findViewById(R.id.imageBtnAbout);
        imageBtnSec  = findViewById(R.id.imageBtnSec);
        imageBtnLogout = findViewById(R.id.imageButtonLogOut);
    }
}