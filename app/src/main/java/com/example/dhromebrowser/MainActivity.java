package com.example.dhromebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhromebrowser.Adapter.NewsAdapter;
import com.example.dhromebrowser.models.News;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ImageButton amazonButton, googleButton, flipkartButton, myntraButton, hotstarButton, instagramButton, youtubeButton;
    ImageButton feedbackButton;
    WebView webView;
    ImageView google_search_button;
    String voice;
    EditText urlET;
    ArrayList<News> news;
    NewsAdapter newsAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Google Search Button for the Voice Command
    public void openVoiceDialog() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            voice = arrayList.get(0);
            urlET.setText(voice);
            startSearch(voice);
        } else {
            Toast.makeText(this, "Something Went wrong!!", Toast.LENGTH_SHORT).show();
        }
    }

    // Start Search without click, only after the voice command
    private void startSearch(String query) {
        // Perform the search operation based on the query
        // For example, update the URL and load the WebView with the search query
        if (!query.isEmpty()) {
            urlET.setText("");
            Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
            intent.putExtra("url", query);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        urlET = findViewById(R.id.url_button);

        // Google Voice Search Button
        google_search_button = findViewById(R.id.google_search_button);
        google_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVoiceDialog();
            }
        });

        urlET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String urlText = urlET.getText().toString(); // Getting the Text from the EditText Field
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

        //        Google Webpage View
        googleButton = findViewById(R.id.google_button);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleActivity.class);
                startActivity(intent);
            }
        });


//        Amazon Webpage View
        amazonButton = findViewById(R.id.amazon_button);
        amazonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AmazonActivity.class);
                startActivity(intent);
            }
        });

//        Flipkart Webpage View
        flipkartButton = findViewById(R.id.flipkart_button);
        flipkartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FlipkartActivity.class);
                startActivity(intent);
            }
        });

//        Myntra Webpage View
        myntraButton = findViewById(R.id.myntra_button);
        myntraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyntraActivity.class);
                startActivity(intent);
            }
        });

//        Hotstar Webpage View
        hotstarButton = findViewById(R.id.hotstar_button);
        hotstarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HotstarActivity.class);
                startActivity(intent);
            }
        });

//        Instagram Webpage View
        instagramButton = findViewById(R.id.instagram_button);
        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InstagramActivity.class);
                startActivity(intent);
            }
        });

//        YouTube Webpage View
        youtubeButton = findViewById(R.id.youtube_button);
        youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, YouTubeActivity.class);
                startActivity(intent);
            }
        });

        // This is the feedback Button
        feedbackButton = findViewById(R.id.feedback_button);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

//        Initializing the New Function
        initNews();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

//    For Getting the News Data
    void initNews() {
        news = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, news);

        getNewsData();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        RecyclerView recyclerView = findViewById(R.id.news_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);
    }
    void getNewsData() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=9d12903806bd47c2b5e70d25cd09c9ca";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ERROR", response);

                try {
                    JSONObject mainObj = new JSONObject(response);
                    if (mainObj.getString("status").equals("ok")) {
                        JSONArray newsArray = mainObj.getJSONArray("articles");
                        for (int i = 0; i < newsArray.length(); i++) {
                            JSONObject object = newsArray.getJSONObject(i);
                            String author = object.getString("author");
                            String title = object.getString("title");
                            String url = object.getString("url");
                            String urlToImage = object.isNull("urlToImage") ? "https://cdn.pixabay.com/photo/2014/08/07/21/13/newspaper-412811_1280.jpg" : object.getString("urlToImage");

                            News newsData = new News(author, title, url, urlToImage);
                            news.add(newsData);
                        }

                        newsAdapter.notifyDataSetChanged();
                    } else {
                        // Do nothing
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.e("ERROR", "Volley Error: " + error.getMessage());
                    // Handle the error here, such as displaying an error message to the user or retrying the request.
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };
        requestQueue.add(request);
    }
}