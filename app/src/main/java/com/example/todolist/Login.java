package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.api.ApiService;
import com.example.todolist.body.LoginBody;
import com.example.todolist.response.LoginRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText editTextUserName, editTextPW;
    TextView tvToSignUp;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUserName = (EditText) findViewById(R.id.editTextUsername);
        editTextPW = (EditText) findViewById(R.id.editTextPW);
        tvToSignUp = (TextView) findViewById(R.id.tvToSignUp);
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

                LoginBody body = new LoginBody(username, password);

                ApiService.apiService.login(body).enqueue(new Callback<LoginRes>() {
                    @Override
                    public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                        System.out.println(response);
                        System.out.println(response.errorBody());
                        LoginRes res = response.body();

                        if (res != null && !res.getAccessToken().isEmpty()) {
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent mainActivity = new Intent(Login.this, MainActivity.class);
                            startActivity(mainActivity);
                            return;
                        }

                        Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<LoginRes> call, Throwable t) {
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
    }

    private void loadAccessToken() {
        SharedPreferences shared = getSharedPreferences("tokens", Context.MODE_PRIVATE);

        if (shared != null) {

        }
    }
}