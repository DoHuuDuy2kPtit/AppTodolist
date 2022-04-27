package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.model.Job;

import java.util.ArrayList;

public class ListJobAdapter extends ArrayAdapter<Job> {
    private Context mContext;
    private int mResource;

    public ListJobAdapter(@NonNull Context context, int resource,@NonNull ArrayList<Job> jobs) {
        super(context, resource, jobs);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView tv = convertView.findViewById(R.id.textTitleJob);

        tv.setText(getItem(position).getTitle());

        return convertView;
    }
}
