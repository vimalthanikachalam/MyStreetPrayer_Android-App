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

public class VideosActivity extends AppCompatActivity {

    private WebView videoWeb;
    private ProgressBar progressBarVideo;
    private String url = "https://thesaturdayagency.com/.content/fmcwc/videos/latest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Videos");

        videoWeb = (WebView) findViewById(R.id.video_view_web);
        progressBarVideo = (ProgressBar) findViewById(R.id.videoProgressBar);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.videos_sr);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                covidWebView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        covidWebView();
    }

    //Back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void covidWebView() {


        videoWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        videoWeb.loadUrl(url);

        progressBarVideo.setMax(100);
        WebSettings webSettings= videoWeb.getSettings();
        videoWeb.getSettings().setDomStorageEnabled(true);
        videoWeb.getSettings().setAppCacheEnabled(true);
        videoWeb.getSettings().setLoadsImagesAutomatically(true);
        videoWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        videoWeb.getSettings().setSupportMultipleWindows(true);

        webSettings.setJavaScriptEnabled(true);

        videoWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarVideo.setProgress(newProgress);
            }
        });

//        videoWeb.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                videoWeb.loadUrl("file:///android_asset/error.html");
//            }
//        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (videoWeb.canGoBack()) {
                        videoWeb.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}

