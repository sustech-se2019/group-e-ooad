package com.google.codelabs.mdc.java.threeNews.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 *
 * Main Application class
 * @author Yu Qiu
 */
public class ThreeNewsApplication extends Application {
    private ThreeNewsApplication instance;

    private static Context appContext;
    private boolean lightMode = true;
    private static RequestQueue queues;



    public  ThreeNewsApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }
    public void setAppContext(Context mAppContext) {
        setAppContextStatic(mAppContext);
    }
    public static void  setAppContextStatic(Context mAppContext){
        appContext = mAppContext;
    }

    public boolean isLightMode() {
        return lightMode;
    }
    public void setLightMode(boolean lightMode) {
        this.lightMode = lightMode;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        queues = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpQueues(){
        return queues;
    }
}