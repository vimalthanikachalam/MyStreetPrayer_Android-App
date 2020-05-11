package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Objects;

public class OfferingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offering);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Offering");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView contactus = (CardView) findViewById(R.id.questions_contact);
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInTouch();
            }
        });
    }

    private void getInTouch() {
        String contact = "+91 9341231121"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = this.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp app not installed in your Phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
