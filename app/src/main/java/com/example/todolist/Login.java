package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.User;
import com.example.todolist.response.LoginRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText editTextUserName, editTextPW;
    TextView tvToSignUp, tvForgotPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUserName = (EditText) findViewById(R.id.editTextUsername);
        editTextPW = (EditText) findViewById(R.id.editTextPW);
        tvToSignUp = (TextView) findViewById(R.id.tvToSignUp);
        tvForgotPass = (TextView) findViewById(R.id.tvForgotPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        loadAccessToken();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUserName.getText().toString();
                String password = editTextPW.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                User body = new User(username, password);

                ApiService.apiService.login(body).enqueue(new Callback<LoginRes>() {
                    @Override
                    public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                        LoginRes res = response.body();

                        if (res != null && !res.getAccessToken().isEmpty()) {
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            SharedPreferences shared = getSharedPreferences("cookie", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("accessToken", res.getAccessToken());
                            editor.commit();
                            Intent mainActivity = new Intent(Login.this, MainActivity.class);
                            startActivity(mainActivity);
                            return;
                        }
                        Log.i("", "abc");
                        Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<LoginRes> call, Throwable t) {
                        Log.i("", "xyz");
                        Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

        tvToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignUp = new Intent(Login.this, Signup.class);
                startActivity(toSignUp);
            }
        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotPass = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgotPass);
            }
        });
    }



    private void loadAccessToken() {
        SharedPreferences shared = getSharedPreferences("cookie", Context.MODE_PRIVATE);

        if (shared != null) {
            if (!shared.getString("accessToken", "").equals("")) {
                Intent mainActivity = new Intent(Login.this, MainActivity.class);
                startActivity(mainActivity);
                return;
            }
        }
    }
}