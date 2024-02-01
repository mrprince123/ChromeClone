package com.example.dhromebrowser.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhromebrowser.NewsActivity;
import com.example.dhromebrowser.R;
import com.example.dhromebrowser.databinding.ItemNewsBinding;
import com.example.dhromebrowser.models.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<News> news;

    public NewsAdapter(Context context, ArrayList<News> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        News news1 = news.get(position);

        Glide.with(context)
                .load(news1.getUrlToImage())
                .into(holder.binding.image);
        holder.binding.author.setText(news1.getAuthor());
        holder.binding.title.setText(news1.getTitle());

        holder.binding.urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = news1.getUrl();
                    // Open URL in WebView
                    WebView webView = new WebView(context);
                    webView.setWebViewClient(new WebViewClient());
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(url);

//                     Create a dialog to display the WebView
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(webView);
                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        ItemNewsBinding binding;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemNewsBinding.bind(itemView);
        }
    }
}
