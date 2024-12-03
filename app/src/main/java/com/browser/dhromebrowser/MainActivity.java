package com.browser.dhromebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.browser.dhromebrowser.Activity.BrowserActivity;
import com.browser.dhromebrowser.Activity.WebPageActivity;
import com.browser.dhromebrowser.Adapter.NewsAdapter;
import com.browser.dhromebrowser.Adapter.WebPageAdapter;
import com.browser.dhromebrowser.Model.News;
import com.browser.dhromebrowser.Model.WebPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView webRecycleView, newsRecycleView;
    ArrayList<WebPage> webPageArrayList;
    WebPageAdapter webPageAdapter;
    ArrayList<News> newsArrayList;
    NewsAdapter newsAdapter;
    LinearLayout loadingProgress;

    ImageView googleSearchButton;
    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        webRecycleView = findViewById(R.id.webpage_recycle_view);
        newsRecycleView = findViewById(R.id.news_recycle_view);
        loadingProgress = findViewById(R.id.loading_news_progress);

        searchInput = findViewById(R.id.search_input);
        searchInput.setOnEditorActionListener((view, actionId, event) -> {
            String searchValue = searchInput.getText().toString().toLowerCase().trim();
            if (!searchValue.isEmpty()) {
                Intent intent = new Intent(this, BrowserActivity.class);
                intent.putExtra("webpage_url", searchValue);
                startActivity(intent);
                searchInput.setText("");
            } else {
                Toast.makeText(this, "Enter anything to search...", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        googleSearchButton = findViewById(R.id.google_search_button);
        googleSearchButton.setOnClickListener(view -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US"); // Set to your preferred language
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search anything...");
            try {
                startActivityForResult(intent, 1); // 1 is the request code, you can change it to any integer value you like
            } catch (Exception e) {
                Toast.makeText(this, "Your device doesn't support speech input", Toast.LENGTH_SHORT).show();
            }
        });


        initWebPage();
        initAllNews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenText = result.get(0);
                Intent intent = new Intent(this, BrowserActivity.class);
                intent.putExtra("webpage_url", spokenText);
                startActivity(intent);
            }
        }
    }


    // WebPage Init
    void initWebPage() {
        webPageArrayList = new ArrayList<>();
        webPageAdapter = new WebPageAdapter(this, webPageArrayList);

        getAllWebPage();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        webRecycleView.setLayoutManager(layoutManager);
        webRecycleView.setAdapter(webPageAdapter);
    }

    void getAllWebPage() {

        // Sample data in a format similar to your provided data
        String jsonData = "[{\"name\": \"Amazon\", \"url\": \"https://www.amazon.in/\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730826090/Amazon-512_vouj9d.webp\"},"
                + "{\"name\": \"Instagram\", \"url\": \"https://www.instagram.com/\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730826092/Instagram_logo_2022.svg_wtx1wo.webp\"},"
                + "{\"name\": \"Flipkart\", \"url\": \"https://www.flipkart.com/\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730903389/flipkart_icon_SKVWQhA_cmtays.png\"},"
                + "{\"name\": \"Myntra\", \"url\": \"https://www.myntra.com/\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730826576/108ffd534d2076aa59babdb3b925438d_c5zps0.png\"},"
                + "{\"name\": \"Jio Cinema\", \"url\": \"https://www.jiocinema.com/\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730826091/Jio_Cinema._ionjev.png\"},"
                + "{\"name\": \"Hotstar\", \"url\": \"https://www.hotstar.com/in/home?ref=%2Fin\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730826453/hotstar-logo-6A3E165CC3-seeklogo.com_hytxse.png\"},"
                + "{\"name\": \"Youtube\", \"url\": \"https://www.youtube.com/\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730903388/11-114700_youtube-red-circle-youtube-circle-icon-png-transparent_b8ruy7.png\"},"
                + "{\"name\": \"Wikipedia\", \"url\": \"https://www.wikipedia.org/\", \"imageLogo\": \"https://res.cloudinary.com/sniperdocs/image/upload/v1730826093/Wikipedia-logo-v2.svg_uafvsc.png\"}]";

        // Parse the JSON data
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String url = jsonObject.getString("url");
                String imageLogo = jsonObject.getString("imageLogo");

                // Create a new WebPage object (assuming you have a WebPage class)
                WebPage webPage = new WebPage(imageLogo, name, url);
                webPageArrayList.add(webPage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Notify the adapter about data changes
        webPageAdapter.notifyDataSetChanged();
    }


    // News View
    void initAllNews() {
        newsArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsArrayList);

        getAllNews();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        newsRecycleView.setLayoutManager(layoutManager);
        newsRecycleView.setAdapter(newsAdapter);
    }

    void getAllNews() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String newsUrl = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=9d12903806bd47c2b5e70d25cd09c9ca";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, newsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    loadingProgress.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("ok")) {
                        JSONArray articleArray = jsonObject.getJSONArray("articles");

                        newsArrayList.clear();

                        for (int i = 0; i < articleArray.length(); i++) {
                            JSONObject article = articleArray.getJSONObject(i);
                            JSONObject source = article.getJSONObject("source");

                            String Image = article.getString("urlToImage").trim();
                            String title = article.getString("title").trim();
                            String Logo = article.getString("urlToImage").trim();
                            String Name = source.getString("name").trim();
                            String time = article.getString("publishedAt").trim();
                            String link = article.getString("url").trim();


                            News news = new News(
                                    Image, title, Logo, Name, time, link
                            );
                            newsArrayList.add(news);
                        }
                        newsAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // No Nothing
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                return headers;
            }
        };

        queue.add(stringRequest);
    }
}