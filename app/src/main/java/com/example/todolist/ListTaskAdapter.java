package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.api.ApiService;
import com.example.todolist.model.Task;
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

        TextView tv = convertView.findViewById(R.id.tvTaskTitle);
        ImageView iv = convertView.findViewById(R.id.ivCheckbox);

        iv.setImageResource(getItem(position).getStatus() == 2 ? R.drawable.checkbox_checked : R.drawable.checkbox_unchecked);
        tv.setText(getItem(position).getTitle());

        return convertView;
    }
}
