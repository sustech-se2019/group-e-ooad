package com.google.codelabs.mdc.java.threeNews.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.codelabs.mdc.java.threeNews.application.ThreeNewsApplication;

/**
 * Class that handles image requests using Volley.
 */
public final class ImageRequester {
    private static ImageRequester instance = null;
    private final Context context;
    private final ImageLoader imageLoader;

    private ImageRequester() {
        RequestQueue requestQueue;
        final int maxByteSize;

        context = ThreeNewsApplication.getAppContext();
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        maxByteSize = calculateMaxByteSize();
        this.imageLoader =
                new ImageLoader(
                        requestQueue,
                        new ImageLoader.ImageCache() {
                            private final LruCache<String, Bitmap> lruCache =
                                    new LruCache<String, Bitmap>(maxByteSize) {
                                        @Override
                                        protected int sizeOf(String url, Bitmap bitmap) {
                                            return bitmap.getByteCount();
                                        }
                                    };

                            @Override
                            public Bitmap getBitmap(String url) {
                                synchronized (this) {
                                    return lruCache.get(url);
                                }
                            }

                            @Override
                            public void putBitmap(String url, Bitmap bitmap) {
                                synchronized (this) {
                                    lruCache.put(url, bitmap);
                                }
                            }
                        });
    }

    /**
     * Get a static instance of ImageRequester
     */
    public static ImageRequester getInstance() {
        synchronized (ImageRequester.class) {
            if (instance == null) {
                instance = new ImageRequester();
            }
            return instance;
        }
    }

    /**
     * Sets the image on the given {@link NetworkImageView} to the image at the given URL
     *
     * @param networkImageView {@link NetworkImageView} to set image on
     * @param url              URL of the image
     */
    public void setImageFromUrl(NetworkImageView networkImageView, String url) {
        networkImageView.setImageUrl(url, imageLoader);
    }

    private int calculateMaxByteSize() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int screenBytes = displayMetrics.widthPixels * displayMetrics.heightPixels * 4;
        return screenBytes * 3;
    }
}