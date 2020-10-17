package com.example.supermarket;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EndPoint {

    @POST("posts")
    Call<OrderObject> newObjects(@Body OrderObject orderObject);
}
