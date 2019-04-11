package com.example.customerclient.testing_stuff;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.customerclient.Interfaces.ServerApi;
import com.example.customerclient.Model.Restaurant;
import com.example.customerclient.R;
import com.example.customerclient.activities.HomescreenActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private static String tableId, restId;
    private ServerApi serverApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        tableId = getIntent().getStringExtra("tableKey");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-appeatite-3b562.cloudfunctions.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serverApi = retrofit.create(ServerApi.class);

        getRestaurantId();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()){
            case R.id.nav_menu:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this, MenuActivity.class);
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
        Call<Restaurant> call = serverApi.getRestaurant(tableId);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(HomeActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                Restaurant restaurant = response.body();
                restId = restaurant.getRestaurantId();
                Toast.makeText(HomeActivity.this, restaurant.getRestaurantId(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                Log.d("OnFailure", t.getMessage());
            }
        });
    }

    public static String getTableId(){
        return tableId;
    }

    public static String getRestId(){
        return restId;
    }
}
