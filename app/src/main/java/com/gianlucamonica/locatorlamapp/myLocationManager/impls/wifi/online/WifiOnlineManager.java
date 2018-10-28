package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.online;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

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
    private ArrayList<IndoorParams> indoorParams;
    private IndoorParamsUtils indoorParamsUtils;
    private DatabaseManager databaseManager;
    private Algorithm algorithm;
    private Building building;
    private BuildingFloor buildingFloor;
    private Config config;

    private MapView mapView;

    public WifiOnlineManager(ArrayList<IndoorParams> indoorParams){
        databaseManager = new DatabaseManager();
        indoorParamsUtils = new IndoorParamsUtils();
        this.indoorParams = indoorParams;
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.ALGORITHM);
        building = (Building) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.BUILDING);
        config = (Config) indoorParamsUtils.getParamObject(indoorParams, IndoorParamName.CONFIG);
    }

    public OnlineScan locate(){

        int rssiValue = wifiScan(); // getting live wifi rssi

        //todo impl multiple aps managing
        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if( wifiInfo != null) {
            Log.i("wifi online manager", "wom " + String.valueOf(building.getId() + " " + algorithm.getId() + " " + config.getId()));

            List<OfflineScan> offlineScans = databaseManager.getAppDatabase().getMyDAO().
                    getOfflineScan(building.getId(),algorithm.getId(),config.getId());
            if (offlineScans.size() > 0) {

                euclideanDistanceAlg = new EuclideanDistanceAlg(offlineScans, rssiValue);
                int index = euclideanDistanceAlg.compute(AlgorithmName.WIFI_RSS_FP);

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

        return  null;

    }


    public int wifiScan(){
        WifiManager wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int rssiValue = wifiInfo.getRssi();

        Log.i("wifiInfo", String.valueOf(rssiValue));
        Toast.makeText(MyApp.getContext(), "Scanning live rss  " + rssiValue, Toast.LENGTH_SHORT).show();
        return rssiValue;
    }

    public List<WifiFingerPrint> getFingerPrintsFromDb(String ssid){
        /*WifiFingerPrintDAO wifiFingerPrintDAO = databaseManager.getAppDatabase().getFingerPrintDAO();
        List<WifiFingerPrint> wifiFingerPrints = wifiFingerPrintDAO.getFingerPrintWithAPSsid(ssid);
        return wifiFingerPrints;*/
        return null;
    }
}
