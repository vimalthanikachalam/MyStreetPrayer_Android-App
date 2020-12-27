package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;
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

public class PrayerRequests extends AppCompatActivity {

    private WebView prayerView;
    private ProgressBar progressbarPrayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_requests);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Prayer Request");

        prayerView =(WebView) findViewById(R.id.prayerRequestwebView);
        progressbarPrayer = (ProgressBar) findViewById(R.id.prayerRequestProgressBar);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.prayerRequestsr);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                prayerRequestWebView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        prayerRequestWebView();
    }

    //Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void prayerRequestWebView() {

        prayerView.loadUrl("https://mystreetprayer.com/prayer-request-user");

        progressbarPrayer.setMax(50);
        WebSettings webSettings= prayerView.getSettings();
        prayerView.getSettings().setDomStorageEnabled(true);
        prayerView.getSettings().setAppCacheEnabled(true);
        prayerView.getSettings().setLoadsImagesAutomatically(true);
        prayerView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);

        prayerView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbarPrayer.setProgress(newProgress);
            }
        });

        prayerView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                prayerView.loadUrl("file:///android_asset/error.html");
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (prayerView.canGoBack()) {
                        prayerView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
