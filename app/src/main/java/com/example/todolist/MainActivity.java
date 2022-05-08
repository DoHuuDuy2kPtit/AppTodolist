package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.Job;
import com.example.todolist.response.GetJobsRes;
import com.example.todolist.response.MessageRes;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private ListView listJobs;
    private AlertDialog dialog;
    private EditText titleList;
    private ImageButton btnCancel, btnSave;
    private ImageButton btnAddList, imageBtnSettings;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listJobs = (ListView) findViewById(R.id.listJobs);
        btnAddList = (ImageButton) findViewById(R.id.btnAddList);
        imageBtnSettings =(ImageButton) findViewById(R.id.imageBtnSettings);

        SharedPreferences shared = getSharedPreferences("cookie", Context.MODE_PRIVATE);

        if (shared != null) {
            if (!shared.getString("accessToken", "").equals("")) {
                token = shared.getString("accessToken", "");
                getJobsAndRender();
            }
        }

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewListDialog();
            }
        });

        imageBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Setting.class);
                i.putExtra("accessToken", token);
                startActivity(i);
            }
        });
    }

    private void getJobsAndRender() {
        ApiService.apiService.getJobs("Bearer " + token,"10", "0").enqueue(new Callback<GetJobsRes>() {
            @Override
            public void onResponse(Call<GetJobsRes> call, Response<GetJobsRes> response) {
                GetJobsRes res = response.body();
                System.out.println(res);

                if (res != null) {
                    ArrayList<Job> jobs = res.getJobs();
                    ListJobAdapter adapter = new ListJobAdapter(MainActivity.this, R.layout.item_job, jobs);
                    listJobs.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetJobsRes> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createNewListDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpAddList = getLayoutInflater().inflate(R.layout.popup_add_list, null);

        titleList = (EditText) popUpAddList.findViewById(R.id.titleList);
        btnCancel = (ImageButton) popUpAddList.findViewById(R.id.btnCancel);
        btnSave = (ImageButton) popUpAddList.findViewById(R.id.btnSave);

        dialogBuilder.setView(popUpAddList);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleList.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                Job job = new Job(title);

                ApiService.apiService.addJob("Bearer " + token, job).enqueue(new Callback<MessageRes>() {
                    @Override
                    public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {Log.d("a",response.toString());
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                            getJobsAndRender();
                            dialog.dismiss();
                            return;
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                Log.d("b","b");
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("c",title);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageRes> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}