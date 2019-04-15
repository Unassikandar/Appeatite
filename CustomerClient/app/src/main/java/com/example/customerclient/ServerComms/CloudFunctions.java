package com.example.customerclient.ServerComms;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.customerclient.Model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloudFunctions {

    private String tableId, restId;
    private ServerApi serverApi;
    private String userIdToken;

    private static final CloudFunctions instance = new CloudFunctions();

    private CloudFunctions(){
        tableId = null;
        restId = null;
        userIdToken = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-appeatite-3b562.cloudfunctions.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serverApi = retrofit.create(ServerApi.class);
    }

    public static CloudFunctions getInstance(){
        return instance;
    }

    public void initializeRestId() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                userIdToken = task.getResult().getToken();
                                Call<Restaurant> call = serverApi.getRestaurant(tableId, "Bearer " + userIdToken);
                                call.enqueue(new Callback<Restaurant>() {
                                    @Override
                                    public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d("initiallizeRestId", "response is not successful");
                                            return;
                                        }
                                        restId = response.body().getRestaurantId();
                                        Log.d("restId=", restId);
                                    }

                                    @Override
                                    public void onFailure(Call<Restaurant> call, Throwable t) {
                                        Log.d("OnFailure", t.getMessage());
                                    }
                                });
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("User token not received", "Token failed from main thread single " + e.toString());
                        }
                    });
        }else{Log.d("initiallizeUserIdToken", "User is null");}
    }


    public void initializeValues(){
        initializeRestId();
    }

    public String getTableId() {
        return tableId;
    }

    public String getRestId() {
        return restId;
    }

    public void setTableId(String tid){
        tableId = tid;
    }

}
