package com.google.codelabs.mdc.java.threeNews.network;

import android.content.res.Resources;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.codelabs.mdc.java.threeNews.R;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A product entry in the list of news.
 */
public class NewsEntry {
    private static final String TAG = NewsEntry.class.getSimpleName();

    public final int id1, id2, id3;
    public final String kind1, kind2, kind3;
    public final String url1, url2, url3;
    public final String title1, title2, title3;
    public final String introduction1, introduction2, introduction3;

    public NewsEntry(int id1, int id2, int id3,
                     String kind1, String kind2, String kind3,
                     String url1, String url2, String url3,
                     String title1, String title2, String title3,
                     String introduction1, String introduction2, String introduction3) {
        this.id1 = id1;
        this.id2 = id2;
        this.id3 = id3;
        this.kind1 = kind1;
        this.kind2 = kind2;
        this.kind3 = kind3;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
        this.introduction1 = introduction1;
        this.introduction2 = introduction2;
        this.introduction3 = introduction3;
    }

    /**
     * Loads a raw JSON at R.raw.news and converts it into a list of NewsEntry objects
     */
    public static List<NewsEntry> initNewsEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.news);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonProductsString = writer.toString();
        Gson gson = new Gson();
        Type productListType = new TypeToken<ArrayList<NewsEntry>>() {
        }.getType();
        return gson.fromJson(jsonProductsString, productListType);
    }

}