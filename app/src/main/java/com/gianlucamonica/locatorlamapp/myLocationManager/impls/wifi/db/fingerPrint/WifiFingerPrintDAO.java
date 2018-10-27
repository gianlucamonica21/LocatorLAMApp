package com.gianlucamonica.locatorlamapp.myLocationManager.impls.wifi.db.fingerPrint;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WifiFingerPrintDAO {
    @Insert
    public void insert(WifiFingerPrint... wifiFingerPrints);

    @Update
    public void update(WifiFingerPrint... wifiFingerPrints);

    @Delete
    public void delete(WifiFingerPrint... wifiFingerPrints);

    @Query("SELECT * FROM WifiFingerPrint")
    public List<WifiFingerPrint> getFingerPrints();

    @Query("SELECT * FROM WifiFingerPrint WHERE apSsid = :apSsid AND gridName = :gridName")
    public WifiFingerPrint getFingerPrintWithAPSsidAndGridName(String apSsid, String gridName);

    @Query("SELECT * FROM WifiFingerPrint WHERE apSsid = :apSsid")
    public List<WifiFingerPrint> getFingerPrintWithAPSsid(String apSsid);

    @Query("DELETE FROM WifiFingerPrint WHERE apSsid = :apSsid")
    public void deleteByAPSsid(String apSsid);

}
