package com.example.cloniamix.weatherapp.app;

import android.util.Log;
import android.view.View;

/**Класс хранения констант и универсальных методов*/
public abstract class Utils {

    public static final String APP_TAG = "weatherAppTag";

    public static final String APP_PREFERENCES = "mySettings";
    public static final String APP_PREFERENCES_CITY_NAME = "CityName";

    public static void setVisible(View view, boolean show){
        if (view == null) return;

        int visibility = show ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

    public static void log(String message){
        Log.d(APP_TAG, message);
    }

}
