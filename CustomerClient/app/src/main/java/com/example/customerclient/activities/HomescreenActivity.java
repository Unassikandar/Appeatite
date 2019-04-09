package com.example.customerclient.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.customerclient.R;
import com.example.customerclient.fragments.AccountFragment;
import com.example.customerclient.fragments.MenuFragment;
import com.example.customerclient.fragments.SettingsFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class HomescreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    /******/
    private static String key;
    private static String restId;
    private FirebaseFirestore db;
    /******/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        key = getIntent().getStringExtra("tableKey");

        setContentView(R.layout.activity_homescreen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Log.d("2222", "blah");

        db = FirebaseFirestore.getInstance();
        db.collection("tables").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> tablelist = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : tablelist){
                                Map<String, Object> t= d.getData();
                                Log.d("2222", t.toString());
                                if(d.getId().equals(key)){
                                    restId = t.get("restaurantId").toString();
                                    Log.d("222", restId);
                                    break;
                                }
                            }
                        }
                        else {
                            Log.d("doc snapshots are empty", restId);
                        }
                    }
                });

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

    public static String getKey(){
        return key;
    }

    public static String getRestId(){
        return restId;
    }

}
