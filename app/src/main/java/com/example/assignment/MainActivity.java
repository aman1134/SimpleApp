package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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