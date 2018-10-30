package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.offline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamName;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParams;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.IndoorParamsUtils;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.DatabaseManager;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.MyApp;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiAP.WifiAP;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiNetwork.WifiNetwork;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.map.Grid;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.map.MapView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WifiOfflineManager extends AppCompatActivity{

    private DatabaseManager databaseManager;

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;

    public MapView mV;
    private WifiNetwork wifiNetwork;
    private ScanSummary scanSummary;

    private IndoorParamsUtils indoorParamsUtils;
    private ArrayList<IndoorParams> indoorParams;
    private Building building;
    private BuildingFloor buildingFloor;
    private Algorithm algorithm;
    private Config config;

    private int clickNumber = 0;
    private Grid nowGrid;
    private int idWifiNetwork = -1;

    public WifiOfflineManager(ArrayList<IndoorParams> indoorParams){
        this.indoorParams = indoorParams;
        this.indoorParamsUtils = new IndoorParamsUtils();
        this.databaseManager = new DatabaseManager();
        scanWifiNetwork();
        building = (Building) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.BUILDING);
        buildingFloor = (BuildingFloor) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.FLOOR);
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.ALGORITHM);
        config = (Config) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.CONFIG);
    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {

                List<ScanResult> mScanResults = wifiManager.getScanResults();
                WifiInfo info = wifiManager.getConnectionInfo ();
                String ssid  = info.getSSID();
                ssid =  ssid.toString().replace("\""   , "");

                // recupero id della rete a cui sono connesso
                if( databaseManager.getAppDatabase().getWifiNetworkDAO().getBySsid(ssid) != null){
                    WifiNetwork wifiNetwork = databaseManager.getAppDatabase().getWifiNetworkDAO().getBySsid(ssid).get(0);
                    idWifiNetwork = wifiNetwork.getId();
                }

                if( clickNumber == 0){
                    // inserisco in scan summary
                    Log.i("wifi off man", "inserisco scan summary click 0");
                    scanSummary = new ScanSummary(building.getId(),-1,algorithm.getId(),config.getId(),
                            idWifiNetwork ,"offline");
                    databaseManager.getAppDatabase().getScanSummaryDAO().insert(scanSummary);

                    for(int i = 0; i < mScanResults.size(); i++){
                        // inserisco scan in db solo per rete a cui si è connessi utilizzando l'info nowRect
                        if(mScanResults.get(i).SSID.toString().equals(ssid)){
                            String BSSID = mScanResults.get(i).BSSID;
                            int level = mScanResults.get(i).level;
                            Log.i("inser wifiap","inserisco " +  ssid + " " + BSSID);

                            // inserisco BSSID
                            insertBSSID(BSSID);
                            //inserisco in offlinescan
                            int idWifiAp = databaseManager.getAppDatabase().getWifiAPDAO().getByBssid(BSSID).get(0).getId();
                            List<ScanSummary> scanSummaries = null;
                            scanSummaries = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(
                                    building.getId(), algorithm.getId(),config.getId());
                            databaseManager.getAppDatabase().getOfflineScanDAO().insert(
                                    new OfflineScan(scanSummaries.get(0).getId(), Integer.valueOf(nowGrid.getName()),idWifiAp , level, new Date())
                            );

                            Log.i("wifi info","inserisco in db " + mScanResults.get(i).BSSID + " " + mScanResults.get(i).level
                                    + " " + nowGrid.getName());
                        }
                    }
                }else{

                    for(int i = 0; i < mScanResults.size(); i++){
                        if(mScanResults.get(i).SSID.toString().equals(ssid)){
                            String BSSID = mScanResults.get(i).BSSID;
                            int level = mScanResults.get(i).level;
                            Log.i("inser wifiap","inserisco " +  ssid + " " + BSSID);
                            // inserisco BSSID
                            insertBSSID(BSSID);
                            //inserisco in offlinescan
                            int idWifiAp = databaseManager.getAppDatabase().getWifiAPDAO().getByBssid(BSSID).get(0).getId();
                            List<ScanSummary> scanSummaries = null;
                            scanSummaries = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(
                                    building.getId(), algorithm.getId(),config.getId());
                            databaseManager.getAppDatabase().getOfflineScanDAO().insert(
                                    new OfflineScan(scanSummaries.get(0).getId(), Integer.valueOf(nowGrid.getName()),idWifiAp , level, new Date())
                            );
                            Log.i("wifi info", "wifiAP " +String.valueOf(idWifiAp));
                            Log.i("wifi info","inserisco in db " + mScanResults.get(i).BSSID + " " + mScanResults.get(i).level
                                    + " " + nowGrid.getName());
                        }
                    }

                }

            }
            clickNumber++;
            Toast.makeText(MyApp.getContext(), "Scanning in  " + nowGrid.getName() + " OK", Toast.LENGTH_SHORT).show();
            //mWifiScanReceiver.abortBroadcast();

        }
    };

    public <T extends View> T build(Class<T> type){

        if( wifiManager.getConnectionInfo() != null){
            mV = new MapView(MyApp.getContext(),null, null, indoorParams, null);
            mV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    collectRssiByUI(event);
                    return false;
                }
            });
            Toast.makeText(MyApp.getContext(),
                    "Tap on the grid corresponding to your position to do a scan, if you want to redo it click 'Redo Scan'",
                    Toast.LENGTH_LONG).show();

            return type.cast(mV);
        }else{

            Toast.makeText(MyApp.getContext(),
                    "You first have to get connected to a wifi",
                    Toast.LENGTH_LONG).show();
            return null;
        }




    }

    /**
     * insert the new wifi nwtwork if it doesn't exist yet
     */
    public void scanWifiNetwork(){
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo != null){
            wifiNetwork = new WifiNetwork(wifiInfo.getSSID().toString().replace("\""   , ""),wifiInfo.getMacAddress());
            List<WifiNetwork> wifiNetworkList = databaseManager.getAppDatabase().getWifiNetworkDAO().getBySsid(wifiNetwork.getSsid());
            if(wifiNetworkList.size() == 0){
                databaseManager.getAppDatabase().getWifiNetworkDAO().insert(wifiNetwork);
            }
        }
    }


    public void collectRssiByUI(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            Log.i("TOUCHED ", x + " " + y);

            ArrayList<Grid> rects = mV.getRects();

            if(rects.size() == 0){
                Toast.makeText(MyApp.getContext(),"scan is finished",Toast.LENGTH_SHORT).show();
            }else{
                for(int i = 0; i < rects.size(); i = i + 1){
                    float aX = ((rects.get(i).getA().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bX = ((rects.get(i).getB().getX()*mV.getScaleFactor())+ mV.getAdd());
                    float bY = ((rects.get(i).getB().getY()*mV.getScaleFactor())+ mV.getAdd());
                    float aY = ((rects.get(i).getA().getY()*mV.getScaleFactor())+ mV.getAdd());
                    String gridName = rects.get(i).getName();

                    if( x >= aX && x <= bX){
                        if( y <= bY && y >= aY){


                            //salvo grid appena cliccata
                            nowGrid = rects.get(i);

                            // faccio partire lo scan
                            wifiManager.startScan();
                            IntentFilter intentFilter = new IntentFilter();
                            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                            MyApp.getContext().registerReceiver(mWifiScanReceiver, intentFilter);

                            //scan wifi rss
                            //int rssiValue = wifiScan(gridName);
                            //inserisco in db
                            //insertRssInDB(rssiValue,rects,i);
                            rects.remove(i);
                        }
                    }
                }
            }
            mV.invalidate();
        }
    }

    public void insertBSSID(String BSSID){
        if(idWifiNetwork != -1){
            try {
                if(databaseManager.getAppDatabase().getWifiAPDAO().getByBssid(BSSID).size() == 0){
                    databaseManager.getAppDatabase().getWifiAPDAO().insert(
                            new WifiAP(idWifiNetwork, BSSID)
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
