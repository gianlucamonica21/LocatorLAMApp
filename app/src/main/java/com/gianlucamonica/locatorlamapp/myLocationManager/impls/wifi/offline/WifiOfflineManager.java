package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.offline;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.fingerPrint.WifiFingerPrint;
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
    private WifiAP wifiAP;
    private ScanSummary scanSummary;

    private IndoorParamsUtils indoorParamsUtils;
    private ArrayList<IndoorParams> indoorParams;
    private Building building;
    private BuildingFloor buildingFloor;
    private Algorithm algorithm;
    private Config config;

    private int clickNumber = 0;


    public WifiOfflineManager(ArrayList<IndoorParams> indoorParams){
        this.indoorParams = indoorParams;
        this.indoorParamsUtils = new IndoorParamsUtils();
        this.databaseManager = new DatabaseManager();
        scanAPs();
        building = (Building) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.BUILDING);
        buildingFloor = (BuildingFloor) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.FLOOR);
        algorithm = (Algorithm) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.ALGORITHM);
        config = (Config) indoorParamsUtils.getParamObject(this.indoorParams, IndoorParamName.CONFIG);
    }

    public <T extends View> T build(Class<T> type){

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

    }

    /**
     * insert the new ap if it doesn't exist yet
     */
    public void scanAPs(){
        wifiManager = (WifiManager) MyApp.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        if(wifiInfo != null){

            wifiAP = new WifiAP(wifiInfo.getSSID(),wifiInfo.getMacAddress());
            List<WifiAP> wifiAPList = databaseManager.getAppDatabase().getWifiAPDAO().getBySsid(wifiAP.getSsid());
            if(wifiAPList.size() == 0){
                databaseManager.getAppDatabase().getWifiAPDAO().insert(wifiAP);
            }
        }
    }

    public int wifiScan(String gridName){
        wifiInfo = wifiManager.getConnectionInfo();
        int rssiValue = wifiInfo.getRssi();
        Log.i("wifiInfo", String.valueOf(rssiValue));
        Toast.makeText(MyApp.getContext(), "Scanning in  " + gridName + "  rss  " + rssiValue, Toast.LENGTH_SHORT).show();
        return rssiValue;
    }

    public void insertRssInDB(int rssiValue, ArrayList<Grid> rects, int i){

        List<ScanSummary> scanSummaryList = null;

        if(clickNumber == 0){
            List<WifiAP> wifiAPList = databaseManager.getAppDatabase().getWifiAPDAO().getBySsid(wifiAP.getSsid());
            if( wifiAPList.size() != 0){
                //inizio di un nuovo scan, inserisco in scanSummary
                scanSummary = new ScanSummary(building.getId(),buildingFloor.getId(),algorithm.getId(),config.getId(), 1 ,"offline");
                databaseManager.getAppDatabase().getScanSummaryDAO().insert(scanSummary);

                 scanSummaryList = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(
                        building.getId(), algorithm.getId(),config.getId()
                );
                if(scanSummaryList.size() != 0){

                    //inserisco in offlinescan
                    databaseManager.getAppDatabase().getOfflineScanDAO().insert(
                            new OfflineScan(scanSummaryList.get(0).getId(), Integer.valueOf(rects.get(i).getName()),rssiValue,new Date())
                    );
                }
            }
        }else{
            scanSummaryList = databaseManager.getAppDatabase().getScanSummaryDAO().getScanSummaryByBuildingAlgorithm(
                    building.getId(), algorithm.getId(),config.getId()
            );
            //inserisco in offlinescan
            databaseManager.getAppDatabase().getOfflineScanDAO().insert(
                    new OfflineScan(scanSummaryList.get(0).getId(), Integer.valueOf(rects.get(i).getName()),rssiValue,new Date())
            );
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
                            //scan wifi rss
                            int rssiValue = wifiScan(gridName);
                            //inserisco in db
                            insertRssInDB(rssiValue,rects,i);
                            rects.remove(i);
                            clickNumber++;

                        }
                    }
                }
            }
            mV.invalidate();
        }
    }

}
