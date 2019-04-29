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
import android.widget.TextView;

import com.example.customerclient.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.varvet.barcodereadersample.QRScanner;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        /*---------NAVIGATION DRAWER ------------*/
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.settings_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_settings);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*---------------------------------------*/

        //Header of navigation drawer
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView tv3 = navigationView.getHeaderView(0).findViewById(R.id.txtUser);
        String temp3 = currentUser.getEmail();
        temp3 = temp3.substring(0, temp3.indexOf("@"));
        tv3.append(temp3);

        TextView tv2 = navigationView.getHeaderView(0).findViewById(R.id.emailUser);
        String temp2 = currentUser.getEmail();
        tv2.append(temp2);
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
            case R.id.nav_settings:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_scan:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this, QRScanner.class);
                startActivity(intent);
        }
        return true;
    }
}
