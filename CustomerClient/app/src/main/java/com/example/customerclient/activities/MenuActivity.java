package com.example.customerclient.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.TextView;


import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;
import com.example.customerclient.activities.helper.TestFragment;


import java.util.ArrayList;
import java.util.List;


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;

    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ListView listView;
    private TextView textView;
    private ArrayList<String> menuHeaders;

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

        /* Set up fragments for menu */
        ArrayList<String> headingNames = CloudFunctions.getInstance().getHeadings().getNames();
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), headingNames);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

//        /*ADDING MENU ITEMS TO ROWS IN LIST*/
//        rows = new ArrayList<>();
//        for(MenuItems items : CloudFunctions.getInstance().getTempListMenu()){
//            rows.addAll(items.getData());
//        }
//        listAdapter = new MenuListAdapter(this, rows);
//        listView.setAdapter(listAdapter);
//        /*==================================*/

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

    public class PagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm, List<String> headingNames) {
            super(fm);

            fragments = new ArrayList<>();
            for(String name : headingNames){
                fragments.add(TestFragment.init(name));
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

        public Fragment getFragment(int position) {
            return fragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return CloudFunctions.getInstance().getHeadings().getNames().get(position);
        }
    }
}
