package com.example.cloniamix.weatherapp.app;

import android.app.Application;


public class MyApp extends Application {

    private static  MyApp ourInstance;

    public static MyApp getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        ourInstance = this;
        super.onCreate();
    }

}
