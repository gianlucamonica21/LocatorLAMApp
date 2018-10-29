package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiAP;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;


@Dao
public abstract class WifiAPDAO {

    @Insert
    public abstract void insert(WifiAP... wifiAPS);

    @Update
    public void update(WifiAP... wifiAPS) {}

    @Delete
    public void delete(WifiAP... wifiAPS) {}

    @Query("DELETE FROM wifiAP")
    public void deleteAll() {}

    @Query("SELECT * FROM wifiAP")
    public abstract List<WifiAP> getAllAPs();

    @Query("SELECT * FROM wifiAP WHERE wifiAP.idSsid = :idSsid")
    public abstract List<WifiAP> getBySsid(int idSsid);

    @Query("SELECT * FROM wifiAP WHERE bssid = :bssid")
    public abstract List<WifiAP> getByBssid(String bssid);

}


