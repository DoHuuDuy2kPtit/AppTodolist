package com.example.todolist.response;

import com.example.todolist.model.Job;

import java.io.Serializable;
import java.util.ArrayList;

public class GetJobsRes implements Serializable {
    private ArrayList<Job> jobs;
    private String limit;
    private String offset;

    public GetJobsRes(ArrayList<Job> jobs, String limit, String offset) {
        this.jobs = jobs;
        this.limit = limit;
        this.offset = offset;
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Job> jobs) {
        this.jobs = jobs;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
