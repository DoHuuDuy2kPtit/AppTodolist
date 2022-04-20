package com.example.todolist.api;

import com.example.todolist.body.LoginBody;
import com.example.todolist.body.RegisterBody;
import com.example.todolist.response.LoginRes;
import com.example.todolist.response.RegisterRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.99:3000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("/sign-up")
    Call<RegisterRes> registerUser(@Body RegisterBody body);

    @POST("/sign-in")
    Call<LoginRes> login(@Body LoginBody body);
}
