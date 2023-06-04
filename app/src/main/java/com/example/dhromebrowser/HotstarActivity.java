package com.example.dhromebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class HotstarActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotstar);

        getSupportActionBar().hide();
            webView = findViewById(R.id.hotstar_webview);
            webView.loadUrl("https://www.hotstar.com/");

            webView.setWebChromeClient(new WebChromeClient());

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setPluginState(WebSettings.PluginState.ON);

            progressBar = findViewById(R.id.hotstar_progress);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    progressBar.setVisibility(View.VISIBLE);
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                    super.onPageFinished(view, url);
                }
            });

        }

        @Override
        public void onBackPressed() {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                super.onBackPressed();
            }
    }
}