package com.gianlucamonica.locatorlamapp.myLocationManager.impls.magnetic.db.magneticFingerPrint;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MagneticFingerPrintDAO {

    @Insert
    public void insert(MagneticFingerPrint... magneticFingerPrints);

    @Update
    public void update(MagneticFingerPrint ... magneticFingerPrints);

    @Delete
    public void delete(MagneticFingerPrint ... magneticFingerPrints);

    @Query("DELETE FROM magneticfingerprint")
    public void deleteAll();

    @Query("SELECT * FROM magneticFingerPrint")
    public List<MagneticFingerPrint> getMagneticFingerPrints();

    @Query("SELECT * FROM magneticFingerPrint WHERE gridName = :gridName")
    public List<MagneticFingerPrint> getFingerPrintWithGridName(String gridName);
}
