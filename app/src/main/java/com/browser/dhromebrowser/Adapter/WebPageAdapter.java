package com.browser.dhromebrowser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.browser.dhromebrowser.Activity.WebPageActivity;
import com.browser.dhromebrowser.Model.WebPage;
import com.browser.dhromebrowser.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WebPageAdapter extends RecyclerView.Adapter<WebPageAdapter.WebPageViewHolder> {


    private final Context context;
    private final ArrayList<WebPage> webpages;


    public WebPageAdapter(Context context, ArrayList<WebPage> webpages) {
        this.context = context;
        this.webpages = webpages;
    }

    @NonNull
    @Override
    public WebPageAdapter.WebPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WebPageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_web_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WebPageAdapter.WebPageViewHolder holder, int position) {

        WebPage webpage = webpages.get(position);
        holder.name.setText(webpage.getName());
        Glide.with(context).load(webpage.getWebpage_image()).into(holder.webpageImage);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, WebPageActivity.class);
            intent.putExtra("webpage_url", webpage.getWebpage_url());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return webpages.size();
    }


    public static class WebPageViewHolder extends RecyclerView.ViewHolder{

        ImageView webpageImage;
        TextView name;

        public WebPageViewHolder(@NonNull View itemView) {
            super(itemView);
            webpageImage = itemView.findViewById(R.id.webpage_image);
            name = itemView.findViewById(R.id.webpage_name);
        }
    }
}
