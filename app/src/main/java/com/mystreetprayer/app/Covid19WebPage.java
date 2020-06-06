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

public class Covid19WebPage extends AppCompatActivity {

    private WebView webView_covid;
    private ProgressBar progressbarCovid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19_web_page);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Covid 19 INDIA");

        webView_covid =(WebView) findViewById(R.id.covid19web);
        progressbarCovid = (ProgressBar) findViewById(R.id.covidProgressBar);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.covid19_sr);
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
//        onBackPressed();
        if(webView_covid.canGoBack())
            webView_covid.goBack();
        else
            super.onBackPressed();
        return true;
    }

    private void covidWebView() {

        webView_covid.loadUrl("https://www.covid19india.org/");
        //webView_covid.loadUrl("https://worshipsongs-fmc.web.app/");
        progressbarCovid.setMax(100);
        WebSettings webSettings= webView_covid.getSettings();
        webView_covid.getSettings().setDomStorageEnabled(true);
        webView_covid.getSettings().setAppCacheEnabled(true);
        webView_covid.getSettings().setLoadsImagesAutomatically(true);
        webView_covid.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);

        webView_covid.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbarCovid.setProgress(newProgress);
            }
        });

        webView_covid.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView_covid.loadUrl("file:///android_asset/error.html");
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView_covid.canGoBack()) {
                        webView_covid.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


}
