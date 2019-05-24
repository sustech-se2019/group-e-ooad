package com.google.codelabs.mdc.java.threeNews.network;


import android.graphics.Bitmap;
import android.support.v4.app.FragmentManager;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.codelabs.mdc.java.threeNews.NavigationHost;
import com.google.codelabs.mdc.java.threeNews.NewsGridFragment;
import com.google.codelabs.mdc.java.threeNews.R;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that request a news entry from database
 */
public class NewsRequester {
    private static NewsRequester instance = null;


//    private NewsRequester() {
//        RequestQueue requestQueue;
//        final int maxByteSize;
//
//        context = ThreeNewsApplication.getAppContext();
//        requestQueue = Volley.newRequestQueue(context);
//        requestQueue.start();
//        maxByteSize = calculateMaxByteSize();
//        this.imageLoader =
//                new ImageLoader(
//                        requestQueue,
//                        new ImageLoader.ImageCache() {
//                            private final LruCache<String, Bitmap> lruCache =
//                                    new LruCache<String, Bitmap>(maxByteSize) {
//                                        @Override
//                                        protected int sizeOf(String url, Bitmap bitmap) {
//                                            return bitmap.getByteCount();
//                                        }
//                                    };
//
//                            @Override
//                            public Bitmap getBitmap(String url) {
//                                synchronized (this) {
//                                    return lruCache.get(url);
//                                }
//                            }
//
//                            @Override
//                            public void putBitmap(String url, Bitmap bitmap) {
//                                synchronized (this) {
//                                    lruCache.put(url, bitmap);
//                                }
//                            }
//                        });
//    }
//
//    public NewsEntry getNews(){
//        String url = "http://47.107.97.154:8081/login";
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            /**
//             * Get response from server
//             *
//             * @param response A JSONString response
//             */
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String url = jsonObject.getJSONObject("data").optString("url");
//                    int id = jsonObject.getJSONObject("data").optInt("news_id");
//                    String title = jsonObject.getJSONObject("data").optString("news_title");
//                    String introduction = jsonObject.getJSONObject("data").optString("introduction");
//                    String label = jsonObject.getJSONObject("data").optString("label");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            /**
//             * When meet error in response
//             *
//             * @param error Error information
//             */
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }){
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response){
//                Response<String> r = super.parseNetworkResponse(response);
//                Map<String,String> head = response.headers;
//                String cookies = head.get("Set-Cookie");
//                CookiesUtils.cookie = cookies.substring(0,cookies.indexOf(";"));
//                return r;
//            }
//
//        };
//        request.setTag("testPost");
//        ThreeNewsApplication.getHttpQueues().add(request);
//    }
//    }
}
