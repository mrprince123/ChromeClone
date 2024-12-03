package com.browser.dhromebrowser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.browser.dhromebrowser.Model.News;
import com.browser.dhromebrowser.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {


    private final Context context;
    private final ArrayList<com.browser.dhromebrowser.Model.News> news;

    public NewsAdapter(Context context, ArrayList<com.browser.dhromebrowser.Model.News> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsViewHolder holder, int position) {

        News newNews = news.get(position);

        holder.newsName.setText(newNews.getNewsProviderName());
        holder.newsTitle.setText(newNews.getNewsTitle());

        // The date string in ISO 8601 format
        String originalDateStr = newNews.getNewsTime();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        originalFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Set UTC timezone for parsing

        // Desired target format
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault()); // e.g., "21 October"
        String formattedDate;

        try {
            Date date = originalFormat.parse(originalDateStr); // Parse the ISO 8601 date
            formattedDate = targetFormat.format(date); // Format to "dd MMMM"
        } catch (ParseException e) {
            e.printStackTrace();
            formattedDate = originalDateStr; // Fallback to the original date string if parsing fails
        }

        holder.newsTime.setText(formattedDate);

        Glide.with(context).load(newNews.getNewsImage()).into(holder.imageView);
        Glide.with(context).load(newNews.getNewsProviderLogo()).into(holder.newsLogo);

        holder.newsBody.setOnClickListener(view -> {
            Intent intent = new Intent(context, com.browser.dhromebrowser.Activity.WebPageActivity.class);
            intent.putExtra("webpage_url", newNews.getNewsLink());
            context.startActivity(intent);
        });

        holder.newsShare.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            // Prepare the share content
            String shareText = "Check out this news: " + newNews.getNewsTitle() + "\n" + newNews.getNewsLink();

            // Add the content to the Intent
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

            // Start the sharing Intent
            context.startActivity(Intent.createChooser(shareIntent, "Share news via"));
        });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{


        ImageView imageView, newsLogo, newsShare;
        TextView newsTitle, newsName, newsTime;
        LinearLayout newsBody;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            newsLogo = itemView.findViewById(R.id.news_logo);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsName = itemView.findViewById(R.id.news_name);
            newsTime = itemView.findViewById(R.id.news_time);
            newsBody = itemView.findViewById(R.id.news_parent);
            newsShare = itemView.findViewById(R.id.news_share_button);
        }
    }
}
