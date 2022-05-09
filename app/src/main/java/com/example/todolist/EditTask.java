package com.example.todolist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.response.MessageRes;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTask extends AppCompatActivity {
    DatePickerDialog picker;
    EditText taskTitleEdit, timeClockEdit, textAreaDescription;
    String token, jobId, taskId;
    Switch switchBtn;
    ImageButton btnUpdate, btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Intent i = getIntent();
        jobId = i.getStringExtra("jobId");
        taskId = i.getStringExtra("taskId");

        taskTitleEdit = (EditText) findViewById(R.id.titleTaskEdit);
        timeClockEdit = (EditText) findViewById(R.id.timeClockEdit);
        textAreaDescription = (EditText) findViewById(R.id.textAreaDescription);
        switchBtn = (Switch) findViewById(R.id.switchBtn);
        btnUpdate = (ImageButton) findViewById(R.id.btnUpdate);
        btnDelete = (ImageButton) findViewById(R.id.btnDelete);

        SharedPreferences shared = getSharedPreferences("cookie", Context.MODE_PRIVATE);

        if (shared != null) {
            if (!shared.getString("accessToken", "").equals("")) {
                token = shared.getString("accessToken", "");
                displayInfoTask();
            }
        }

        timeClockEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(EditTask.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                cldr.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                String strDate = format.format(cldr.getTime());
                                timeClockEdit.setText(strDate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = taskTitleEdit.getText().toString();
                String[] arr = timeClockEdit.getText().toString().split("-");
                String dueDate = arr[2] + "-" + arr[1] + "-" + arr[0];
                String description = textAreaDescription.getText().toString();
                int status = switchBtn.isChecked() ? 2 : 1;

                Task task = new Task(title, dueDate, status, description.isEmpty() ? null : description);

                ApiService.apiService.updateStatusTask(jobId, taskId , "Bearer " + token, task).enqueue(new Callback<MessageRes>() {
                    @Override
                    public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditTask.this, "Đã cập nhật thông tin task", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(EditTask.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(EditTask.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageRes> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(EditTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.deleteTask(jobId, taskId, "Bearer " + token).enqueue(new Callback<MessageRes>() {
                    @Override
                    public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditTask.this, "Đã xóa task", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditTask.this, ListTask.class);
                            intent.putExtra("jobId", jobId);
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(EditTask.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(EditTask.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageRes> call, Throwable t) {
                        Toast.makeText(EditTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
            }
        });
    }

    private void displayInfoTask() {
        ApiService.apiService.getTask(jobId, taskId, "Bearer " + token).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    Task task = response.body();
                    taskTitleEdit.setText(task.getTitle());
                    timeClockEdit.setText(task.getDueDate());
                    switchBtn.setChecked(task.getStatus() == 2);
                    textAreaDescription.setText(task.getDescription());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(EditTask.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(EditTask.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                Toast.makeText(EditTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });

    }
}
