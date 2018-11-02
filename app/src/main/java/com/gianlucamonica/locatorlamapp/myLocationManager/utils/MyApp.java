package com.gianlucamonica.locatorlamapp.myLocationManager.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.gianlucamonica.locatorlamapp.myLocationManager.LocationMiddleware;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.MyLocationManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;


public class MyApp extends Application {

    private static MyApp instance;
    private static MyLocationManager myLocationManagerInstance;
    private static LocationMiddleware locationMiddlewareInstance;
    private static Activity activity;


    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public static void setInstance(MyApp instance) {
        MyApp.instance = instance;
    }

    public static MyApp getInstance() {
        return instance;
    }

    public static MyLocationManager getMyLocationManagerInstance() {
        return myLocationManagerInstance;
    }

    public static void setMyLocationManagerInstance(MyLocationManager myLocationManagerInstance) {
        MyApp.myLocationManagerInstance = myLocationManagerInstance;
    }

    public static LocationMiddleware getLocationMiddlewareInstance() {
        return locationMiddlewareInstance;
    }

    public static void setLocationMiddlewareInstance(LocationMiddleware locationMiddlewareInstance) {
        MyApp.locationMiddlewareInstance = locationMiddlewareInstance;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        MyApp.activity = activity;
    }

}
