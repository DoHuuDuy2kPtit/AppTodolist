package com.example.todolist.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://localhost:8080/api")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

}
