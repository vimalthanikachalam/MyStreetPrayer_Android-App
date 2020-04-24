package com.mystreetprayer.app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);


        if(savedInstanceState == null ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   new Fragment_Home()).commit();
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);
        }



    }

    // Create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.notification_menu is a reference to an xml file named notification_menu.xmlon_menu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Menu Button Inflater Action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification) {
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }


    //Bottom Navigation Handler
    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.bottom_home:
                        selectedFragment = new Fragment_Home();
                        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);

                        break;

                        case R.id.bottom_bible:
                            selectedFragment = new Fragment_Bible();
                            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_bible);
                            break;

                        case R.id.bottom_ignite:
                            selectedFragment = new Fragment_Ignite();
                            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ignite);
                            break;

                        case R.id.bottom_profile:
                            selectedFragment = new Fragment_Home();
                            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
                            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_profile_fill);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }

            };



    //logout function
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

    }



}

