package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Objects;

public class PrayerSongWeb extends AppCompatActivity {

    private WebView webView_worship;
    private ProgressBar progressbarWorship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_song_web);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Worship Songs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView_worship =(WebView) findViewById(R.id.worship_webView);
        progressbarWorship = (ProgressBar) findViewById(R.id.worshipProgressBar);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.worship_sr);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prayerSong();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

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

        webView_worship.loadUrl("https://worshipsongs-fmc.web.app/");
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

        webView_worship.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView_worship.loadUrl("file:///android_asset/error.html");
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