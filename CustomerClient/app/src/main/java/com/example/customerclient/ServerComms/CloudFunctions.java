package com.example.customerclient.ServerComms;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.customerclient.Model.Headings;
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

    private final String TAG = "Debugging CF";
    private String tableId, restId;
    private ServerApi serverApi;
    private String userIdToken;
    private Headings headings;

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

    public void initializeHeadings(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            userIdToken = task.getResult().getToken();
                            Call<Headings> call = serverApi.getHeadings(restId, "Bearer " + userIdToken);
                            call.enqueue(new Callback<Headings>() {
                                @Override
                                public void onResponse(Call<Headings> call, Response<Headings> response) {
                                    if(!response.isSuccessful()){
                                        Log.d(TAG, "Heading response not successful");
                                        return;
                                    }
                                    headings = response.body();
                                    Log.d("headings=", headings.toString());
                                }

                                @Override
                                public void onFailure(Call<Headings> call, Throwable t) {
                                    Log.d("OnFailure", "headings failure: " + t.getMessage());
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Headings, usertoken failure: "+e.getMessage());
                }
            });
        }
        else{ Log.d(TAG, "User is null");}
    }


    public void initializeValues(){
        initializeRestId();
        initializeHeadings();
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

    public Headings getHeadings(){
        return headings;
    }

}
