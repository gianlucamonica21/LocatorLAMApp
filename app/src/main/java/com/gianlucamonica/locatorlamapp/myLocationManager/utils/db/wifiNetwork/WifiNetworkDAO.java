package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.wifiNetwork;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class WifiNetworkDAO {

    @Insert
    public abstract void insert(WifiNetwork... wifiNetworks);

    @Update
    public void update(WifiNetwork... wifiNetworks) {}

    @Delete
    public void delete(WifiNetwork... wifiNetworks) {}

    @Query("DELETE FROM wifiNetwork")
    public void deleteAll() {}

    @Query("SELECT * FROM wifiNetwork")
    public abstract List<WifiNetwork> getAllAPs();

    @Query("SELECT * FROM wifiNetwork WHERE ssid =:ssid")
    public abstract List<WifiNetwork> getBySsid(String ssid);

}