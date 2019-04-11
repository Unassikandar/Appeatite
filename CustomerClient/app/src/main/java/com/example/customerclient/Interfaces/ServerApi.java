package com.example.customerclient.Interfaces;

import com.example.customerclient.Model.Restaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ServerApi {

    @Headers("Content-Type: application/json")
    @GET("getRestaurant")
    Call<Restaurant> getRestaurant(@Query("tableId") String tableId);
}
