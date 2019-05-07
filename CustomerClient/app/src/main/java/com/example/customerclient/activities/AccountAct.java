package com.example.customerclient.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customerclient.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.varvet.barcodereadersample.QRScanner;

import java.io.IOException;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class AccountAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;

    private Button uploadButton;
    private Button passButton;
    private Button emailButton;
    private Button signOutButton;

    private EditText passText;
    private EditText emailText;

    private ImageView displayPic;

    private ProgressDialog mProgress;
    private ProgressDialog logout;

    private static final int CAMERA_REQUEST_CODE = 01;

    private StorageReference mStorage;

    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account2);



        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);
        logout = new ProgressDialog(this);

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

        //Header of navigation drawer
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        TextView tv3 = navigationView.getHeaderView(0).findViewById(R.id.txtUser);
        String temp3 = currentUser.getEmail();
        temp3 = temp3.substring(0, temp3.indexOf("@"));
        tv3.append(temp3);

        TextView tv2 = navigationView.getHeaderView(0).findViewById(R.id.emailUser);
        String temp2 = currentUser.getEmail();
        tv2.append(temp2);

        //Get username and display welcome user
        TextView tv = findViewById(R.id.welcomeText);
        String temp = currentUser.getEmail();
        temp = temp.substring(0, temp.indexOf("@"));
        tv.append(temp + "s account" );

        //Add profile picture
        //If dp already exists get it and upload
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("newFolder/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        uploadButton = (Button) findViewById(R.id.btnPic);
        displayPic = (ImageView) findViewById(R.id.imgPic);


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        //Change account email
        emailButton = (Button) findViewById(R.id.btnEmail);
        emailText = (EditText) findViewById(R.id.txtEmail);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( emailText.getText().toString().isEmpty() )
                {
                    Toast.makeText(AccountAct.this, "Please provide an email address to change!", Toast.LENGTH_LONG).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches())
                {
                    Toast.makeText(AccountAct.this, "Please provide a valid email address!", Toast.LENGTH_LONG).show();
                }
                else {
                    currentUser.updateEmail(emailText.getText().toString());
                    Toast.makeText(AccountAct.this, "Email updated to " + emailText.getText().toString(), Toast.LENGTH_LONG).show();
                    logout.setMessage("Redirecting to log-in screen");
                    logout.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            logout.dismiss();
                            mAuth.signOut();
                            Intent intent = new Intent(AccountAct.this, MainActivity.class );
                            startActivity(intent);
                        }
                    }, 2500);

                }
            }
        });
        //Change account pass
        passButton = (Button) findViewById(R.id.btnPass);
        passText = (EditText) findViewById(R.id.txtPass);
        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( passText.getText().toString().isEmpty() )
                {
                    Toast.makeText(AccountAct.this, "Please provide a password to change!", Toast.LENGTH_LONG).show();
                }
                else if ( passText.getText().toString().length() < 6 )
                {
                    Toast.makeText(AccountAct.this, "Password must be at least 6 characters!", Toast.LENGTH_LONG).show();
                }
                else {
                    currentUser.updatePassword(passText.getText().toString());
                    Toast.makeText(AccountAct.this, "Password updated to " + passText.getText().toString(), Toast.LENGTH_LONG).show();
                    logout.setMessage("Redirecting to log-in screen");
                    logout.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            logout.dismiss();
                            mAuth.signOut();
                            Intent intent = new Intent(AccountAct.this, MainActivity.class );
                            startActivity(intent);
                        }
                    }, 2500);
                }
            }
        });

        //Sign out
        signOutButton = (Button) findViewById(R.id.btnSignOut);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout.setMessage("Signing out...");
                logout.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        logout.dismiss();
                        mAuth.signOut();
                        Intent intent = new Intent(AccountAct.this, MainActivity.class );
                        startActivity(intent);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Upload image from camera
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK ){
            mProgress.setMessage("Uploading Image...");
            mProgress.show();
            String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final Uri uri  = data.getData();
            StorageReference filePath = mStorage.child("Photos").child(userUid);
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgress.dismiss();
                    Toast.makeText(AccountAct.this, "Uploaded image", Toast.LENGTH_LONG).show();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        displayPic.setImageBitmap(bitmap);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
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
}
