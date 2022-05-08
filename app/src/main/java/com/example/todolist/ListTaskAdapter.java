package com.example.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.Task;
import com.example.todolist.response.AddJobRes;
import com.example.todolist.response.MessageRes;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTaskAdapter extends ArrayAdapter<Task> {
    private Context mContext;
    private int mResource;
    private String jobId;
    private String token;

    public ListTaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> tasks, String jobId, String token) {
        super(context, resource, tasks);
        this.mContext = context;
        this.mResource = resource;
        this.jobId = jobId;
        this.token = token;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        CheckBox tv = convertView.findViewById(R.id.checkBoxTask);

        tv.setText(getItem(position).getTitle());

        tv.setChecked(getItem(position).getStatus() == 2);

        tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {Log.d("haha", "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

                    Task task = new Task(2);

                    ApiService.apiService.updateStatusTask(jobId, Integer.toString(getItem(position).getId()) , "Bearer " + token, task).enqueue(new Callback<MessageRes>() {
                        @Override
                        public void onResponse(Call<MessageRes> call, Response<MessageRes> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(mContext, "Đã hoàn thành task " + getItem(position).getId(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(mContext, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageRes> call, Throwable t) {
                            System.out.println(t.getMessage());
                            Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });



            }
        });

        return convertView;
    }
}
