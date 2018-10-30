package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.ConfigDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.AlgorithmDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.Building;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.building.BuildingDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloor;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.buildingFloor.BuildingFloorDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScan;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan.OfflineScanDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan.OnlineScan;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.onlineScan.OnlineScanDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummary;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.scanSummary.ScanSummaryDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiAP.WifiAP;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiAP.WifiAPDAO;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiNetwork.WifiNetwork;
import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiNetwork.WifiNetworkDAO;

@Database(entities = {
        Building.class,
        BuildingFloor.class,
        Algorithm.class,
        Config.class,
        OfflineScan.class,
        OnlineScan.class,
        ScanSummary.class,
        WifiNetwork.class,
        WifiAP.class
        }, version = 47)
public abstract class AppDatabase extends RoomDatabase {

     public abstract BuildingDAO getBuildingDAO();
    public abstract AlgorithmDAO getAlgorithmDAO();
    public abstract OfflineScanDAO getOfflineScanDAO();
    public abstract OnlineScanDAO getOnlineScanDAO();
    public abstract ScanSummaryDAO getScanSummaryDAO();
    public abstract BuildingFloorDAO getBuildingFloorDAO();
    public abstract ConfigDAO getConfigDAO();
    public abstract MyDao getMyDAO();
    public abstract WifiNetworkDAO getWifiNetworkDAO();
    public abstract WifiAPDAO getWifiAPDAO();

}