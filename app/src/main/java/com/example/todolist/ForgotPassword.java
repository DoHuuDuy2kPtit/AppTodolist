package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.User;
import com.example.todolist.response.MessageRes;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class ForgotPassword extends AppCompatActivity {
    private TextView username, password;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPass();
            }
        });
    }
    private void forgotPass(){
        String user, pass;
        user = username.getText().toString();
        pass = password.getText().toString();
        if(user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Xin điền đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        User man = new User(user, pass);
        ApiService.apiService.forgotPassword(man).enqueue(new Callback<MessageRes>() {
            @Override
            public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Đã tải lại mật khẩu "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(ForgotPassword.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageRes> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(ForgotPassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    private  void init(){
        username = findViewById(R.id.forgotUsername);
        password = findViewById(R.id.forgotPW);
        btn = findViewById(R.id.btnAcceptForgot);
    }

}