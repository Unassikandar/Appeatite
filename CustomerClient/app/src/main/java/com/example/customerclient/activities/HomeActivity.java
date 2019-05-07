package com.example.customerclient.activities;

import android.app.ProgressDialog;
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

import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView textView;
    private static String tableId, restId;
    private ProgressDialog mProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.rest_name_home);
        mProgress = new ProgressDialog(this);
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

        // Gets restaurantId and Headings
        new FetchingTask().execute();



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
            case R.id.nav_basket:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this, BasketActivity.class);
                startActivity(intent);
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

    // USING TO FETCH DATA FROM BACK END AND TO INITIALIZE MODELS
    private class FetchingTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setMessage("Fetching data...");
            mProgress.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CloudFunctions.getInstance().initializeRestId();
            while(restId == null){
                restId = CloudFunctions.getInstance().getRestId();
            }
            tableId = CloudFunctions.getInstance().getTableId();
            CloudFunctions.getInstance().initializeHeadings();
            while(CloudFunctions.getInstance().getHeadings() == null){
            }
            Log.d("Fetching Heading", CloudFunctions.getInstance().getHeadings().toString());
            CloudFunctions.getInstance().initializeMenuItems();
            while (CloudFunctions.getInstance().getMenuItems() == null){}
            Log.d("Fetching MenuItems", CloudFunctions.getInstance().getMenuItems().toString());
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> names = CloudFunctions.getInstance().getHeadings().getNames();
            Log.d("Fetching names", names.toString());
//            while(CloudFunctions.getInstance().getTempListMenu().size() != CloudFunctions.getInstance().getHeadings().getData().size()){}
            Log.d("Fetching tempList", CloudFunctions.getInstance().getTempListMenu().toString());
            textView.setText(restId);
            mProgress.hide();
        }
    }


}
