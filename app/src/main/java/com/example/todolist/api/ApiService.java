package com.example.todolist.api;


import com.example.todolist.model.Password;
import com.example.todolist.model.Job;

import com.example.todolist.model.User;
import com.example.todolist.response.AddJobRes;
import com.example.todolist.response.GetJobsRes;
import com.example.todolist.response.LoginRes;
import com.example.todolist.response.MessageRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {
    // Base URL api: https://api-todoapp.tk/
    // Add api prefix for all request.
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://api-todoapp.tk/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @POST("api/sign-up")
    Call<MessageRes> registerUser(@Body User body);

    @POST("api/sign-in")
    Call<LoginRes> login(@Body User body);

    @PUT("api/users/changepassword")
    Call<MessageRes> changePassword(@Header("Authorization") String authHeader, @Body Password password);

    @GET("api/users/profile")
    Call<User> getInfo(@Header("Authorization") String authHeader);

    @PUT("api/users/profile")
    Call<MessageRes> editInfo(@Header("Authorization") String authHeader, @Body User user );

    @GET("api/jobs")
    Call<GetJobsRes> getJobs(@Header("Authorization") String authHeader, @Query("limit") String limit, @Query("offset") String offset);

    @POST("api/jobs")
    Call<AddJobRes> addJob(@Header("Authorization") String authHeader, @Body Job job);
}
