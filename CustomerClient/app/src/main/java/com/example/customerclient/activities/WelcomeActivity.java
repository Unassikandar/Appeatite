package com.example.customerclient.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerclient.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.varvet.barcodereadersample.QRScanner;

public class WelcomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Button scanButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ImageView arrow1;
    private ImageView arrow2;
    private Button helpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /*---------NAVIGATION DRAWER ------------*/
        Toolbar toolbar = findViewById(R.id.toolbar_welcome);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_welcome);
        NavigationView navigationView = findViewById(R.id.nav_view_welcome);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        /*---------------------------------------*/

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();



        scanButton = (Button) findViewById(R.id.btnScan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, QRScanner.class));
            }
        });



        //Get username and display welcome user
        TextView tv = findViewById(R.id.hiText);
        String temp = currentUser.getEmail();
        temp = temp.substring(0, temp.indexOf("@"));
        tv.append("Welcome to Appeatite " + temp + '!');
        tv.setTextSize(40);
        tv.setAllCaps(true);
        tv.setTextColor(Color.BLACK);


        //Header of navigation drawer
        TextView tv3 = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtUser);
        String temp3 = currentUser.getEmail();
        temp3 = temp3.substring(0, temp3.indexOf("@"));
        tv3.append(temp3);

        TextView tv2 = navigationView.getHeaderView(0).findViewById(R.id.emailUser);
        String temp2 = currentUser.getEmail();
        tv2.append(temp2);

        arrow1 = findViewById(R.id.imageView2);
        Animation animation= AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.blink_anim);
        arrow1.startAnimation(animation);

        arrow2 = findViewById(R.id.imageView4);
        arrow2.startAnimation(animation);

        helpButton = findViewById(R.id.btnHelp);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });
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
        }
        return true;
    }
}
