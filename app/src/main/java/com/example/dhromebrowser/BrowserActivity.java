package com.example.dhromebrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BrowserActivity extends AppCompatActivity {

    private String url;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        getSupportActionBar().hide();
        webView = findViewById(R.id.webview);
        final EditText urlET = findViewById(R.id.url_text);
        final ImageView homeBtn = findViewById(R.id.home_icon);


        url = getIntent().getStringExtra("url");

        final String urlDate = url.substring(0, 4);
        if (!urlDate.contains("www.")) {
            url = "www.google.com/search?q=" + url;
        }

        urlET.setText(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                urlET.setText(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });


        urlET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String urlText = urlET.getText().toString();

                    if (!urlText.isEmpty()) {

                        final String urlDate = url.substring(0, 4);
                        if (!urlDate.contains("www.")) {
                            url = "www.google.com/search?q=" + url;
                        } else {
                            url = urlText;
                        }
                    }
                }

                return false;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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