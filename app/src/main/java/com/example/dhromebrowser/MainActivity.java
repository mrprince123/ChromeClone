package com.example.dhromebrowser;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ImageButton amazonButton, googleButton, flipkartButton, myntraButton, hotstarButton, techcrunchButton, instagramButton, youtubeButton;

    WebView webView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
//        getSupportActionBar().addOnMenuVisibilityListener(onCreateOptionsMenu());

        final EditText urlET = findViewById(R.id.url_button);


        urlET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String urlText = urlET.getText().toString();

                    if (!urlText.isEmpty()) {
                        urlET.setText("");
                        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
                        intent.putExtra("url", urlText);
                        startActivity(intent);
                    }
                }
                return false;
            }
        });

        amazonButton = findViewById(R.id.amazon_button);
        amazonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AmazonActivity.class);
                startActivity(intent);
            }
        });

        googleButton = findViewById(R.id.google_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleActivity.class);
                startActivity(intent);
            }
        });

        flipkartButton = findViewById(R.id.flipkart_button);
        flipkartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlipkartActivity.class);
                startActivity(intent);
            }
        });

        myntraButton = findViewById(R.id.myntra_button);
        myntraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyntraActivity.class);
                startActivity(intent);
            }
        });

        hotstarButton = findViewById(R.id.hotstar_button);
        hotstarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HotstarActivity.class);
                startActivity(intent);
            }
        });

        techcrunchButton = findViewById(R.id.techcrunch_button);
        techcrunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TechcrunchActivity.class);
                startActivity(intent);
            }
        });

        instagramButton = findViewById(R.id.instagram_button);
        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstagramActivity.class);
                startActivity(intent);
            }
        });

        myntraButton = findViewById(R.id.myntra_button);
        myntraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyntraActivity.class);
                startActivity(intent);
            }
        });

        youtubeButton = findViewById(R.id.youtube_button);
        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, YouTubeActivity.class);
                startActivity(intent);
            }
        });


        webView = findViewById(R.id.news_tech);
        ProgressBar progressBar = findViewById(R.id.loading_news);
//        TextView loadingText = findViewById(R.id.loading_text);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.INVISIBLE);
//                loadingText.setVisibility(View.INVISIBLE);
                super.onPageFinished(view, url);
            }
        });

        webView.loadUrl("https://techcrunch.com/");

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