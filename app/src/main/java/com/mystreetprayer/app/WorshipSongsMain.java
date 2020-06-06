package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Objects;

public class WorshipSongsMain extends AppCompatActivity {

    String tamil_web_link = "https://worshipsongs-fmc.web.app/index-ta.html";
    String kannada_web_link = "https://worshipsongs-fmc.web.app/index-ka.html";
    String hindi_web_link = "https://worshipsongs-fmc.web.app/index-hi.html";
    String english_web_link = "https://worshipsongs-fmc.web.app/index-en.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worship_songs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.worship_songs_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        CardView songLyrics = (CardView) findViewById(R.id.song_lyrics);
        songLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent lyrics_view = new Intent(WorshipSongsMain.this, Lyrics_MainActivity.class);
               startActivity(lyrics_view);
            }
        });




        CardView tamil_song = (CardView) findViewById(R.id.tamil_song_button);
        tamil_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrayerSongWeb.class);
                intent.putExtra("url", tamil_web_link);
                startActivity(intent);
            }
        });

        CardView kannada_song = (CardView) findViewById(R.id.kannada_song_button);
        kannada_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrayerSongWeb.class);
                intent.putExtra("url", kannada_web_link);
                startActivity(intent);
            }
        });

        CardView hindi_song = (CardView) findViewById(R.id.hindi_song_button);
        hindi_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrayerSongWeb.class);
                intent.putExtra("url", hindi_web_link);
                startActivity(intent);
            }
        });

        CardView english_song = (CardView) findViewById(R.id.english_song_button);
        english_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrayerSongWeb.class);
                intent.putExtra("url", english_web_link);
                startActivity(intent);
            }
        });

    }

    //Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
