package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.online;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.myLocationManager.AP_RSS;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.EuclidDistanceMultipleAPs;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.EuclideanDistanceAlg;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiNetwork.WifiNetwork;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.map.MapView;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.AlgorithmName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static android.content.Context.WIFI_SERVICE;

public class WifiOnlineManager {

    private EuclideanDistanceAlg euclideanDistanceAlg;
    private EuclidDistanceMultipleAPs euclidDistanceMultipleAPs;
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;
    private DatabaseManager databaseManager;
    private Algorithm algorithm;
    private Building building;
    private BuildingFloor buildingFloor;
    private Config config;
    private WifiManager wifiManager;
    private int idWifiNetwork;
    private ArrayList<AP_RSS> ap_rsses;
    private OnlineScan onlineScan;

    private MapView mapView;

    public WifiOnlineManager(ArrayList<IndoorParams> indoorParams){
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        ap_rsses = new ArrayList<>();
        this.indoorParams = indoorParams;
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        building = (Building) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.BUILDING);
        config = (Config) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.CONFIG);
        onlineScan  = null;
    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

                List<ScanResult> mScanResults = wifiManager.getScanResults();
                WifiInfo info = wifiManager.getConnectionInfo ();
                String ssid  = info.getSSID();
                ssid =  ssid.toString().replace("\""   , "");


                for(int i = 0; i < mScanResults.size(); i++){
                    // inserisco scan in db solo per rete a cui si è connessi utilizzando l'info nowRect
                    if(mScanResults.get(i).SSID.toString().equals(ssid)){
                        String BSSID = mScanResults.get(i).BSSID;
                        int level = mScanResults.get(i).level;
                        int idWifiAp = 0;
                        try {
                            idWifiAp = databaseManager.getAppDatabase().getWifiAPDAO().getByBssid(BSSID).get(0).getId();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.i("wifi onl man","popolo ap rss " + ap_rsses.toString());
                        ap_rsses.add(new AP_RSS(idWifiAp,level));

                    }
                }


            }

            mWifiScanReceiver.abortBroadcast();
        }
    };

    public OnlineScan locate(){
        wifiScan();

       WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if( wifiInfo != null) {
            Log.i("wifi online manager", "wom " + String.valueOf(building.getId() + " " + algorithm.getId() + " " + config.getId()));

            List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getMyDAO().
                    getOfflineScan(building.getId(),algorithm.getId(),config.getId());
            if (offlineScans.size() > 0) {

                ap_rsses.add(new AP_RSS(1,45));
                ap_rsses.add(new AP_RSS(2,45));
                ap_rsses.add(new AP_RSS(3,45));
                ap_rsses.add(new AP_RSS(4,45));
                euclidDistanceMultipleAPs = new EuclidDistanceMultipleAPs(offlineScans,ap_rsses);
                int index = euclidDistanceMultipleAPs.compute();

                Toast.makeText(MyApp.getContext(),
                        "Sei nel riquadro " + index,
                        Toast.LENGTH_SHORT).show();

                OnlineScan onlineScan = new OnlineScan(offlineScans.get(0).getIdScan(),index,0,new Date());
                return onlineScan;
            } else {
                Toast.makeText(MyApp.getContext(),
                        "Non ci sono informazioni in db",
                        Toast.LENGTH_SHORT).show();
            }
        }

        return  new OnlineScan(1,2,3,new Date());

    }


    public void wifiScan(){
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        // faccio partire lo scan
        wifiManager.startScan();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        MyApp.getContext().registerReceiver(mWifiScanReceiver, intentFilter);
    }

}
