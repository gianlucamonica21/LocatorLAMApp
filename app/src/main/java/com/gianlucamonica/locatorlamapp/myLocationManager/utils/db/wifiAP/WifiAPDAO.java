package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiAP;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algConfig.Config;

import java.util.List;

@Dao
public abstract class WifiAPDAO {

    @Insert
    public abstract void insert(WifiAP... wifiAPs);

    @Update
    public void update(WifiAP... wifiAPs) {}

    @Delete
    public void delete(WifiAP... wifiAPs) {}

    @Query("DELETE FROM wifiAP")
    public void deleteAll() {}

    @Query("SELECT * FROM wifiAP")
    public abstract List<WifiAP> getAllAPs();

    @Query("SELECT * FROM wifiAP WHERE ssid =:ssid")
    public abstract List<WifiAP> getBySsid(String ssid);

}