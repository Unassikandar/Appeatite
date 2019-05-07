package com.example.customerclient.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.customerclient.Model.Basket;
import com.example.customerclient.Model.BasketItem;
import com.example.customerclient.Model.MenuItemData;
import com.example.customerclient.Model.MenuItems;
import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;
import com.example.customerclient.activities.helper.TestFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.varvet.barcodereadersample.QRScanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ListView listView;
    private TextView textView;
    private ArrayList<String> menuHeaders;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


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
        //Header of navigation drawer
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        TextView tv = navigationView.getHeaderView(0).findViewById(R.id.txtUser);
        String temp = currentUser.getEmail();
        temp = temp.substring(0, temp.indexOf("@"));
        tv.append(temp);

        TextView tv2 = navigationView.getHeaderView(0).findViewById(R.id.emailUser);
        String temp2 = currentUser.getEmail();
        tv2.append(temp2);
        /*==========================================*/


        /* Set up fragments for menu */
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


        /* Testing Post */
        Basket basket = CloudFunctions.getInstance().getBasket();
        MenuItemData menuItemData = CloudFunctions.getInstance().getMenuItems().getData().get(0);
        BasketItem basketItem = new BasketItem(menuItemData.getMenuItemId(), menuItemData.getName(), menuItemData.getPrice());
        basket.addItem(basketItem);
        basket.setRestaurantId(CloudFunctions.getInstance().getRestId());
        CloudFunctions.getInstance().setBasket(basket);

        JSONObject jsonObject = null;
        JSONArray jsonArray = new JSONArray();
        for(int i=0; i<basket.getItems().size(); i++){
            jsonObject = new JSONObject();
            try{
                jsonObject.put("menuItemId", basket.getItems().get(i).getMenuItemId());
                jsonObject.put("quantity", basket.getItems().get(i).getQuantity());
                jsonArray.put(jsonObject);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        Log.i("json", jsonArray.toString());
        CloudFunctions.getInstance().setJsonArray(jsonArray);

        CloudFunctions.getInstance().postBasket();

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
            case R.id.nav_help:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                drawer.closeDrawer(GravityCompat.START);
                mAuth.signOut();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_scan:
                drawer.closeDrawer(GravityCompat.START);
                intent = new Intent(this, QRScanner.class);
                startActivity(intent);
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

    public class PagerAdapter extends FragmentStatePagerAdapter {

        List<TestFragment> fragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);

            fragments = new ArrayList<>();
            for(int i=0; i<CloudFunctions.getInstance().getHeadings().getData().size(); i++){
                fragments.add(TestFragment.init(i));
            }

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return CloudFunctions.getInstance().getHeadings().getNames().get(position);
        }

    }

}
