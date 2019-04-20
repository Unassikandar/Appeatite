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
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.customerclient.R;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private ListView listView;
    private TextView textView;
    private String restId;
    private ArrayList<String> menuHeaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        listView = findViewById(R.id.menu_header_list);
        textView = findViewById(R.id.rest_name);

        /*---------NAVIGATION DRAWER ------------*/
        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_menu);
        NavigationView navigationView = findViewById(R.id.nav_view_menu);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*---------------------------------------*/

        /* PUT ITEMS IN THE LISTVIEW */
        menuHeaders = new ArrayList<>();
        menuHeaders.add("H1");
        menuHeaders.add("H2");
        menuHeaders.add("H3");
        menuHeaders.add("H4");

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                menuHeaders
        );
        listView.setAdapter(listViewAdapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()){
            case R.id.nav_menu:
                drawer.closeDrawer(GravityCompat.START);
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
}