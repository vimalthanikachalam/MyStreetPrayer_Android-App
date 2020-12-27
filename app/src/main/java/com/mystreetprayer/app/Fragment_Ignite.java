package com.mystreetprayer.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Objects;


public class Fragment_Ignite extends Fragment {

    private WebView webView_ignite;
    private ProgressBar progressBarIgnite;
    AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ignite, container, false);

        webView_ignite =(WebView) rootView.findViewById(R.id.igniteWebView);
        progressBarIgnite = (ProgressBar) rootView.findViewById(R.id.igniteProgressBar);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.ignite_toolbar);
        activity = (AppCompatActivity) getActivity();
        assert activity != null;

        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle("Ignite Service");


        final SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.ignite_sr);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ingiteWebView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ingiteWebView();

        return rootView;
    }


    private void ingiteWebView() {
        webView_ignite.loadUrl("https://mystreetprayer.com/ignite-service");
        progressBarIgnite.setMax(50);

        WebSettings webSettings= webView_ignite.getSettings();
        webView_ignite.getSettings().setDomStorageEnabled(true);
        webView_ignite.getSettings().setAppCacheEnabled(true);
        webView_ignite.getSettings().setLoadsImagesAutomatically(true);

        webView_ignite.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);

        webView_ignite.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarIgnite.setProgress(newProgress);
            }
        });

        webView_ignite.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView_ignite.loadUrl("file:///android_asset/error.html");
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        //getActivity().setTitle("Ignite");
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
