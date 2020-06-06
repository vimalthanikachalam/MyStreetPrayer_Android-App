package com.mystreetprayer.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Objects;

public class AudioSermonsActivity extends AppCompatActivity {

    private WebView sermonAudioWeb;
    private ProgressBar sermonProgressbarWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_sermons);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sermons");

        sermonAudioWeb =(WebView) findViewById(R.id.audio_sermon_web);
        sermonProgressbarWeb = (ProgressBar) findViewById(R.id.audio_sermon_ProgressBar);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.audio_sermon_sr);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sermonsWebView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        sermonsWebView();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void sermonsWebView() {

        sermonAudioWeb.loadUrl("https://mystreetprayer.com/AudioSermons/");
        sermonProgressbarWeb.setMax(100);
        WebSettings webSettings= sermonAudioWeb.getSettings();
        sermonAudioWeb.getSettings().setDomStorageEnabled(true);
        sermonAudioWeb.getSettings().setAppCacheEnabled(true);
        sermonAudioWeb.getSettings().setLoadsImagesAutomatically(true);
        sermonAudioWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);

        sermonAudioWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                sermonProgressbarWeb.setProgress(newProgress);
            }
        });

        sermonAudioWeb.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                sermonAudioWeb.loadUrl("file:///android_asset/error.html");
            }
        });

    }

    //On Back Pressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
