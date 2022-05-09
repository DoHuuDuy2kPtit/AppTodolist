package com.example.todolist;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.Task;
import com.example.todolist.response.GetTaskRes;
import com.example.todolist.response.MessageRes;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTask extends AppCompatActivity {
    DatePickerDialog picker;
    private AlertDialog.Builder dialogBuilder;
    private ListView listTasks;
    private AlertDialog dialog;
    private EditText titleList, timeClock;
    private Button btnCancel, btnSave;
    private ImageButton btnAddList, imageBtnSettings;
    private String token;
    private String jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);

        Intent i = getIntent();
        jobId = i.getStringExtra("jobId");

        listTasks = (ListView) findViewById(R.id.listJobsT);
        btnAddList = (ImageButton) findViewById(R.id.btnAddListT);
        imageBtnSettings =(ImageButton) findViewById(R.id.imageBtnSettingsT);

        SharedPreferences shared = getSharedPreferences("cookie", Context.MODE_PRIVATE);

        if (shared != null) {
            if (!shared.getString("accessToken", "").equals("")) {
                token = shared.getString("accessToken", "");
                getTasksAndRender();
            }
        }

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewListDialog();
            }
        });

        listTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task taskClick = (Task) adapterView.getItemAtPosition(i);
                String taskId = taskClick.getId() + "";
                Intent intent = new Intent(ListTask.this, EditTask.class);
                intent.putExtra("jobId", jobId);
                intent.putExtra("taskId", taskId);
                startActivity(intent);
            }
        });

        imageBtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListTask.this, Setting.class);
                i.putExtra("accessToken", token);
                startActivity(i);
            }
        });
    }

    private void getTasksAndRender() {
        ApiService.apiService.getTasks(jobId, "Bearer " + token,"10", "0").enqueue(new Callback<GetTaskRes>() {
            @Override
            public void onResponse(Call<GetTaskRes> call, Response<GetTaskRes> response) {
                GetTaskRes res = response.body();

                if (res != null) {
                    ArrayList<Task> tasks = res.getTasks();
                    ListTaskAdapter adapter = new ListTaskAdapter(ListTask.this, R.layout.item_task, tasks, jobId, token);
                    listTasks.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetTaskRes> call, Throwable t) {
                Toast.makeText(ListTask.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createNewListDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpAddList = getLayoutInflater().inflate(R.layout.activity_task, null);

        titleList = (EditText) popUpAddList.findViewById(R.id.titleListT);
        timeClock = (EditText) popUpAddList.findViewById(R.id.timeClock);
        btnCancel = (Button) popUpAddList.findViewById(R.id.btnCancelT);
        btnSave = (Button) popUpAddList.findViewById(R.id.btnSaveT);

        timeClock.setInputType(InputType.TYPE_NULL);

        dialogBuilder.setView(popUpAddList);
        dialog = dialogBuilder.create();
        dialog.show();

        timeClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(ListTask.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                cldr.set(year, monthOfYear, dayOfMonth);

                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                String strDate = format.format(cldr.getTime());
                                timeClock.setText(strDate);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleList.getText().toString();
                String[] str = timeClock.getText().toString().split("-");
                String time = str[2] + "-" + str[1] + "-" + str[0];

                if (title.isEmpty() || time.isEmpty()) {
                    Toast.makeText(ListTask.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                Task task = new Task(title,time);

                ApiService.apiService.addTask(jobId, "Bearer " + token, task).enqueue(new Callback<MessageRes>() {
                    @Override
                    public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ListTask.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                            getTasksAndRender();
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Toast.makeText(ListTask.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(ListTask.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<MessageRes> call, Throwable t) {
                        System.out.println(t.getMessage());
                        Toast.makeText(ListTask.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        getTasksAndRender();
    }
}
