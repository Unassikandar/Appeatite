package com.example.customerclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.customerclient.R;

public class AccountAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account2);

        /*---------NAVIGATION DRAWER ------------*/
        Toolbar toolbar = findViewById(R.id.toolbar_acc);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_acc);
        NavigationView navigationView = findViewById(R.id.nav_view_acc);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*---------------------------------------*/

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
                break;
//            case R.id.nav_settings:
//                drawer.closeDrawer(GravityCompat.START);
//                intent = new Intent(this, SettingsActivity.class);
//                startActivity(intent);
//                break;
        }
        return true;
    }
}
