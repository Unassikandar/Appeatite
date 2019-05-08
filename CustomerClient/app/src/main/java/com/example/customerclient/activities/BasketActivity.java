package com.example.customerclient.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerclient.Model.Basket;
import com.example.customerclient.R;
import com.example.customerclient.ServerComms.CloudFunctions;
import com.example.customerclient.activities.helper.BasketListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.varvet.barcodereadersample.QRScanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BasketActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private Basket mBasket;
    ListAdapter listAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        /*---------NAVIGATION DRAWER ------------*/
        Toolbar toolbar = findViewById(R.id.toolbar_basket);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout_basket);
        NavigationView navigationView = findViewById(R.id.nav_view_basket);
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

        /* Make Basket List */
        listView = findViewById(R.id.basket_listview);
        listAdapter = new BasketListAdapter(this, CloudFunctions.getInstance().getBasket().getItems());
        listView.setAdapter(listAdapter);

        /* Post basket Button */
        Button postButton = findViewById(R.id.basket_postOrderBtn);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Basket basket = CloudFunctions.getInstance().getBasket();
                basket.setRestaurantId(CloudFunctions.getInstance().getRestId());
                CloudFunctions.getInstance().setBasket(basket);

                JSONObject jsonObject = null;
                JSONArray jsonArray = new JSONArray();
                for(int i=0; i<basket.getItems().size(); i++){
                    jsonObject = new JSONObject();
                    try{
                        jsonObject.put("menuItemId", basket.getItems().get(i).getMenuItemId());
                        jsonObject.put("menuItemName", basket.getItems().get(i).getName());
                        jsonObject.put("quantity", basket.getItems().get(i).getQuantity());
                        jsonArray.put(jsonObject);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                Log.i("json", jsonArray.toString());
                CloudFunctions.getInstance().setJsonArray(jsonArray);
                CloudFunctions.getInstance().postBasket();
                basket.clearBasket();
                Log.i("jsonX", basket.toString());
                CloudFunctions.getInstance().setBasket(basket);
                ((BasketListAdapter) listView.getAdapter()).notifyDataSetChanged();
                listAdapter = new BasketListAdapter(BasketActivity.this, CloudFunctions.getInstance().getBasket().getItems());
                listView.setAdapter(listAdapter);
                //ALERT BOX
                Toast.makeText(v.getContext(), "Order Submited", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()){
            case R.id.nav_menu:
                drawer.closeDrawer(GravityCompat.START);
                if(CloudFunctions.getInstance().getTableId() != null){
                    intent = new Intent(this, MenuActivity.class);
                    startActivity(intent);
                    break;
                } else {
                    Toast.makeText(this, "Please scan suitable QRcode first", Toast.LENGTH_LONG).show();
                    break;
                }
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
        }
        return true;
    }
}
