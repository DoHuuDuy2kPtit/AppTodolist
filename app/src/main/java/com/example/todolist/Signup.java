package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.User;
import com.example.todolist.response.MessageRes;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity {
    EditText editTextUsername, editTextEmail, editTextPw, editTextConfirmPw;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPw = (EditText) findViewById(R.id.password);
        editTextConfirmPw = (EditText) findViewById(R.id.confirmPassword);
        btnRegister = (Button) findViewById(R.id.registerBtn);
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPw.getText().toString();
                String confirmPassword = editTextConfirmPw.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(Signup.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(Signup.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                    return;
                }

                User body = new User(username, email, password, confirmPassword);

                ApiService.apiService.registerUser(body).enqueue(new Callback<MessageRes>() {
                    @Override
                    public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Signup.this, "Đăng kí "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(Signup.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageRes> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(Signup.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });


    }
}