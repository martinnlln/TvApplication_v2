package com.example.myapplication.television;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.sax.Element;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class StartTelevisionActivity extends Activity {
    private WebView webView;
    private View mCustomView;
    String detailUrl;
    String tv_name;
    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_television_layout);
        webView = (WebView) findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBarWebView);
        detailUrl = getIntent().getStringExtra("tv_url");
        tv_name = getIntent().getStringExtra("tv_name");


        System.out.println(detailUrl);

        webView.setWebChromeClient(new MyChrome());
        webView.setWebViewClient(new WebViewClientClass());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        progressBar.setVisibility(View.VISIBLE);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
                finish();
            }
        }, 20000);

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('post-page-thumbnail')[0].style.display='none'; " +
                        "document.getElementsByClassName('post-page-head-area bauhaus')[0].style.display='none'; " +
                        "document.getElementsByClassName('wptouch-showcase showcase-location-pre_content')[0].style.display='none'; " +
                        "document.getElementsByClassName('sharing-options wptouch-clearfix share-top style-default')[0].style.display='none'; " +
                        "document.getElementsByClassName('swp_social_panel swp_horizontal_panel swp_flat_fresh  swp_default_full_color swp_individual_full_color swp_other_full_color scale-100  scale-')[0].style.display='none'; " +
                        "document.getElementsByClassName('related-posts')[0].style.display='none'; " +
                        "document.getElementsByClassName('nav-controls clearfix')[0].style.display='none'; " +
                        "document.getElementsByClassName('swp_social_panel swp_horizontal_panel swp_flat_fresh  swp_default_full_color swp_individual_full_color swp_other_full_color scale-100  scale- nc_floater')[0].style.display='none'; " +
                        "document.getElementsByClassName('image-with-text')[0].style.display='none'; " +
                        "})()");
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(detailUrl);

    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class MyChrome extends WebChromeClient {
        View fullscreen = null;

        @Override
        public void onHideCustomView() {
            fullscreen.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            webView.setVisibility(View.GONE);

            if (fullscreen != null) {
                ((FrameLayout) getWindow().getDecorView()).removeView(fullscreen);
            }

            fullscreen = view;
            ((FrameLayout) getWindow().getDecorView()).addView(fullscreen, new FrameLayout.LayoutParams(-1, -1));
            fullscreen.setVisibility(View.VISIBLE);
        }
    }

    public static String[] extractLinks(String text) {
        List<String> links = new ArrayList<String>();
        Matcher m = Patterns.WEB_URL.matcher(text);
        while (m.find()) {
            String url = m.group();
            Log.d("TAG", "URL extracted: " + url);
            links.add(url);
        }

        return links.toArray(new String[links.size()]);
    }


    @Override
    protected void onPause() {
        super.onPause();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        webView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            if ((mCustomView == null) && webView.canGoBack()) {
//                webView.goBack();
//                return true;
//            }
//        }
        return super.onKeyDown(keyCode, event);
    }
}