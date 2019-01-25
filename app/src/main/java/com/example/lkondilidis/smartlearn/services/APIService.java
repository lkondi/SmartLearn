package com.example.lkondilidis.smartlearn.services;


import com.example.lkondilidis.smartlearn.notification.MyResponse;
import com.example.lkondilidis.smartlearn.notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAXhZdh4k:APA91bHjEI3UouEbnlMNTwT3Q0BN82tPjIUeMNpr7UproVT127zAo0UfYXNpQImPr9MZEalF3gUAjzaAQ6E2o0KbJFo0dLhRsf7Byl1ag_jiJ2n_pO7uI2FfdObvuTIBCoLc-fNxqWhG"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
