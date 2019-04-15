package com.example.customerclient.testing_stuff;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerclient.ServerComms.ServerApi;
import com.example.customerclient.Model.Restaurant;
import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView textView;
    private static String tableId, restId;
    private ServerApi serverApi;
    private String userIdToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.rest_name_home);

        Log.d("tablee", CloudFunctions.getInstance().getTableId());

        /*---------NAVIGATION DRAWER ------------*/
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_home);
        NavigationView navigationView = findViewById(R.id.nav_view_home);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*---------------------------------------*/

////        tableId = getIntent().getStringExtra("tableKey");
//        /*************************/
//        CloudFunctions.getInstance().initializeValues();
//        tableId = CloudFunctions.getInstance().getTableId();
//        restId = CloudFunctions.getInstance().getRestId();
//        /*************************/
////
////        new FetchingTask().execute();
//
//        Log.d("tablee", tableId+"\n"+restId);
//
//        textView.setText(restId);
        new FetchingTask().execute();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()){
            case R.id.nav_menu:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("userToken", userIdToken);
                startActivity(intent);
                break;
            case R.id.nav_useraccount:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this, AccountAct.class);
                startActivity(intent);
                break;
//            case R.id.nav_settings:
//                drawer.closeDrawer(GravityCompat.START);
//                intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
//                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else{
            super.onBackPressed();
        }
    }


    private void getRestaurantId(){
        Call<Restaurant> call = serverApi.getRestaurant(tableId, ("Bearer "+userIdToken));
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(HomeActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                Restaurant restaurant = response.body();
                restId = restaurant.getRestaurantId();
                Log.d("restId", restaurant.getRestaurantId());
                Toast.makeText(HomeActivity.this, restaurant.getRestaurantId(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.d("OnFailure", t.getMessage());
            }
        });
    }

//    public void getUserIdToken(){
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                @Override
//                public void onComplete(@NonNull Task<GetTokenResult> task) {
//                    if (task.isSuccessful()) {
//                        userIdToken = task.getResult().getToken();
//                        Log.d("User token received",userIdToken);
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d("User token not received","Token failed from main thread single "+e.toString());
//                }
//            });
//        }
//        else{
//            Log.d("nullUser", "0000");
//        }
//    }

    public static String getTableId(){
        return tableId;
    }

    public static String getRestId(){
        return restId;
    }

    public void getUserIdToken(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        Call<Restaurant> call = serverApi.getRestaurant(tableId, ("Bearer "+task.getResult().getToken()));
                        call.enqueue(new Callback<Restaurant>() {
                            @Override
                            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                                if(!response.isSuccessful()){
                                    Toast.makeText(HomeActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Restaurant restaurant = response.body();
                                restId = restaurant.getRestaurantId();
                                Log.d("restId", restaurant.getRestaurantId());
                                Toast.makeText(HomeActivity.this, restaurant.getRestaurantId(), Toast.LENGTH_LONG).show();
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
                    Log.d("User token not received","Token failed from main thread single "+e.toString());
                }
            });
        }
        else{
            Log.d("nullUser", "0000");
        }
    }

    private class FetchingTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            CloudFunctions.getInstance().initializeRestId();
            while(restId == null){
                restId = CloudFunctions.getInstance().getRestId();
            }
            tableId = CloudFunctions.getInstance().getTableId();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("onpost", tableId+"\n"+CloudFunctions.getInstance().getRestId());
            textView.setText(restId);
        }
    }

}
