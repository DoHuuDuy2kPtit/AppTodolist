package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.Password;
import com.example.todolist.response.MessageRes;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    private EditText txtOldPassword, txtNewPassword, txtComfirmNewPassword;
    private Button btnAccept;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent i = getIntent();
        token = i.getStringExtra("accessToken");
        Log.e(null, "token: "+ token);
        init();
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });

    }
    private void changePass(){
        String oldPass = txtOldPassword.getText().toString();
        String newPass = txtNewPassword.getText().toString();
        String confirmNewPass = txtComfirmNewPassword.getText().toString();
        if (oldPass.isEmpty() || newPass.isEmpty() || confirmNewPass.isEmpty()) {
            Toast.makeText(ChangePassword.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(confirmNewPass)) {
            Toast.makeText(ChangePassword.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
            return;
        }
        Password pass = new Password(oldPass, newPass);
        ApiService.apiService.changePassword("Bearer "+token, pass).enqueue(new Callback<MessageRes>() {
            @Override
            public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChangePassword.this, "Thay đổi mật khẩu "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ChangePassword.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageRes> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(ChangePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    private void init(){
        txtOldPassword = findViewById(R.id.editTextOldPassword);
        txtNewPassword = findViewById(R.id.editTextNewPassword);
        txtComfirmNewPassword = findViewById(R.id.editTextComfirmNewPassword);
        btnAccept = findViewById(R.id.btnAccept);
    }
}