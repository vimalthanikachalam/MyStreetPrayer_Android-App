package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.widget.ProgressBar;

import java.util.Objects;

public class PrayerSongWeb extends AppCompatActivity {

    private WebView webView_worship;
    private ProgressBar progressbarWorship;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_song_web);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Worship Songs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Url Router
        url = getIntent().getStringExtra("url");

        webView_worship = (WebView) findViewById(R.id.worship_webView);
        progressbarWorship = (ProgressBar) findViewById(R.id.worshipProgressBar);

        prayerSong();

    }

    //Back
    @Override
    public boolean onSupportNavigateUp() {
        if(webView_worship.canGoBack())
            webView_worship.goBack();
        else
            super.onBackPressed();
        return true;
    }

    private void prayerSong() {

        webView_worship.loadUrl(url);
        progressbarWorship.setMax(100);
        WebSettings webSettings= webView_worship.getSettings();
        webView_worship.getSettings().setDomStorageEnabled(true);
        webView_worship.getSettings().setAppCacheEnabled(true);
        webView_worship.getSettings().setLoadsImagesAutomatically(true);
        webView_worship.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);

        webView_worship.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbarWorship.setProgress(newProgress);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView_worship.canGoBack()) {
                        webView_worship.goBack();
                    } else {

                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}