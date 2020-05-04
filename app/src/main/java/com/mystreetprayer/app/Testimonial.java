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

public class Testimonial extends AppCompatActivity {
    private WebView webView_Testimonial;
    ProgressBar progressbarTestimonial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimonial);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Testimonial");

        webView_Testimonial =(WebView) findViewById(R.id.testimonialweb);
        progressbarTestimonial = (ProgressBar) findViewById(R.id.testimonialProgressBar);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.testimonial_sr);
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

        webView_Testimonial.loadUrl("https://mystreetprayer.com/testimony/");

        progressbarTestimonial.setMax(50);
        WebSettings webSettings= webView_Testimonial.getSettings();
        webView_Testimonial.getSettings().setDomStorageEnabled(true);
        webView_Testimonial.getSettings().setAppCacheEnabled(true);
        webView_Testimonial.getSettings().setLoadsImagesAutomatically(true);
        webView_Testimonial.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);

        webView_Testimonial.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbarTestimonial.setProgress(newProgress);
            }
        });

        webView_Testimonial.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView_Testimonial.loadUrl("file:///android_asset/error.html");
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView_Testimonial.canGoBack()) {
                        webView_Testimonial.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


}
