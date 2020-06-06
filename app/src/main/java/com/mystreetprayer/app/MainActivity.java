package com.mystreetprayer.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mystreetprayer.app.alarmclock.service.NotificationReceiver;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Analytics
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseMessaging.getInstance().subscribeToTopic("FMC");

        //Methods Call
        dailyVerseNotification();
        checkPermission();

//        findViewById(R.id.appbar_main).bringToFront();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        if(savedInstanceState == null ){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   new Fragment_Home()).commit();
           // Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);
        }

    }

    // Notification Channel
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Notification Inflater
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification) {
            Intent notificationIntent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(notificationIntent);
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
//                        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);

                        break;

                        case R.id.bottom_bible:
                            selectedFragment = new Fragment_Bible();
//                            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//                            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_bible);
                            break;

                        case R.id.bottom_ignite:
                            selectedFragment = new Fragment_Ignite();
//                            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//                            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ignite);
                            break;

                        case R.id.bottom_profile:
                            selectedFragment = new Fragment_Profile();
//                            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//                            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_more_horiz_black_24dp);
                            break;
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };


    private void dailyVerseNotification() {

        Calendar calendar = Calendar.getInstance();
        Calendar currentDate = Calendar.getInstance();

        //Setting time of the day (7 AM here) Notification will be sent everyday.
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);

        if(currentDate.after(calendar)){
            Log.w("Added a day", "1");
            calendar.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    //On Back Pressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void checkPermission(){

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_CALENDAR)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        // Use the Builder class for convenient dialog construction
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(R.string.permissiondialog)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Method Calls
                                        permissionToken.continuePermissionRequest();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog returns null
                                    }
                                }).show();
                    }
                }).check();

    }


    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(getBaseContext(), "Press Back again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    }
}

