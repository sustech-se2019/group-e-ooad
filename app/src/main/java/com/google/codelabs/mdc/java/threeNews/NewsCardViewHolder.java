package com.google.codelabs.mdc.java.threeNews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A class for Card view Holder
 *
 */
public class NewsCardViewHolder extends RecyclerView.ViewHolder {

    TextView newsTitle1, newsTitle2, newsTitle3;
    TextView newsIntro1, newsIntro2, newsIntro3;
    private OnRecycleViewClickListener listener;

    NewsCardViewHolder(@NonNull View itemView, OnRecycleViewClickListener onRecycleViewClickListener) {
        super(itemView);
        newsTitle1 = itemView.findViewById(R.id.news_title_1);
        newsIntro1 = itemView.findViewById(R.id.news_description_1);
        LinearLayout newsLayout1 = itemView.findViewById(R.id.news_1);


        newsTitle2 = itemView.findViewById(R.id.news_title_2);
        newsIntro2 = itemView.findViewById(R.id.news_description_2);
        LinearLayout newsLayout2 = itemView.findViewById(R.id.news_2);

        newsTitle3 = itemView.findViewById(R.id.news_title_3);
        newsIntro3 = itemView.findViewById(R.id.news_description_3);
        LinearLayout newsLayout3 = itemView.findViewById(R.id.news_3);

        listener = onRecycleViewClickListener;
        // set 3 different click listener which are all implements from own listener interface
        newsLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClickListener(getAdapterPosition(), 1);
                }
            }
        });
        newsLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClickListener(getAdapterPosition(), 2);
                }
            }
        });
        newsLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClickListener(getAdapterPosition(), 3);
                }
            }
        });
    }

}
