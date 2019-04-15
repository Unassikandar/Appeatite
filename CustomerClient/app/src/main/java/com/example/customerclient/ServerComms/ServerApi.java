package com.example.customerclient.ServerComms;

import com.example.customerclient.Model.Restaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ServerApi {

//    @Headers("Content-Type: application/json")
//    @GET("getRestaurant")
//    Call<Restaurant> getRestaurant(@Query("tableId") String tableId);

    @Headers("Content-Type: application/json")
    @GET("getRestaurant")
    Call<Restaurant> getRestaurant(@Query("tableId") String tableId, @Header("Authorization") String userAuth);

}
