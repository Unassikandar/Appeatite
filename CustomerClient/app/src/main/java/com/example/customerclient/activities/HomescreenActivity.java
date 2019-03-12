package com.example.customerclient.activities;

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

import com.example.customerclient.R;
import com.example.customerclient.fragments.AccountFragment;
import com.example.customerclient.fragments.MenuFragment;
import com.example.customerclient.fragments.SettingsFragment;

public class HomescreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String key = getIntent().getStringExtra("tableKey");
        Log.d("tablekey", key);

        setContentView(R.layout.activity_homescreen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_menu);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_menu:
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
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
}
