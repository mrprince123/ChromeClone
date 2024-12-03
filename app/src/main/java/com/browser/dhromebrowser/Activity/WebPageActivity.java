package com.browser.dhromebrowser.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.browser.dhromebrowser.MainActivity;
import com.browser.dhromebrowser.R;

public class WebPageActivity extends AppCompatActivity {


    WebView webView;
    ProgressBar progressBar;
    EditText webPageUrlHeader;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView homeButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progress_bar);
        String webUrl = getIntent().getStringExtra("webpage_url");
        webPageUrlHeader = findViewById(R.id.webpage_url_input);
        webPageUrlHeader.setText(webUrl);

        homeButtonBack = findViewById(R.id.home_button_webpage);
        homeButtonBack.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        webView = findViewById(R.id.webpage_view);

        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);

        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // Set WebViewClient to keep all links within WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {

                    view.loadUrl(url); // Load URLs in the WebView
                } else {
                    // Handle other URL schemes like "mailto:", "tel:", "intent:", etc.
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        view.getContext().startActivity(intent); // Open in an external app
                    } catch (Exception e) {
                        Toast.makeText(view.getContext(), "Cannot handle this link", Toast.LENGTH_SHORT).show();
                    }
                }
                return true; // Indicate that the URL has been handled
            }

            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                swipeRefreshLayout.setRefreshing(false); // Hide refresh animation
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String customHtml = "<html><body style='text-align:center; padding-top:50px;'>"
                        + "<h2>Oops! Something went wrong.</h2>"
                        + "<p>We couldn't load the page.</p>"
                        + "<p>Error: " + description + "</p>"
                        + "</body></html>";
                view.loadData(customHtml, "text/html", "UTF-8");
            }
        });


        // Set a WebChromeClient to handle progress updates
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Update the progress bar
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        // Load the URL
        if (webUrl != null) {
            webView.loadUrl(webUrl);
            webView.clearCache(true);
        } else {
            Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
        }


        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            webView.reload();
        });
    }


    @Override
    public void onBackPressed() {
        // Allow back navigation within WebView
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadUrl("about:blank");
            webView.stopLoading();
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroy();
    }
}