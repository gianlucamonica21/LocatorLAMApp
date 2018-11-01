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
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.AP.AP;
import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.liveMeasurements.LiveMeasurements;
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

    public OnlineScan locate(){

       WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if( wifiInfo != null) {
            Log.i("wifi online manager", "wom " + String.valueOf(building.getId() + " " + algorithm.getId() + " " + config.getId()));

            List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getMyDAO().
                    getOfflineScan(building.getId(),algorithm.getId(),config.getId());
            if (offlineScans.size() > 0) {

                List<LiveMeasurements> liveMeasurements =
                        databaseManager.getAppDatabase().getLiveMeasurementsDAO().getLiveMeasurements(2,"wifi_rss");
                ArrayList<AP_RSS> ap_rsses = new ArrayList<>();
                for(int i = 0; i < liveMeasurements.size(); i++){
                    int idAP = liveMeasurements.get(i).getIdAP();
                    int value = (int) liveMeasurements.get(i).getValue();
                    ap_rsses.add(new AP_RSS(idAP,value));
                }
                Log.i("ap_rss","ap_rss " + ap_rsses.toString());
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


}
