package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Random;

public class DailyVerse_Activity extends AppCompatActivity {

    public static TextView dailyVerse;
    public static TextView verseAuthor;
    private Button sharetheApp;


    private int[] images = {
            R.drawable.bg_getting_started,R.drawable.bg,R.drawable.verse_img_1,
            R.drawable.verse_img_2, R.drawable.verse_img_3, R.drawable.verse_img_4,
            R.drawable.verse_img_5, R.drawable.verse_img_6, R.drawable.verse_img_7,
            R.drawable.verse_img_8, R.drawable.verse_img_9, R.drawable.verse_img_10,
            R.drawable.verse_img_11, R.drawable.verse_img_12, R.drawable.verse_img_13,
            R.drawable.verse_img_14, R.drawable.verse_img_15, R.drawable.verse_img_16,
            R.drawable.verse_img_17, R.drawable.verse_img_18, R.drawable.verse_img_19,
            R.drawable.verse_img_20,
            R.drawable.verse_img_new_2, R.drawable.verse_img_new_3, R.drawable.verse_img_new_4,
            R.drawable.verse_img_new_5, R.drawable.verse_img_new_6, R.drawable.verse_img_new_7,
            R.drawable.verse_img_new_8, R.drawable.verse_img_new_10, R.drawable.verse_img_new_11,
            R.drawable.verse_img_new_12, R.drawable.verse_img_new_13, R.drawable.verse_img_new_14,
            R.drawable.verse_img_new_15, R.drawable.verse_img_new_16, R.drawable.verse_img_new_17,
            R.drawable.verse_img_new_18, R.drawable.verse_img_new_19, R.drawable.verse_img_new_20,
            R.drawable.verse_img_new_21, R.drawable.verse_img_new_22, R.drawable.verse_img_new_23,
            R.drawable.verse_img_new_24, R.drawable.verse_img_new_25, R.drawable.verse_img_new_26,
            R.drawable.verse_img_new_27, R.drawable.verse_img_new_28, R.drawable.verse_img_new_29,
            R.drawable.verse_img_new_30, R.drawable.verse_img_new_31, R.drawable.verse_img_new_32,
            R.drawable.verse_img_new_33, R.drawable.verse_img_new_34,
    };


    private ImageView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_verse);


        banner = (ImageView) findViewById(R.id.dailyVerse_Image);
//
         dailyVerse = (TextView) findViewById(R.id.verse_View);
         verseAuthor = (TextView) findViewById(R.id.author_view);

         sharetheApp = (Button) findViewById(R.id.share_verse);

//
//
        Toolbar toolbar = (Toolbar) findViewById(R.id.dailverse_Toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_24dp);

        VOTD_Data_Activity process = new VOTD_Data_Activity(DailyVerse_Activity.this);
        process.execute();


        randomImage();
        shareKPCapp();

    }

    private void shareKPCapp() {
        sharetheApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "Karnataka Prayer Call 24/7 APP.\n"
                        +"\n"
                        + "Be the first to receive updates from KPC APP. \n"
                        + "\n"
                        + "Download the App Now!  \n"
                        + "https://play.google.com/store/apps/details?id="
                        + BuildConfig.APPLICATION_ID);
                startActivity(Intent.createChooser(share, "Share"));
            }
        });
    }


    //On Back Pressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    //Daily Verse Random Images Pick from Local
    private void randomImage() {
        Random random = new Random();
        banner.setImageResource(images[random.nextInt(images.length)]);

    }

}
