package com.example.todolist.api;

import com.example.todolist.model.User;
import com.example.todolist.response.GetJobsRes;
import com.example.todolist.response.LoginRes;
import com.example.todolist.response.RegisterRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    // Base URL api: https://api-todoapp.tk/
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api-todoapp.tk/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("api/sign-up")
    Call<RegisterRes> registerUser(@Body User body);

    @POST("api/sign-in")
    Call<LoginRes> login(@Body User body);

    @GET("api/jobs")
    Call<GetJobsRes> getJobs(@Header("Authorization") String authHeader, @Query("limit") String limit, @Query("offset") String offset);
}
