package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi;


import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.view.View;

import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.offline.WifiOfflineManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.utils.WifiScanReceiver;
import com.gianlucamonica.locatorlamapp.myLocationManager.locAlgInterface.LocalizationAlgorithmInterface;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.online.WifiOnlineManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.indoorParams.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan.OnlineScan;

import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

public class WifiAlgorithm implements LocalizationAlgorithmInterface {

    private WifiOfflineManager wifiOfflineManager;
    private WifiOnlineManager wifiOnlineManager;

    private ArrayList<IndoorParams> indoorParams;

    private WifiManager wifiManager;
    private final WifiScanReceiver wifiScanReceiver;

    public WifiAlgorithm(ArrayList<IndoorParams> indoorParams){
        this.indoorParams = indoorParams;
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        // faccio partire lo scan
        wifiManager.startScan();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        wifiScanReceiver = new WifiScanReceiver();
        MyApp.getContext().registerReceiver(wifiScanReceiver, intentFilter);
    }

    @Override
    public Object getBuildClass() {
        return new WifiOfflineManager(indoorParams);
    }

    @Override
    public <T extends View> T build(Class<T> type) {
        this.wifiOfflineManager = new WifiOfflineManager(indoorParams);
        return wifiOfflineManager.build(type);
    }

    @Override
    public OnlineScan locate() {
        this.wifiOnlineManager = new WifiOnlineManager(indoorParams);
        return  wifiOnlineManager.locate();
    }

    @Override
    public void checkPermissions() {
    }
    

}
