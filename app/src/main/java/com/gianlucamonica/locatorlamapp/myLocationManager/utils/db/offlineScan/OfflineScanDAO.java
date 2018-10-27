package com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.offlineScan;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gianlucamonica.locatorlamapp.myLocationManager.utils.db.algorithm.Algorithm;

import java.util.List;

@Dao
public abstract class OfflineScanDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(OfflineScan... offlineScans);

    @Update
    public void update(OfflineScan ... offlineScans){};

    @Delete
    public void delete(OfflineScan ... offlineScans){};

    @Query("DELETE FROM offlineScan")
    public void deleteAll(){};

    @Query("SELECT * FROM offlineScan")
    public abstract List<OfflineScan> getOfflineScans();

    @Query("SELECT * FROM offlineScan WHERE idScan = :idScan")
    public abstract List<OfflineScan>  getOfflineScansById(int idScan);

}
