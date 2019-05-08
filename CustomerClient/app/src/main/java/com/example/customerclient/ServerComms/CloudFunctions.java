package com.example.customerclient.ServerComms;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.customerclient.Model.Basket;
import com.example.customerclient.Model.Headings;
import com.example.customerclient.Model.MenuItemData;
import com.example.customerclient.Model.MenuItems;
import com.example.customerclient.Model.Restaurant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CloudFunctions {

    private final String TAG = "Debugging CF";
    private String tableId, restId, restaurantName;
    private ServerApi serverApi;
    private String userIdToken;
    private Headings headings;
    private String headingId;
    private MenuItems menuItems;
    private Basket basket;
    JSONArray jsonArray;

    private ArrayList<MenuItems> tempListMenu;
    private HashMap<String, ArrayList<MenuItemData>> map;

    private static final CloudFunctions instance = new CloudFunctions();

    private CloudFunctions() {
        tableId = null;
        restId = null;
        userIdToken = null;
        tempListMenu = new ArrayList<>();
        basket = new Basket();

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-appeatite-3b562.cloudfunctions.net/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        serverApi = retrofit.create(ServerApi.class);

    }


    public static CloudFunctions getInstance() {
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
                                        restaurantName = response.body().getRestaurantName();
                                        //Log.d("restId=", restId);
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
        } else {
            Log.d("initiallizeUserIdToken", "User is null");
        }
    }

    public void initializeHeadings() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            userIdToken = task.getResult().getToken();
                            Log.d("token=", userIdToken);
                            Call<Headings> call = serverApi.getHeadings(restId, "Bearer " + userIdToken);
                            call.enqueue(new Callback<Headings>() {
                                @Override
                                public void onResponse(Call<Headings> call, Response<Headings> response) {
                                    if (!response.isSuccessful()) {
                                        Log.d(TAG, "Heading response not successful");
                                        return;
                                    }
                                    headings = response.body();
                                    headingId = headings.getHeadingId();
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
                    Log.d(TAG, "Headings, usertoken failure: " + e.getMessage());
                }
            });
        } else {
            Log.d(TAG, "User is null");
        }
    }


    public void initializeMenuItems() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            userIdToken = task.getResult().getToken();
                            for (int i = 0; i < headings.getData().size(); i++) {
                                Call<MenuItems> call = serverApi.getMenuItems(headings.getIds().get(i), "Bearer " + userIdToken);
                                call.enqueue(new Callback<MenuItems>() {
                                    @Override
                                    public void onResponse(Call<MenuItems> call, Response<MenuItems> response) {
                                        if (!response.isSuccessful()) {
                                            Log.d(TAG, "MenuItems response not successful");
                                            return;
                                        }
                                        menuItems = response.body();
                                        Log.d(TAG, menuItems.toString());
                                        tempListMenu.add(menuItems);
                                    }

                                    @Override
                                    public void onFailure(Call<MenuItems> call, Throwable t) {
                                        Log.d("OnFailure", "menuitems failure: " + t.getMessage());
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Headings, usertoken failure: " + e.getMessage());
                }
            });
        } else {
            Log.d(TAG, "User is null");
        }
    }


    public String getTableId() {
        return tableId;
    }

    public String getRestId() {
        return restId;
    }

    public void setTableId(String tid) {
        tableId = tid;
    }

    public Headings getHeadings() {
        return headings;
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }

    public ArrayList<MenuItems> getTempListMenu(){
        return tempListMenu;
    }

    public void postBasket(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            userIdToken = task.getResult().getToken();
                            Log.i("basket", userIdToken);
                            serverApi.addOrder(restId, tableId, jsonArray, "Bearer " + userIdToken).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        Log.i("basket", "posted");
                                    } else {
                                        Log.i("Basket", "post failed");
                                    }
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.i("basket", "response failure" + t.getMessage());
                                }
                            });
                        }
                    });
        } else {
            Log.d(TAG, "User is null");
        }
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
