package com.example.cloniamix.weatherapp.app;

import android.util.Log;
import android.view.View;


public abstract class Utils {

    public static final String APP_TAG = "cloniamix.tag";

    public static void setVisible(View view, boolean show){
        if (view == null) return;

        int visibility = show ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

    public static void log(String message){
        Log.d(APP_TAG, message);
    }

}
