package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    EditText txtOldPassword, txtNewPassword, txtComfirmNewPassword;
    Button btnAccept;

    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SharedPreferences shared = getSharedPreferences("cookie", Context.MODE_PRIVATE);

        if (shared != null) {
            if (!shared.getString("accessToken", "").equals("")) {
                token = shared.getString("accessToken", "");
            }
        }

        txtOldPassword = (EditText) findViewById(R.id.editTextOldPassword);
        txtNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        txtComfirmNewPassword = (EditText) findViewById(R.id.editTextComfirmNewPassword);
        btnAccept = (Button) findViewById(R.id.btnAccept);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = txtOldPassword.getText().toString().trim();
                String newPass = txtNewPassword.getText().toString().trim();
                String confirmNewPass = txtComfirmNewPassword.getText().toString().trim();
                Log.i("oldPass", oldPass);
                Log.i("newPass", newPass);
                Log.i("confirmPass", confirmNewPass);
                Log.i("token", token);
                if (oldPass.isEmpty() || newPass.isEmpty() || confirmNewPass.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPass.equals(confirmNewPass)) {
                    Toast.makeText(ChangePassword.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                    return;
                }
                Password pass = new Password(oldPass, newPass);
                ApiService.apiService.changePassword("Bearer " + token, pass).enqueue(new Callback<MessageRes>() {
                    @Override
                    public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ChangePassword.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
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
        });
    }
}