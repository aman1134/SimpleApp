package com.example.assignment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;


    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle( this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView;
        navigationView = findViewById(R.id.top_menu);
        email = navigationView.getHeaderView(0).findViewById(R.id.head_email);
        email.setText(SignIn.header_email);
        navigationView.setNavigationItemSelectedListener(this);

        findViewById(R.id.contact).setBackgroundColor(Color.parseColor("#9DF0FE"));
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.topNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.contact:
                        findViewById(R.id.contact).setBackgroundColor(Color.parseColor("#9DF0FE"));
                        findViewById(R.id.images).setBackgroundColor(Color.parseColor("#000000"));
                        findViewById(R.id.view_images).setBackgroundColor(Color.parseColor("#000000"));
                        switchToContactFrag();
                        break;

                    case R.id.images:
                        findViewById(R.id.images).setBackgroundColor(Color.parseColor("#9DF0FE"));
                        findViewById(R.id.contact).setBackgroundColor(Color.parseColor("#000000"));
                        findViewById(R.id.view_images).setBackgroundColor(Color.parseColor("#000000"));
                        switchToImagesFrag();
                        break;

                    case R.id.view_images:
                        findViewById(R.id.view_images).setBackgroundColor(Color.parseColor("#9DF0FE"));
                        findViewById(R.id.images).setBackgroundColor(Color.parseColor("#000000"));
                        findViewById(R.id.contact).setBackgroundColor(Color.parseColor("#000000"));
                        switchToViewImagesFrag();
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        switch (menuItem.getItemId()){
            case R.id.logout:
                finishAffinity();
                startActivity(new Intent(MainActivity.this , SignInUp.class));
                break;
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void switchToViewImagesFrag() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag , new ViewImagesFragment());
        fragmentTransaction.commit();
    }

    private void switchToImagesFrag() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag , new ImagesFragment());
        fragmentTransaction.commit();
    }

    private void switchToContactFrag() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag , new ContactFragment());
        fragmentTransaction.commit();
    }
}