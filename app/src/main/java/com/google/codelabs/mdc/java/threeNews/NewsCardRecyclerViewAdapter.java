package com.google.codelabs.mdc.java.threeNews;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.codelabs.mdc.java.threeNews.network.NewsEntry;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter used to show a simple grid of news.
 */
public class NewsCardRecyclerViewAdapter extends RecyclerView.Adapter<NewsCardViewHolder> {

    final private List<NewsEntry> newsList;
    private OnRecycleViewClickListener listener;
    // final private ImageRequester imageRequester;

    NewsCardRecyclerViewAdapter(List<NewsEntry> newsList) {
        this.newsList = newsList;
        // imageRequester = ImageRequester.getInstance();
    }

    /**
     * Set the listener
     * @param onRecycleViewClickListener own interface
     */
    public void setItemClickListener(OnRecycleViewClickListener onRecycleViewClickListener){
        listener = onRecycleViewClickListener;
    }

    /**
     * Create a holder of this view
     *
     * @param parent The parent of view
     * @param viewType The type of view
     * @return NewsCardViewHolder
     */
    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new NewsCardViewHolder(layoutView, listener);
    }


    /**
     * Bind a holder of view
     *
     * @param holder The holder of view
     * @param position The position of Three Entries
     */
    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder holder, int position) {
        if (newsList != null && position < newsList.size()){
            NewsEntry news = newsList.get(position);
            holder.newsTitle1.setText(news.title1);
            String intro1 = news.introduction1;
            if (intro1.length() > 50){
                intro1 = intro1.substring(0,49)+"...";
            }
            holder.newsIntro1.setText(intro1);

            holder.newsTitle2.setText(news.title2);
            String intro2 = news.introduction2;
            if (intro2.length() > 50){
                intro2 = intro2.substring(0,49)+"...";
            }
            holder.newsIntro2.setText(intro2);

            holder.newsTitle3.setText(news.title3);
            String intro3 = news.introduction3;
            if (intro3.length() > 50){
                intro3 = intro3.substring(0,49)+"...";
            }
            holder.newsIntro3.setText(intro3);
        }
    }

    /**
     * Get Item count
     *
     * @return The size of newslist
     */
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    /**
     * pop an news entry
     */
    void popNews(int position, NewsEntry news, Resources resource){
        newsList.add(position, news);
        notifyItemInserted(position);
    }



}
