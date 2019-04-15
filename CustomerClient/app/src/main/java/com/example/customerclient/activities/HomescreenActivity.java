package com.example.customerclient.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.customerclient.ServerComms.ServerApi;
import com.example.customerclient.R;
import com.example.customerclient.fragments.AccountFragment;
import com.example.customerclient.fragments.MenuFragment;
import com.example.customerclient.fragments.SettingsFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomescreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private static String tableId;
    private static String restId;
    private FirebaseFirestore db;

    private ServerApi serverApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tableId = getIntent().getStringExtra("tableKey");
        setContentView(R.layout.activity_homescreen);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-appeatite-3b562.cloudfunctions.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serverApi = retrofit.create(ServerApi.class);

//        getRestaurantId();


//        if(savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_menu);
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_menu:
                drawer.closeDrawer(GravityCompat.START);
                MenuFragment frag = new MenuFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).commit();
                break;
            case R.id.nav_useraccount:
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccountFragment()).commit();
                break;
            case R.id.nav_settings:
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                break;
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



//    private void getRestaurantId(){
//        Call<Restaurant> call = serverApi.getRestaurant(tableId);
//        call.enqueue(new Callback<Restaurant>() {
//            @Override
//            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(HomescreenActivity.this, response.toString(), Toast.LENGTH_LONG).show();
//                    return;
//                }
//                Restaurant restaurant = response.body();
//                restId = restaurant.getRestaurantId();
//                Toast.makeText(HomescreenActivity.this, restaurant.getRestaurantId(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<Restaurant> call, Throwable t) {
//                Log.d("OnFailure", t.getMessage());
//            }
//        });
//    }

    public static String getTableId(){
        return tableId;
    }

    public static String getRestId(){
        return restId;
    }

}
